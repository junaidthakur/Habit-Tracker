package com.example.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val DEFAULT_RELAPSE_REASONS = listOf(
    "মানসিক চাপ (Stress)",
    "একঘেয়েমি (Boredom)",
    "বন্ধুদের প্ররোচনা (Peer Pressure)",
    "তীব্র ইচ্ছা (Craving)",
    "মন খারাপ বা বিষণ্নতা (Sadness)",
    "ক্লান্তি ও ঘুম কম (Fatigue)",
    "সোশ্যাল মিডিয়া ট্রিগার (Media)",
    "অন্যান্য কারণ (Other)"
)

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RelapseDialog(
    onDismiss: () -> Unit,
    onConfirmRelapse: (reason: String, notes: String, triggerCategory: String) -> Unit,
    currentStreakDays: Double,
    lang: String = "en"
) {
    val defaultReasons = when (lang.lowercase()) {
        "bn" -> listOf(
            "মানসিক চাপ (Stress)",
            "একঘেয়েমি (Boredom)",
            "বন্ধুদের প্ররোচনা (Peer Pressure)",
            "তীব্র ইচ্ছা (Craving)",
            "মন খারাপ বা বিষণ্নতা (Sadness)",
            "ক্লান্তি ও ঘুম কম (Fatigue)",
            "সোশ্যাল মিডিয়া ট্রিগার (Media)",
            "অন্যান্য কারণ (Other)"
        )
        "es" -> listOf(
            "Estrés",
            "Aburrimiento",
            "Presión social",
            "Ansia / Craving",
            "Tristeza",
            "Fatiga / Cansancio",
            "Redes sociales",
            "Otro motivo"
        )
        else -> listOf(
            "Stress",
            "Boredom",
            "Peer Pressure",
            "Craving",
            "Sadness / Depression",
            "Fatigue & Sleep Lack",
            "Social Media Triggers",
            "Other Causes"
        )
    }

    var selectedReason by remember(lang) { mutableStateOf(defaultReasons[0]) }
    var customReason by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        modifier = Modifier.testTag("relapse_dialog"),
        shape = RoundedCornerShape(24.dp),
        icon = {
            Icon(
                imageVector = Icons.Default.Warning,
                contentDescription = "Relapse Warning",
                tint = MaterialTheme.colorScheme.error,
                modifier = Modifier.size(36.dp)
            )
        },
        title = {
            Text(
                text = com.example.util.AppStrings.get("relapse_dialog_title", lang),
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            )
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Text(
                    text = com.example.util.AppStrings.get("relapse_dialog_sub", lang),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = com.example.util.AppStrings.get("select_relapse_reason", lang),
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    defaultReasons.forEach { reason ->
                        val isSelected = selectedReason == reason
                        FilterChip(
                            selected = isSelected,
                            onClick = { selectedReason = reason },
                            label = {
                                Text(
                                    text = reason,
                                    fontSize = 12.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                )
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MaterialTheme.colorScheme.errorContainer,
                                selectedLabelColor = MaterialTheme.colorScheme.onErrorContainer
                            )
                        )
                    }
                }

                if (selectedReason.lowercase().contains("other") || selectedReason.contains("অন্যান্য") || selectedReason.lowercase().contains("otro")) {
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = customReason,
                        onValueChange = { customReason = it },
                        label = { Text(com.example.util.AppStrings.get("custom_reason_label", lang)) },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    label = { Text(com.example.util.AppStrings.get("notes_label", lang)) },
                    placeholder = { Text(com.example.util.AppStrings.get("notes_placeholder", lang)) },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 3
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val isOther = selectedReason.lowercase().contains("other") || selectedReason.contains("অন্যান্য") || selectedReason.lowercase().contains("otro")
                    val finalReason = if (isOther && customReason.isNotBlank()) {
                        customReason.trim()
                    } else {
                        selectedReason
                    }
                    onConfirmRelapse(finalReason, notes.trim(), selectedReason)
                    onDismiss()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error,
                    contentColor = MaterialTheme.colorScheme.onError
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.testTag("confirm_relapse_button")
            ) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.size(6.dp))
                Text(com.example.util.AppStrings.get("confirm_reset_btn", lang), fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                modifier = Modifier.testTag("cancel_relapse_button")
            ) {
                Text(com.example.util.AppStrings.get("cancel_btn", lang))
            }
        }
    )
}
