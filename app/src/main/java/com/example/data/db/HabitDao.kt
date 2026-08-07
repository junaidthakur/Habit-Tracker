package com.example.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitDao {
    @Query("SELECT * FROM habits WHERE isArchived = 0 ORDER BY id ASC")
    fun getAllActiveHabits(): Flow<List<HabitEntity>>

    @Query("SELECT * FROM habits WHERE id = :id LIMIT 1")
    fun getHabitByIdFlow(id: Long): Flow<HabitEntity?>

    @Query("SELECT * FROM habits WHERE id = :id LIMIT 1")
    suspend fun getHabitById(id: Long): HabitEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHabit(habit: HabitEntity): Long

    @Update
    suspend fun updateHabit(habit: HabitEntity)

    @Query("UPDATE habits SET startDateTimestamp = :newStartTime WHERE id = :habitId")
    suspend fun resetHabitTimer(habitId: Long, newStartTime: Long = System.currentTimeMillis())

    @Query("DELETE FROM habits WHERE id = :id")
    suspend fun deleteHabit(id: Long)
}
