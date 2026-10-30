package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "level_progress")
data class LevelProgressEntity(
    @PrimaryKey val levelId: Int,
    val stars: Int = 0,
    val completed: Boolean = false,
    val bestTimeSeconds: Int = 0,
    val hintsUsed: Int = 0,
    val completedTimestamp: Long = 0L
)

@Entity(tableName = "user_stats")
data class UserStatsEntity(
    @PrimaryKey val id: Int = 1,
    val coins: Int = 150,
    val soundEnabled: Boolean = true,
    val hapticsEnabled: Boolean = true,
    val dailyStreak: Int = 0,
    val lastDailyCompletedDate: String = "",
    val totalHintsUsed: Int = 0,
    val totalWrongAnswers: Int = 0,
    val totalCorrectAnswers: Int = 0,
    val hasRemovedAds: Boolean = false
)

@Entity(tableName = "daily_progress")
data class DailyProgressEntity(
    @PrimaryKey val id: String, // "$dateString-$index"
    val dateString: String,
    val puzzleIndex: Int,
    val completed: Boolean = false
)

@Entity(tableName = "math_cross_progress")
data class MathCrossProgressEntity(
    @PrimaryKey val boardId: Int,
    val completed: Boolean = false,
    val bestTimeSeconds: Int = 0
)

@Entity(tableName = "achievements")
data class AchievementEntity(
    @PrimaryKey val id: String,
    val title: String,
    val description: String,
    val iconName: String,
    val currentProgress: Int,
    val targetProgress: Int,
    val unlocked: Boolean = false,
    val rewardClaimed: Boolean = false,
    val coinReward: Int = 50
)
