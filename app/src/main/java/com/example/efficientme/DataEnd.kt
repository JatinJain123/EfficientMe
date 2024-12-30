package com.example.efficientme

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Tasks")
data class Task(
    @PrimaryKey(autoGenerate = true) val taskId: Int? = null,
    val content: String,
    val deadline: Long,
    val priority: Int,
    val estimatedTime: String
)

@Entity(tableName = "Profile")
data class Profile(
    @PrimaryKey val profileId: Int = 1,
    val name: String,
    val sleepStartTime: String,
    val sleepEndTime: String,
    val breakfastStartTime: String,
    val breakfastEndTime: String,
    val lunchStartTime: String,
    val lunchEndTime: String,
    val dinnerStartTime: String,
    val dinnerEndTime: String,
)

@Entity(tableName = "Breaks")
data class Break(
    @PrimaryKey(autoGenerate = true) val breakId: Int,
    val type: String,
    val startTime: String,
    val endTime: String
)