package com.example.data

import com.example.model.Difficulty
import com.example.model.LevelPuzzle

object LevelsRepository {
    val allLevels: List<LevelPuzzle> = levelsEasy + levelsNormal + levelsMedium + levelsHard + levelsExpert

    fun getLevel(id: Int): LevelPuzzle? {
        return if (id in 1..TOTAL_LEVELS) {
            allLevels.find { it.id == id }
        } else if (id > TOTAL_LEVELS) {
            InfiniteLevelGenerator.generateLevel(id)
        } else {
            null
        }
    }

    fun getLevelsByDifficulty(difficulty: Difficulty): List<LevelPuzzle> {
        return allLevels.filter { it.difficulty == difficulty }
    }

    fun getNextLevelId(currentId: Int): Int? {
        return currentId + 1
    }

    const val TOTAL_LEVELS = 100
}
