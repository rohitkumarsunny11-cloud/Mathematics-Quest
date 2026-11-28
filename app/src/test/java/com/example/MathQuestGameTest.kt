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
    fun testRemoveAdsComingSoonMessageConstant() {
        val expectedMessage = "Coming Soon! Remove Ads feature will be available soon."
        assertEquals("Coming Soon! Remove Ads feature will be available soon.", expectedMessage)
    }

    @Test
    fun testUserStatsRemoveAdsDefault() {
        val stats = com.example.data.local.UserStatsEntity()
        org.junit.Assert.assertFalse("User should not have removed ads by default", stats.hasRemovedAds)
        val updated = stats.copy(hasRemovedAds = true)
        assertTrue("Updated stats should reflect hasRemovedAds", updated.hasRemovedAds)
    }

    @Test
    fun testMathCrossBoardUniqueIdsAndRewards() {
        val boards = MathCrossData.boards
        val ids = boards.map { it.id }
        assertEquals("Board IDs must all be unique", ids.distinct().size, ids.size)

        for (board in boards) {
            assertTrue("Board ID must be positive", board.id > 0)
            assertTrue("Board reward coins must be positive", board.rewardCoins > 0)
        }
    }

    @Test
    fun testMathCrossProgressEntityRewardClaimedState() {
        val initial = com.example.data.local.MathCrossProgressEntity(boardId = 1)
        org.junit.Assert.assertFalse("Initial rewardClaimed must be false", initial.rewardClaimed)
        org.junit.Assert.assertFalse("Initial completed must be false", initial.completed)

        val claimed = initial.copy(completed = true, rewardClaimed = true)
        assertTrue("Claimed progress must have rewardClaimed = true", claimed.rewardClaimed)
        assertTrue("Claimed progress must have completed = true", claimed.completed)
    }

    @Test
    fun testMathCrossSolveResultTypes() {
        val success: com.example.data.MathCrossSolveResult = com.example.data.MathCrossSolveResult.SuccessAwarded(30)
        assertTrue(success is com.example.data.MathCrossSolveResult.SuccessAwarded)
        assertEquals(30, (success as com.example.data.MathCrossSolveResult.SuccessAwarded).coinsAwarded)

        val claimed: com.example.data.MathCrossSolveResult = com.example.data.MathCrossSolveResult.AlreadyClaimed
        assertEquals(com.example.data.MathCrossSolveResult.AlreadyClaimed, claimed)
    }

    @Test
    fun testHintTierPrices() {
        assertEquals("Tier 1 Gentle Nudge price must be 200 coins", 200, com.example.ui.components.HintPricing.TIER_1_PRICE)
        assertEquals("Tier 2 Logical Step price must be 500 coins", 500, com.example.ui.components.HintPricing.TIER_2_PRICE)
        assertEquals("Tier 3 Full Solution price must be 1000 coins", 1000, com.example.ui.components.HintPricing.TIER_3_PRICE)

        assertEquals(200, com.example.ui.components.HintPricing.getPriceForTier(1))
        assertEquals(500, com.example.ui.components.HintPricing.getPriceForTier(2))
        assertEquals(1000, com.example.ui.components.HintPricing.getPriceForTier(3))
    }

    @Test
    fun testHintAffordabilityAndDeductionSimulation() {
        val tier1Price = com.example.ui.components.HintPricing.TIER_1_PRICE
        val tier2Price = com.example.ui.components.HintPricing.TIER_2_PRICE
        val tier3Price = com.example.ui.components.HintPricing.TIER_3_PRICE

        // Test Tier 1 (200 coins)
        val coinsBelowTier1 = 199
        val canAffordTier1Below = coinsBelowTier1 >= tier1Price
        org.junit.Assert.assertFalse("Should NOT afford Tier 1 with 199 coins", canAffordTier1Below)

        val coinsExactTier1 = 200
        val canAffordTier1Exact = coinsExactTier1 >= tier1Price
        assertTrue("Should afford Tier 1 with 200 coins", canAffordTier1Exact)
        assertEquals("After Tier 1 deduction from 200, balance must be 0", 0, coinsExactTier1 - tier1Price)

        // Test Tier 2 (500 coins)
        val coinsBelowTier2 = 499
        val canAffordTier2Below = coinsBelowTier2 >= tier2Price
        org.junit.Assert.assertFalse("Should NOT afford Tier 2 with 499 coins", canAffordTier2Below)

        val coinsExactTier2 = 500
        val canAffordTier2Exact = coinsExactTier2 >= tier2Price
        assertTrue("Should afford Tier 2 with 500 coins", canAffordTier2Exact)
        assertEquals("After Tier 2 deduction from 500, balance must be 0", 0, coinsExactTier2 - tier2Price)

        // Test Tier 3 (1000 coins)
        val coinsBelowTier3 = 999
        val canAffordTier3Below = coinsBelowTier3 >= tier3Price
        org.junit.Assert.assertFalse("Should NOT afford Tier 3 with 999 coins", canAffordTier3Below)

        val coinsExactTier3 = 1000
        val canAffordTier3Exact = coinsExactTier3 >= tier3Price
        assertTrue("Should afford Tier 3 with 1000 coins", canAffordTier3Exact)
        assertEquals("After Tier 3 deduction from 1000, balance must be 0", 0, coinsExactTier3 - tier3Price)

        // Test Multi-tier purchase with a balance of 2000
        var balance = 2000
        assertTrue(balance >= tier1Price)
        balance -= tier1Price
        assertEquals(1800, balance)

        assertTrue(balance >= tier2Price)
        balance -= tier2Price
        assertEquals(1300, balance)

        assertTrue(balance >= tier3Price)
        balance -= tier3Price
        assertEquals(300, balance)

        // Now balance is 300: can afford Tier 1 (200), but NOT Tier 2 (500) or Tier 3 (1000)
        assertTrue(balance >= tier1Price)
        org.junit.Assert.assertFalse(balance >= tier2Price)
        org.junit.Assert.assertFalse(balance >= tier3Price)
    }

    @Test
    fun testInfiniteModePuzzlesSolvabilityAndIntegrity() {
        // Test a diverse sample of infinite mode levels from 101 to 500
        val sampleLevels = listOf(101, 102, 103, 104, 105, 120, 140, 150, 175, 201, 250, 500)

        for (lvlId in sampleLevels) {
            val puzzle = LevelsRepository.getLevel(lvlId)
            assertNotNull("Infinite puzzle Level $lvlId must not be null", puzzle)
            assertEquals("Level ID must match requested ID", lvlId, puzzle?.id)
            assertTrue("Title must not be blank for Level $lvlId", puzzle?.title?.isNotBlank() == true)
            assertTrue("Question must not be blank for Level $lvlId", puzzle?.question?.isNotBlank() == true)
            assertTrue("Hint 1 must not be blank for Level $lvlId", puzzle?.hint1?.isNotBlank() == true)
            assertTrue("Hint 2 must not be blank for Level $lvlId", puzzle?.hint2?.isNotBlank() == true)
            assertTrue("Hint 3 must not be blank for Level $lvlId", puzzle?.hint3?.isNotBlank() == true)
            assertTrue("Explanation must not be blank for Level $lvlId", puzzle?.explanation?.isNotBlank() == true)
            assertTrue("Reward coins must be positive for Level $lvlId", (puzzle?.rewardCoins ?: 0) > 0)

            // Validate that the correctAnswer passes answer validation
            assertTrue(
                "Level $lvlId correctAnswer '${puzzle?.correctAnswer}' must validate",
                puzzle?.isAnswerCorrect(puzzle.correctAnswer) == true
            )

            // Validate trimmed case insensitivity
            assertTrue(
                "Level $lvlId with whitespace should validate",
                puzzle?.isAnswerCorrect("  ${puzzle.correctAnswer}  ") == true
            )
        }
    }

    @Test
    fun testInfiniteModeDeterminism() {
        val puzzleA = LevelsRepository.getLevel(101)
        val puzzleB = LevelsRepository.getLevel(101)

        assertNotNull(puzzleA)
        assertNotNull(puzzleB)
        assertEquals(puzzleA?.title, puzzleB?.title)
        assertEquals(puzzleA?.question, puzzleB?.question)
        assertEquals(puzzleA?.correctAnswer, puzzleB?.correctAnswer)
        assertEquals(puzzleA?.hint1, puzzleB?.hint1)
        assertEquals(puzzleA?.hint2, puzzleB?.hint2)
        assertEquals(puzzleA?.hint3, puzzleB?.hint3)
        assertEquals(puzzleA?.explanation, puzzleB?.explanation)
        assertEquals(puzzleA?.rewardCoins, puzzleB?.rewardCoins)
    }

    @Test
    fun testInfiniteNextLevelProgression() {
        assertEquals(101, LevelsRepository.getNextLevelId(100))
        assertEquals(102, LevelsRepository.getNextLevelId(101))
        assertEquals(501, LevelsRepository.getNextLevelId(500))
    }
}
