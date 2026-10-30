package com.example.model

enum class Difficulty(val displayName: String, val defaultReward: Int) {
    EASY("Easy", 20),
    NORMAL("Normal", 30),
    MEDIUM("Medium", 40),
    HARD("Hard", 50),
    EXPERT("Expert", 75)
}

enum class PuzzleType {
    SEQUENCE,
    SHAPE_ALGEBRA,
    PYRAMID,
    EQUATION,
    LOGIC_GRID,
    CRYPTARITHM,
    CLOCK_MATH,
    RIDDLE,
    MATRIX,
    OPERATOR
}

sealed class VisualData {
    data class SequenceVisual(val items: List<String>) : VisualData()
    data class ShapeAlgebraVisual(val equations: List<String>, val questionLine: String) : VisualData()
    data class PyramidVisual(val rows: List<List<String>>) : VisualData()
    data class MatrixVisual(val grid: List<List<String>>) : VisualData()
    data class ClockVisual(val times: List<String>) : VisualData()
    data class BalanceVisual(val leftSide: String, val rightSide: String, val queryLine: String) : VisualData()
    data class SimpleVisual(val promptText: String) : VisualData()
}

data class LevelPuzzle(
    val id: Int,
    val difficulty: Difficulty,
    val type: PuzzleType,
    val title: String,
    val question: String,
    val visual: VisualData,
    val correctAnswer: String,
    val acceptedAnswers: List<String> = listOf(correctAnswer),
    val hint1: String,
    val hint2: String,
    val hint3: String,
    val explanation: String,
    val rewardCoins: Int = difficulty.defaultReward
) {
    fun isAnswerCorrect(input: String): Boolean {
        val trimmed = input.trim().lowercase()
        return acceptedAnswers.any { it.trim().lowercase() == trimmed }
    }
}
