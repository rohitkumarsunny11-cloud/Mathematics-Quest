package com.example

import com.example.data.DailyChallengeGenerator
import com.example.data.LevelsRepository
import com.example.data.MathCrossData
import com.example.model.Difficulty
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class MathQuestGameTest {

    @Test
    fun testAll100LevelsExistAndAreSequential() {
        val allLevels = LevelsRepository.allLevels
        assertEquals(100, allLevels.size)

        for (i in 1..100) {
            val level = LevelsRepository.getLevel(i)
            assertNotNull("Level $i should not be null", level)
            assertEquals(i, level?.id)
        }
    }

    @Test
    fun testDifficultyDistributions() {
        val easy = LevelsRepository.getLevelsByDifficulty(Difficulty.EASY)
        val normal = LevelsRepository.getLevelsByDifficulty(Difficulty.NORMAL)
        val medium = LevelsRepository.getLevelsByDifficulty(Difficulty.MEDIUM)
        val hard = LevelsRepository.getLevelsByDifficulty(Difficulty.HARD)
        val expert = LevelsRepository.getLevelsByDifficulty(Difficulty.EXPERT)

        assertEquals("Easy should have 20 levels", 20, easy.size)
        assertEquals("Normal should have 20 levels", 20, normal.size)
        assertEquals("Medium should have 20 levels", 20, medium.size)
        assertEquals("Hard should have 20 levels", 20, hard.size)
        assertEquals("Expert should have 20 levels", 20, expert.size)
    }

    @Test
    fun testEveryPuzzleAnswerValidationAndMetadata() {
        for (level in LevelsRepository.allLevels) {
            assertTrue("Level ${level.id} title should not be blank", level.title.isNotBlank())
            assertTrue("Level ${level.id} question should not be blank", level.question.isNotBlank())
            assertTrue("Level ${level.id} hint1 should not be blank", level.hint1.isNotBlank())
            assertTrue("Level ${level.id} hint2 should not be blank", level.hint2.isNotBlank())
            assertTrue("Level ${level.id} hint3 should not be blank", level.hint3.isNotBlank())
            assertTrue("Level ${level.id} explanation should not be blank", level.explanation.isNotBlank())
            assertTrue("Level ${level.id} rewardCoins should be positive", level.rewardCoins > 0)

            // Validate that the correctAnswer passes validation
            assertTrue(
                "Level ${level.id} correctAnswer '${level.correctAnswer}' must validate",
                level.isAnswerCorrect(level.correctAnswer)
            )

            // Test trimmed and case-insensitive check
            assertTrue(
                "Level ${level.id} with spaces should validate",
                level.isAnswerCorrect("  " + level.correctAnswer.lowercase() + "  ")
            )
        }
    }

    @Test
    fun testMathCrossBoardSolutions() {
        val boards = MathCrossData.boards
        assertTrue("Should have at least 5 Math-Cross boards", boards.size >= 5)

        for (board in boards) {
            val fullSolutionInputs = mutableMapOf<Pair<Int, Int>, String>()
            for (cell in board.cells) {
                if (cell.isEditable) {
                    fullSolutionInputs[Pair(cell.row, cell.col)] = cell.solutionValue.toString()
                }
            }
            assertTrue(
                "Board ${board.id} ('${board.title}') solution must satisfy all row and column equations",
                board.isSolved(fullSolutionInputs)
            )
        }
    }

    @Test
    fun testDailyChallengeDeterminismAndSize() {
        val testDate = "2026-10-29"
        val dailySet1 = DailyChallengeGenerator.getDailyPuzzlesForDate(testDate)
        val dailySet2 = DailyChallengeGenerator.getDailyPuzzlesForDate(testDate)

        assertEquals("Daily challenge should have exactly 10 puzzles", 10, dailySet1.size)
        for (i in 0 until 10) {
            assertEquals(
                "Daily puzzle $i must be identical across calls for the same date",
                dailySet1[i].puzzle.id,
                dailySet2[i].puzzle.id
            )
        }
    }

    @Test
    fun testAdConfigTestUnitsAndFormat() {
        val bannerId = com.example.ads.AdConfig.getBannerAdUnitId()
        val interstitialId = com.example.ads.AdConfig.getInterstitialAdUnitId()
        val rewardedId = com.example.ads.AdConfig.getRewardedAdUnitId()
        val rewardedInterstitialId = com.example.ads.AdConfig.getRewardedInterstitialAdUnitId()

        assertTrue("Banner unit ID must contain '/'", bannerId.contains("/"))
        assertTrue("Interstitial unit ID must contain '/'", interstitialId.contains("/"))
        assertTrue("Rewarded unit ID must contain '/'", rewardedId.contains("/"))
        assertTrue("Rewarded interstitial unit ID must contain '/'", rewardedInterstitialId.contains("/"))
    }

    @Test
    fun testBillingProductConstants() {
        assertEquals("remove_ads_99", com.example.billing.BillingManager.PRODUCT_REMOVE_ADS)
    }

    @Test
    fun testUserStatsRemoveAdsDefault() {
        val stats = com.example.data.local.UserStatsEntity()
        org.junit.Assert.assertFalse("User should not have removed ads by default", stats.hasRemovedAds)
        val updated = stats.copy(hasRemovedAds = true)
        assertTrue("Updated stats should reflect hasRemovedAds", updated.hasRemovedAds)
    }
}
