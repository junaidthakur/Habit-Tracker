package com.example.data.repository

import com.example.data.db.HabitDao
import com.example.data.db.HabitEntity
import com.example.data.db.RelapseDao
import com.example.data.db.RelapseEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull

class HabitRepository(
    private val habitDao: HabitDao,
    private val relapseDao: RelapseDao
) {
    val allActiveHabits: Flow<List<HabitEntity>> = habitDao.getAllActiveHabits()

    suspend fun ensureDefaultHabitExists(): Long {
        val habits = habitDao.getAllActiveHabits().firstOrNull()
        if (habits.isNullOrEmpty()) {
            val defaultHabit = HabitEntity(
                title = "Quit Smoking",
                description = "Journey towards a healthier smoke-free life",
                category = "Health",
                startDateTimestamp = System.currentTimeMillis(),
                iconName = "smoking",
                themeColorHex = "#2D6A4F"
            )
            return habitDao.insertHabit(defaultHabit)
        }
        return habits.first().id
    }

    fun getHabit(id: Long): Flow<HabitEntity?> = habitDao.getHabitByIdFlow(id)

    fun getRelapses(habitId: Long): Flow<List<RelapseEntity>> = relapseDao.getRelapsesForHabit(habitId)

    suspend fun addNewHabit(title: String, description: String, category: String, iconName: String): Long {
        val habit = HabitEntity(
            title = title,
            description = description,
            category = category,
            startDateTimestamp = System.currentTimeMillis(),
            iconName = iconName
        )
        return habitDao.insertHabit(habit)
    }

    suspend fun recordRelapse(
        habitId: Long,
        relapseTimestamp: Long,
        daysAchieved: Double,
        reason: String,
        notes: String,
        triggerCategory: String
    ) {
        val relapse = RelapseEntity(
            habitId = habitId,
            timestamp = relapseTimestamp,
            streakDaysAchieved = daysAchieved,
            reason = reason,
            triggerCategory = triggerCategory,
            notes = notes
        )
        relapseDao.insertRelapse(relapse)
        // Reset the habit start timer to relapse time
        habitDao.resetHabitTimer(habitId, relapseTimestamp)
    }

    suspend fun updateHabit(habit: HabitEntity) {
        habitDao.updateHabit(habit)
    }

    suspend fun deleteHabit(habitId: Long) {
        relapseDao.deleteAllRelapsesForHabit(habitId)
        habitDao.deleteHabit(habitId)
    }

    suspend fun manualResetTimer(habitId: Long) {
        habitDao.resetHabitTimer(habitId, System.currentTimeMillis())
    }
}
