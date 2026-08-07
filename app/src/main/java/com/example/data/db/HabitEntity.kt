package com.example.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "habits")
data class HabitEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val description: String = "",
    val category: String = "সাধারণ", // e.g. ধূমপান, সোশ্যাল মিডিয়া, ফাস্ট ফুড
    val startDateTimestamp: Long = System.currentTimeMillis(),
    val isArchived: Boolean = false,
    val iconName: String = "smoking",
    val themeColorHex: String = "#2D6A4F"
)
