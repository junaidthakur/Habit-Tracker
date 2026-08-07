package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.db.HabitEntity
import com.example.ui.components.MotivationalCard
import com.example.ui.components.PlantGrowthCard
import com.example.ui.components.RelapseDialog
import com.example.ui.components.TimerDisplay
import com.example.ui.model.PlantStage
import com.example.ui.viewmodel.HabitViewModel
import com.example.ui.viewmodel.TimeElapsed

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HabitViewModel,
    selectedHabit: HabitEntity?,
    allHabits: List<HabitEntity>,
    timeElapsed: TimeElapsed,
    currentStage: PlantStage,
    onNavigateToAddHabit: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showRelapseDialog by remember { mutableStateOf(false) }
    var dropdownExpanded by remember { mutableStateOf(false) }

    val userProfile by viewModel.userProfile.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState()

    val context = androidx.compose.ui.platform.LocalContext.current
    val activity = context as? android.app.Activity

    val lang = userProfile.language

    if (showRelapseDialog) {
        RelapseDialog(
            onDismiss = { showRelapseDialog = false },
            onConfirmRelapse = { reason, notes, triggerCat ->
                viewModel.recordRelapse(reason, notes, triggerCat)
                if (activity != null) {
                    com.example.util.AdMobManager.loadAndShowInterstitialAd(activity)
                }
            },
            currentStreakDays = timeElapsed.totalDaysDouble,
            lang = lang
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(scrollState)
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        // Active Habit Selector Dropdown Bar
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .testTag("habit_selector_card"),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { dropdownExpanded = true }
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Eco,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        Text(
                            text = com.example.util.AppStrings.getLocalizedHabitTitle(selectedHabit?.title, lang),
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                        Text(
                            text = selectedHabit?.description?.ifBlank { com.example.util.AppStrings.get("default_habit_sub", lang) }
                                ?: com.example.util.AppStrings.get("default_habit_sub", lang),
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
                            )
                        )
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Select Habit",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                DropdownMenu(
                    expanded = dropdownExpanded,
                    onDismissRequest = { dropdownExpanded = false }
                ) {
                    allHabits.forEach { habit ->
                        DropdownMenuItem(
                            text = { Text(com.example.util.AppStrings.getLocalizedHabitTitle(habit.title, lang), fontWeight = FontWeight.SemiBold) },
                            onClick = {
                                viewModel.selectHabit(habit.id)
                                dropdownExpanded = false
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = null,
                                    tint = if (habit.id == selectedHabit?.id) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline
                                )
                            }
                        )
                    }
                    DropdownMenuItem(
                        text = { Text(com.example.util.AppStrings.get("add_new_habit_btn", lang), fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary) },
                        onClick = {
                            dropdownExpanded = false
                            onNavigateToAddHabit()
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Plant Growth Stage Showcase Card
        PlantGrowthCard(
            currentStage = currentStage,
            daysElapsed = timeElapsed.totalDaysDouble,
            selectedSkinId = userProfile.selectedPlantSkinId,
            lang = lang
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Quick Plant & Seed Skin Selector Button
        OutlinedButton(
            onClick = {
                val skins = listOf("skin_natural", "skin_sakura", "skin_golden", "skin_autumn", "skin_mystic", "skin_cyber", "skin_cosmic")
                val currentIndex = skins.indexOf(userProfile.selectedPlantSkinId)
                val nextSkinId = skins[(if (currentIndex < 0) 0 else currentIndex + 1) % skins.size]
                viewModel.selectPlantSkin(nextSkinId)
            },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Default.Eco,
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            val currentSkin = com.example.ui.model.PRESET_PLANT_SKINS.find { it.id == userProfile.selectedPlantSkinId }
            val currentSkinName = currentSkin?.getName(lang) ?: "Natural Oak"
            Text(
                text = String.format(com.example.util.AppStrings.get("seed_tree_theme_btn", lang), currentSkinName),
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Timer Display
        TimerDisplay(
            timeElapsed = timeElapsed,
            lang = lang
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Primary Action Button
        Button(
            onClick = { showRelapseDialog = true },
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp)
                .testTag("relapse_button"),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.error,
                contentColor = MaterialTheme.colorScheme.onError
            ),
            shape = RoundedCornerShape(16.dp),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Warning,
                contentDescription = null,
                modifier = Modifier.size(22.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = com.example.util.AppStrings.get("i_relapsed_btn", lang),
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Motivational Card
        MotivationalCard(lang = lang)

        Spacer(modifier = Modifier.height(16.dp))

        // Milestone Roadmap Explanation Card
        MilestoneRoadmapCard(lang = lang)

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun MilestoneRoadmapCard(lang: String = "en") {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp)
        ) {
            Text(
                text = com.example.util.AppStrings.get("milestone_header", lang),
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            MilestoneItem(
                dayText = com.example.util.AppStrings.get("m_seed_day", lang),
                stageTitle = com.example.util.AppStrings.get("m_seed_title", lang),
                desc = com.example.util.AppStrings.get("m_seed_desc", lang)
            )

            MilestoneItem(
                dayText = com.example.util.AppStrings.get("m_sprout_day", lang),
                stageTitle = com.example.util.AppStrings.get("m_sprout_title", lang),
                desc = com.example.util.AppStrings.get("m_sprout_desc", lang)
            )

            MilestoneItem(
                dayText = com.example.util.AppStrings.get("m_sapling_day", lang),
                stageTitle = com.example.util.AppStrings.get("m_sapling_title", lang),
                desc = com.example.util.AppStrings.get("m_sapling_desc", lang)
            )

            MilestoneItem(
                dayText = com.example.util.AppStrings.get("m_tree_day", lang),
                stageTitle = com.example.util.AppStrings.get("m_tree_title", lang),
                desc = com.example.util.AppStrings.get("m_tree_desc", lang)
            )
        }
    }
}

@Composable
private fun MilestoneItem(
    dayText: String,
    stageTitle: String,
    desc: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            shape = RoundedCornerShape(8.dp),
            color = MaterialTheme.colorScheme.primaryContainer,
            modifier = Modifier.width(90.dp)
        ) {
            Text(
                text = dayText,
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column {
            Text(
                text = stageTitle,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            )
            Text(
                text = desc,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 12.sp
                )
            )
        }
    }
}
