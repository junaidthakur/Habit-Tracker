package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.db.AppDatabase
import com.example.data.db.HabitEntity
import com.example.data.db.RelapseEntity
import com.example.data.repository.HabitRepository
import com.example.ui.model.PlantStage
import com.example.ui.model.UserProfile
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.math.max

data class TimeElapsed(
    val days: Long = 0,
    val hours: Long = 0,
    val minutes: Long = 0,
    val seconds: Long = 0,
    val totalDaysDouble: Double = 0.0
)

data class StatisticsData(
    val currentStreakDays: Double = 0.0,
    val longestStreakDays: Double = 0.0,
    val totalRelapses: Int = 0,
    val averageStreakDays: Double = 0.0,
    val triggerBreakdown: Map<String, Int> = emptyMap()
)

class HabitViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: HabitRepository

    val activeHabits: StateFlow<List<HabitEntity>>

    private val _selectedHabitId = MutableStateFlow<Long?>(null)
    val selectedHabitId: StateFlow<Long?> = _selectedHabitId.asStateFlow()

    private val _selectedHabit = MutableStateFlow<HabitEntity?>(null)
    val selectedHabit: StateFlow<HabitEntity?> = _selectedHabit.asStateFlow()

    private val _relapsesList = MutableStateFlow<List<RelapseEntity>>(emptyList())
    val relapsesList: StateFlow<List<RelapseEntity>> = _relapsesList.asStateFlow()

    private val _timeElapsed = MutableStateFlow(TimeElapsed())
    val timeElapsed: StateFlow<TimeElapsed> = _timeElapsed.asStateFlow()

    private val _currentPlantStage = MutableStateFlow<PlantStage>(PlantStage.Seed)
    val currentPlantStage: StateFlow<PlantStage> = _currentPlantStage.asStateFlow()

    private val _statistics = MutableStateFlow(StatisticsData())
    val statistics: StateFlow<StatisticsData> = _statistics.asStateFlow()

    private val _userProfile = MutableStateFlow(UserProfile())
    val userProfile: StateFlow<UserProfile> = _userProfile.asStateFlow()

    init {
        val db = AppDatabase.getInstance(application)
        repository = HabitRepository(db.habitDao(), db.relapseDao())

        activeHabits = repository.allActiveHabits
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )

        viewModelScope.launch {
            val defaultId = repository.ensureDefaultHabitExists()
            _selectedHabitId.value = defaultId
        }

        // Observe selected habit ID change
        viewModelScope.launch {
            _selectedHabitId.collectLatest { habitId ->
                if (habitId != null) {
                    launch {
                        repository.getHabit(habitId).collectLatest { habit ->
                            _selectedHabit.value = habit
                        }
                    }
                    launch {
                        repository.getRelapses(habitId).collectLatest { relapses ->
                            _relapsesList.value = relapses
                            recalculateStatistics()
                        }
                    }
                }
            }
        }

        // Ticker coroutine for live timer updating every second
        viewModelScope.launch {
            while (true) {
                updateTimer()
                delay(1000L)
            }
        }
    }

    fun selectHabit(id: Long) {
        _selectedHabitId.value = id
    }

    private fun updateTimer() {
        val habit = _selectedHabit.value ?: return
        val now = System.currentTimeMillis()
        val start = habit.startDateTimestamp

        val diffMillis = max(0L, now - start)

        val totalSeconds = diffMillis / 1000
        val days = totalSeconds / (24 * 3600)
        val hours = (totalSeconds % (24 * 3600)) / 3600
        val minutes = (totalSeconds % 3600) / 60
        val seconds = totalSeconds % 60

        val totalDaysDouble = diffMillis.toDouble() / (1000.0 * 60.0 * 60.0 * 24.0)

        _timeElapsed.value = TimeElapsed(
            days = days,
            hours = hours,
            minutes = minutes,
            seconds = seconds,
            totalDaysDouble = totalDaysDouble
        )

        val stage = PlantStage.getStageForDays(totalDaysDouble)
        _currentPlantStage.value = stage

        recalculateStatistics()
    }

    private fun recalculateStatistics() {
        val currentStreak = _timeElapsed.value.totalDaysDouble
        val relapses = _relapsesList.value

        var longest = currentStreak
        var totalStreakSum = currentStreak
        val triggers = mutableMapOf<String, Int>()

        relapses.forEach { relapse ->
            if (relapse.streakDaysAchieved > longest) {
                longest = relapse.streakDaysAchieved
            }
            totalStreakSum += relapse.streakDaysAchieved
            val r = relapse.reason.ifBlank { "অন্যান্য (Other)" }
            triggers[r] = (triggers[r] ?: 0) + 1
        }

        val totalCount = relapses.size
        val avgStreak = if (totalCount + 1 > 0) totalStreakSum / (totalCount + 1) else 0.0

        _statistics.value = StatisticsData(
            currentStreakDays = currentStreak,
            longestStreakDays = longest,
            totalRelapses = totalCount,
            averageStreakDays = avgStreak,
            triggerBreakdown = triggers
        )
    }

    fun recordRelapse(reason: String, notes: String, triggerCategory: String) {
        val habit = _selectedHabit.value ?: return
        val daysAchieved = _timeElapsed.value.totalDaysDouble
        val now = System.currentTimeMillis()

        viewModelScope.launch {
            repository.recordRelapse(
                habitId = habit.id,
                relapseTimestamp = now,
                daysAchieved = daysAchieved,
                reason = reason,
                notes = notes,
                triggerCategory = triggerCategory
            )
            updateTimer()
        }
    }

    fun createNewHabit(title: String, description: String, category: String, iconName: String) {
        viewModelScope.launch {
            val newId = repository.addNewHabit(title, description, category, iconName)
            _selectedHabitId.value = newId
        }
    }

    fun deleteCurrentHabit() {
        val habit = _selectedHabit.value ?: return
        viewModelScope.launch {
            repository.deleteHabit(habit.id)
            val habits = activeHabits.value.filter { it.id != habit.id }
            if (habits.isNotEmpty()) {
                _selectedHabitId.value = habits.first().id
            } else {
                val newId = repository.ensureDefaultHabitExists()
                _selectedHabitId.value = newId
            }
        }
    }

    fun manualResetTimer() {
        val habit = _selectedHabit.value ?: return
        viewModelScope.launch {
            repository.manualResetTimer(habit.id)
            updateTimer()
        }
    }

    // --- Profile & Rewards Unlock System Methods ---

    fun updateLanguage(langCode: String) {
        _userProfile.value = _userProfile.value.copy(language = langCode)
    }

    fun updateProfileNameAndBio(name: String, bio: String) {
        val lang = _userProfile.value.language
        val defaultName = if (lang == "bn") "সবুজ যাত্রী" else "Green Voyager"
        val defaultBio = if (lang == "bn") "ক্ষতিকর অভ্যাস ত্যাগ করার সংকল্প।" else "Committed to breaking bad habits & growing a healthier life."
        _userProfile.value = _userProfile.value.copy(
            userName = name.ifBlank { defaultName },
            userBio = bio.ifBlank { defaultBio }
        )
    }

    fun selectAvatar(avatarId: String) {
        _userProfile.value = _userProfile.value.copy(selectedAvatarId = avatarId)
    }

    fun selectTheme(themeId: String) {
        _userProfile.value = _userProfile.value.copy(selectedThemeId = themeId)
    }

    fun selectPlantSkin(skinId: String) {
        _userProfile.value = _userProfile.value.copy(selectedPlantSkinId = skinId)
    }

    fun unlockItemWithAd(itemId: String) {
        val currentSet = _userProfile.value.unlockedItemIds.toMutableSet()
        currentSet.add(itemId)
        _userProfile.value = _userProfile.value.copy(unlockedItemIds = currentSet)
    }
}
