package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.SmokingRooms
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.data.db.HabitEntity
import com.example.ui.viewmodel.HabitViewModel

data class HabitTemplate(
    val titleEn: String,
    val titleBn: String,
    val categoryEn: String,
    val categoryBn: String,
    val descriptionEn: String,
    val descriptionBn: String
) {
    fun getTitle(lang: String): String = if (lang.lowercase() == "bn") titleBn else titleEn
    fun getCategory(lang: String): String = if (lang.lowercase() == "bn") categoryBn else categoryEn
    fun getDescription(lang: String): String = if (lang.lowercase() == "bn") descriptionBn else descriptionEn
}

val PRESET_HABIT_TEMPLATES = listOf(
    HabitTemplate("Quit Smoking", "ধূমপান ত্যাগ", "Health", "স্বাস্থ্য", "Journey towards a healthier smoke-free life", "তামাক ও নিকোটিন মুক্ত সুস্থ জীবন"),
    HabitTemplate("Social Media Detox", "সোশ্যাল মিডিয়া আসক্তি", "Time", "সময় ব্যবস্থাপনা", "Reduce screen time and increase focus", "অতিরিক্ত স্ক্রিন টাইম কমানো"),
    HabitTemplate("Junk Food Control", "ফাস্ট ফুড নিয়ন্ত্রণ", "Diet", "খাদ্যাভ্যাস", "Eat healthy and balanced meals", "স্বাস্থ্যকর ও পরিমিত খাবার গ্রহণ"),
    HabitTemplate("Gaming Addiction", "গেম আসক্তি", "Productivity", "উৎপাদনশীলতা", "Control excessive gaming hours", "অতিরিক্ত গেম খেলা নিয়ন্ত্রণ করা"),
    HabitTemplate("Late Sleep Habit", "দেরিতে ঘুমানো", "Sleep", "ঘুম ও বিশ্রাম", "Fix sleep schedule and rest well", "নিয়মিত রাতে দ্রুত ঘুমানোর অভ্যাস"),
    HabitTemplate("Excess Caffeine", "অতিরিক্ত চা/কফি", "Health", "স্বাস্থ্য", "Reduce dependency on coffee & caffeine", "ক্যাফেইন নির্ভরতা কমানো"),
    HabitTemplate("Custom Habit", "কাস্টম অভ্যাস", "Personal", "ব্যক্তিগত", "Set your own custom bad habit", "নিজের মতো করে অভ্যাস তৈরি করুন")
)

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HabitsScreen(
    viewModel: HabitViewModel,
    allHabits: List<HabitEntity>,
    selectedHabitId: Long?,
    modifier: Modifier = Modifier
) {
    val userProfile by viewModel.userProfile.collectAsStateWithLifecycle()
    val lang = userProfile.language

    var showAddDialog by remember { mutableStateOf(false) }
    var selectedTemplate by remember(lang) { mutableStateOf(PRESET_HABIT_TEMPLATES[0]) }
    var customTitle by remember { mutableStateOf("") }
    var customDescription by remember { mutableStateOf("") }

    if (showAddDialog) {
        AlertDialog(
            onDismissRequest = { showAddDialog = false },
            shape = RoundedCornerShape(24.dp),
            title = {
                Text(
                    text = com.example.util.AppStrings.get("add_habit_dialog_title", lang),
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                )
            },
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Text(
                        text = com.example.util.AppStrings.get("select_template", lang),
                        style = MaterialTheme.typography.labelMedium.copy(
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
                        PRESET_HABIT_TEMPLATES.forEach { template ->
                            val isSelected = selectedTemplate == template
                            FilterChip(
                                selected = isSelected,
                                onClick = {
                                    selectedTemplate = template
                                    if (template.titleEn.contains("Custom") || template.titleBn.contains("কাস্টম")) {
                                        customTitle = ""
                                        customDescription = ""
                                    } else {
                                        customTitle = template.getTitle(lang)
                                        customDescription = template.getDescription(lang)
                                    }
                                },
                                label = { Text(template.getTitle(lang).split(" ")[0]) }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = customTitle,
                        onValueChange = { customTitle = it },
                        label = { Text(com.example.util.AppStrings.get("habit_name_label", lang)) },
                        placeholder = { Text(com.example.util.AppStrings.get("habit_name_ph", lang)) },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = customDescription,
                        onValueChange = { customDescription = it },
                        label = { Text(com.example.util.AppStrings.get("habit_desc_label", lang)) },
                        placeholder = { Text(com.example.util.AppStrings.get("habit_desc_ph", lang)) },
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 2
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (customTitle.isNotBlank()) {
                            viewModel.createNewHabit(
                                title = customTitle.trim(),
                                description = customDescription.trim(),
                                category = selectedTemplate.getCategory(lang),
                                iconName = "eco"
                            )
                            showAddDialog = false
                        }
                    },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.testTag("save_new_habit_button")
                ) {
                    Text(com.example.util.AppStrings.get("save_btn", lang))
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddDialog = false }) {
                    Text(com.example.util.AppStrings.get("cancel_btn", lang))
                }
            }
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = com.example.util.AppStrings.get("bad_habits_header", lang),
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                )
                Text(
                    text = com.example.util.AppStrings.get("bad_habits_sub", lang),
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
            }

            FloatingActionButton(
                onClick = {
                    selectedTemplate = PRESET_HABIT_TEMPLATES[0]
                    customTitle = PRESET_HABIT_TEMPLATES[0].getTitle(lang)
                    customDescription = PRESET_HABIT_TEMPLATES[0].getDescription(lang)
                    showAddDialog = true
                },
                modifier = Modifier
                    .size(48.dp)
                    .testTag("add_habit_fab"),
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Habit"
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(allHabits) { habit ->
                val isSelected = habit.id == selectedHabitId

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { viewModel.selectHabit(habit.id) },
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = if (isSelected) 4.dp else 1.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(42.dp)
                                    .clip(CircleShape)
                                    .background(if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Eco,
                                    contentDescription = null,
                                    tint = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(22.dp)
                                )
                            }

                            Spacer(modifier = Modifier.width(14.dp))

                            Column {
                                Text(
                                    text = com.example.util.AppStrings.getLocalizedHabitTitle(habit.title, lang),
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface
                                    )
                                )
                                Text(
                                    text = habit.description.ifBlank { com.example.util.AppStrings.get("no_description", lang) },
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f) else MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                )
                            }
                        }

                        if (isSelected) {
                            Surface(
                                shape = CircleShape,
                                color = MaterialTheme.colorScheme.primary
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = "Selected",
                                    tint = MaterialTheme.colorScheme.onPrimary,
                                    modifier = Modifier
                                        .padding(6.dp)
                                        .size(16.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
