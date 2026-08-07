package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.components.BannerAdBar
import com.example.ui.screens.HabitsScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.ProfileScreen
import com.example.ui.screens.StatisticsScreen
import com.example.ui.theme.HabitTrackerTheme
import com.example.ui.viewmodel.HabitViewModel

enum class MainTab(val titleBn: String, val icon: androidx.compose.ui.graphics.vector.ImageVector) {
    TRACKER("গার্ডেন", Icons.Default.Eco),
    STATISTICS("পরিসংখ্যান", Icons.Default.BarChart),
    HABITS("অভ্যাসসমূহ", Icons.Default.List),
    PROFILE("প্রোফাইল", Icons.Default.Person)
}

class MainActivity : ComponentActivity() {

    private val habitViewModel: HabitViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        com.example.util.AdMobManager.init(this)

        setContent {
            val userProfile by habitViewModel.userProfile.collectAsStateWithLifecycle()

            HabitTrackerTheme(selectedThemeId = userProfile.selectedThemeId) {
                MainAppScreen(viewModel = habitViewModel)
            }
        }
    }
}

@Composable
fun MainAppScreen(viewModel: HabitViewModel) {
    var selectedTab by remember { mutableStateOf(MainTab.TRACKER) }

    val activeHabits by viewModel.activeHabits.collectAsStateWithLifecycle()
    val selectedHabitId by viewModel.selectedHabitId.collectAsStateWithLifecycle()
    val selectedHabit by viewModel.selectedHabit.collectAsStateWithLifecycle()
    val timeElapsed by viewModel.timeElapsed.collectAsStateWithLifecycle()
    val currentStage by viewModel.currentPlantStage.collectAsStateWithLifecycle()
    val relapsesList by viewModel.relapsesList.collectAsStateWithLifecycle()
    val statisticsData by viewModel.statistics.collectAsStateWithLifecycle()
    val userProfile by viewModel.userProfile.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            Column(modifier = Modifier.fillMaxWidth()) {
                // Docked Banner Ad Bar
                BannerAdBar()

                // Navigation Bar
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.testTag("bottom_navigation_bar")
                ) {
                    MainTab.values().forEach { tab ->
                        val isSelected = selectedTab == tab
                        val lang = userProfile.language
                        val tabTitle = when (tab) {
                            MainTab.TRACKER -> com.example.util.AppStrings.get("tab_garden", lang)
                            MainTab.STATISTICS -> com.example.util.AppStrings.get("tab_stats", lang)
                            MainTab.HABITS -> com.example.util.AppStrings.get("tab_habits", lang)
                            MainTab.PROFILE -> com.example.util.AppStrings.get("tab_profile", lang)
                        }
                        NavigationBarItem(
                            selected = isSelected,
                            onClick = { selectedTab = tab },
                            icon = {
                                Icon(
                                    imageVector = tab.icon,
                                    contentDescription = tabTitle
                                )
                            },
                            label = {
                                Text(
                                    text = tabTitle,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                                selectedTextColor = MaterialTheme.colorScheme.primary,
                                indicatorColor = MaterialTheme.colorScheme.primaryContainer
                            )
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (selectedTab) {
                MainTab.TRACKER -> {
                    HomeScreen(
                        viewModel = viewModel,
                        selectedHabit = selectedHabit,
                        allHabits = activeHabits,
                        timeElapsed = timeElapsed,
                        currentStage = currentStage,
                        onNavigateToAddHabit = { selectedTab = MainTab.HABITS }
                    )
                }
                MainTab.STATISTICS -> {
                    StatisticsScreen(
                        viewModel = viewModel,
                        selectedHabit = selectedHabit,
                        stats = statisticsData,
                        relapses = relapsesList
                    )
                }
                MainTab.HABITS -> {
                    HabitsScreen(
                        viewModel = viewModel,
                        allHabits = activeHabits,
                        selectedHabitId = selectedHabitId
                    )
                }
                MainTab.PROFILE -> {
                    ProfileScreen(
                        viewModel = viewModel,
                        profile = userProfile,
                        stats = statisticsData
                    )
                }
            }
        }
    }
}
