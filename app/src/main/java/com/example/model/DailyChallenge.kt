package com.example.model

data class DailyPuzzle(
    val index: Int, // 0 to 9
    val dateString: String,
    val puzzle: LevelPuzzle,
    val completed: Boolean = false
)

data class DailyChallengeSummary(
    val dateString: String,
    val totalCount: Int = 10,
    val completedCount: Int,
    val streak: Int,
    val allCompleted: Boolean
)
