package com.example.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RelapseDao {
    @Query("SELECT * FROM relapses WHERE habitId = :habitId ORDER BY timestamp DESC")
    fun getRelapsesForHabit(habitId: Long): Flow<List<RelapseEntity>>

    @Query("SELECT * FROM relapses WHERE habitId = :habitId ORDER BY timestamp DESC")
    suspend fun getRelapsesListForHabit(habitId: Long): List<RelapseEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRelapse(relapse: RelapseEntity): Long

    @Query("DELETE FROM relapses WHERE habitId = :habitId")
    suspend fun deleteAllRelapsesForHabit(habitId: Long)
}
