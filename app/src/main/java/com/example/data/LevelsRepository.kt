package com.example.data

import com.example.model.Difficulty
import com.example.model.LevelPuzzle

object LevelsRepository {
    val allLevels: List<LevelPuzzle> = levelsEasy + levelsNormal + levelsMedium + levelsHard + levelsExpert

    fun getLevel(id: Int): LevelPuzzle? {
        return allLevels.find { it.id == id }
    }

    fun getLevelsByDifficulty(difficulty: Difficulty): List<LevelPuzzle> {
        return allLevels.filter { it.difficulty == difficulty }
    }

    fun getNextLevelId(currentId: Int): Int? {
        return if (currentId < allLevels.size) currentId + 1 else null
    }

    const val TOTAL_LEVELS = 100
}
