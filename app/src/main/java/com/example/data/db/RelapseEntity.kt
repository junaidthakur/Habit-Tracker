package com.example.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "relapses")
data class RelapseEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val habitId: Long,
    val timestamp: Long = System.currentTimeMillis(),
    val streakDaysAchieved: Double, // Number of days streak held before relapse
    val reason: String, // e.g. মানসিক চাপ (Stress), একঘেয়েমি (Boredom), প্ররোচনা (Peer pressure)
    val triggerCategory: String = "অন্যান্য",
    val notes: String = ""
)
