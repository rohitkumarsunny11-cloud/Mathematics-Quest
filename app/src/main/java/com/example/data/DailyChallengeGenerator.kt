package com.example.data

import com.example.model.DailyPuzzle
import com.example.model.Difficulty
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.random.Random

object DailyChallengeGenerator {

    fun getTodayDateString(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        return sdf.format(Date())
    }

    fun getDailyPuzzlesForDate(dateString: String): List<DailyPuzzle> {
        val seed = dateString.hashCode().toLong()
        val random = Random(seed)

        val easyPuzzles = LevelsRepository.getLevelsByDifficulty(Difficulty.EASY).shuffled(random)
        val normalPuzzles = LevelsRepository.getLevelsByDifficulty(Difficulty.NORMAL).shuffled(random)
        val mediumPuzzles = LevelsRepository.getLevelsByDifficulty(Difficulty.MEDIUM).shuffled(random)
        val hardPuzzles = LevelsRepository.getLevelsByDifficulty(Difficulty.HARD).shuffled(random)
        val expertPuzzles = LevelsRepository.getLevelsByDifficulty(Difficulty.EXPERT).shuffled(random)

        val selected = mutableListOf<com.example.model.LevelPuzzle>()
        selected.addAll(easyPuzzles.take(2))
        selected.addAll(normalPuzzles.take(3))
        selected.addAll(mediumPuzzles.take(2))
        selected.addAll(hardPuzzles.take(2))
        selected.addAll(expertPuzzles.take(1))

        return selected.mapIndexed { idx, pz ->
            DailyPuzzle(
                index = idx,
                dateString = dateString,
                puzzle = pz,
                completed = false
            )
        }
    }
}
