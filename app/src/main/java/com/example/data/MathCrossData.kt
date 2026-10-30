package com.example.data

import com.example.model.CrossCellType
import com.example.model.CrossEquation
import com.example.model.Difficulty
import com.example.model.MathCrossBoard
import com.example.model.MathCrossCell

object MathCrossData {

    private fun standard5x5Equations(): List<CrossEquation> = listOf(
        CrossEquation(isRow = true, index = 0, leftRow = 0, leftCol = 0, opRow = 0, opCol = 1, rightRow = 0, rightCol = 2, resultRow = 0, resultCol = 4),
        CrossEquation(isRow = true, index = 2, leftRow = 2, leftCol = 0, opRow = 2, opCol = 1, rightRow = 2, rightCol = 2, resultRow = 2, resultCol = 4),
        CrossEquation(isRow = false, index = 0, leftRow = 0, leftCol = 0, opRow = 1, opCol = 0, rightRow = 2, rightCol = 0, resultRow = 4, resultCol = 0),
        CrossEquation(isRow = false, index = 2, leftRow = 0, leftCol = 2, opRow = 1, opCol = 2, rightRow = 2, rightCol = 2, resultRow = 4, resultCol = 2)
    )

    val boards: List<MathCrossBoard> = listOf(
        // Board 1: Easy
        // 4 + [5] = 9
        // +    *
        // [3] * 2 = 6
        // =    =
        // 7   10
        MathCrossBoard(
            id = 1,
            title = "Cross Starter",
            difficulty = Difficulty.EASY,
            rows = 5,
            cols = 5,
            rewardCoins = 30,
            equations = standard5x5Equations(),
            cells = listOf(
                MathCrossCell(0, 0, CrossCellType.NUMBER_FIXED, initialText = "4", solutionValue = 4),
                MathCrossCell(0, 1, CrossCellType.OPERATOR, initialText = "+"),
                MathCrossCell(0, 2, CrossCellType.NUMBER_INPUT, initialText = "", solutionValue = 5),
                MathCrossCell(0, 3, CrossCellType.EQUALS, initialText = "="),
                MathCrossCell(0, 4, CrossCellType.NUMBER_FIXED, initialText = "9", solutionValue = 9),

                MathCrossCell(1, 0, CrossCellType.OPERATOR, initialText = "+"),
                MathCrossCell(1, 1, CrossCellType.EMPTY),
                MathCrossCell(1, 2, CrossCellType.OPERATOR, initialText = "*"),
                MathCrossCell(1, 3, CrossCellType.EMPTY),
                MathCrossCell(1, 4, CrossCellType.EMPTY),

                MathCrossCell(2, 0, CrossCellType.NUMBER_INPUT, initialText = "", solutionValue = 3),
                MathCrossCell(2, 1, CrossCellType.OPERATOR, initialText = "*"),
                MathCrossCell(2, 2, CrossCellType.NUMBER_FIXED, initialText = "2", solutionValue = 2),
                MathCrossCell(2, 3, CrossCellType.EQUALS, initialText = "="),
                MathCrossCell(2, 4, CrossCellType.NUMBER_FIXED, initialText = "6", solutionValue = 6),

                MathCrossCell(3, 0, CrossCellType.EQUALS, initialText = "="),
                MathCrossCell(3, 1, CrossCellType.EMPTY),
                MathCrossCell(3, 2, CrossCellType.EQUALS, initialText = "="),
                MathCrossCell(3, 3, CrossCellType.EMPTY),
                MathCrossCell(3, 4, CrossCellType.EMPTY),

                MathCrossCell(4, 0, CrossCellType.NUMBER_FIXED, initialText = "7", solutionValue = 7),
                MathCrossCell(4, 1, CrossCellType.EMPTY),
                MathCrossCell(4, 2, CrossCellType.NUMBER_FIXED, initialText = "10", solutionValue = 10),
                MathCrossCell(4, 3, CrossCellType.EMPTY),
                MathCrossCell(4, 4, CrossCellType.EMPTY)
            )
        ),

        // Board 2: Normal
        // [8] - 3 = 5
        // /    +
        // 2 + [7] = 9
        // =    =
        // 4   10
        MathCrossBoard(
            id = 2,
            title = "Dual Path",
            difficulty = Difficulty.NORMAL,
            rows = 5,
            cols = 5,
            rewardCoins = 40,
            equations = standard5x5Equations(),
            cells = listOf(
                MathCrossCell(0, 0, CrossCellType.NUMBER_INPUT, initialText = "", solutionValue = 8),
                MathCrossCell(0, 1, CrossCellType.OPERATOR, initialText = "-"),
                MathCrossCell(0, 2, CrossCellType.NUMBER_FIXED, initialText = "3", solutionValue = 3),
                MathCrossCell(0, 3, CrossCellType.EQUALS, initialText = "="),
                MathCrossCell(0, 4, CrossCellType.NUMBER_FIXED, initialText = "5", solutionValue = 5),

                MathCrossCell(1, 0, CrossCellType.OPERATOR, initialText = "/"),
                MathCrossCell(1, 1, CrossCellType.EMPTY),
                MathCrossCell(1, 2, CrossCellType.OPERATOR, initialText = "+"),
                MathCrossCell(1, 3, CrossCellType.EMPTY),
                MathCrossCell(1, 4, CrossCellType.EMPTY),

                MathCrossCell(2, 0, CrossCellType.NUMBER_FIXED, initialText = "2", solutionValue = 2),
                MathCrossCell(2, 1, CrossCellType.OPERATOR, initialText = "+"),
                MathCrossCell(2, 2, CrossCellType.NUMBER_INPUT, initialText = "", solutionValue = 7),
                MathCrossCell(2, 3, CrossCellType.EQUALS, initialText = "="),
                MathCrossCell(2, 4, CrossCellType.NUMBER_FIXED, initialText = "9", solutionValue = 9),

                MathCrossCell(3, 0, CrossCellType.EQUALS, initialText = "="),
                MathCrossCell(3, 1, CrossCellType.EMPTY),
                MathCrossCell(3, 2, CrossCellType.EQUALS, initialText = "="),
                MathCrossCell(3, 3, CrossCellType.EMPTY),
                MathCrossCell(3, 4, CrossCellType.EMPTY),

                MathCrossCell(4, 0, CrossCellType.NUMBER_FIXED, initialText = "4", solutionValue = 4),
                MathCrossCell(4, 1, CrossCellType.EMPTY),
                MathCrossCell(4, 2, CrossCellType.NUMBER_FIXED, initialText = "10", solutionValue = 10),
                MathCrossCell(4, 3, CrossCellType.EMPTY),
                MathCrossCell(4, 4, CrossCellType.EMPTY)
            )
        ),

        // Board 3: Medium
        // 6 * [4] = 24
        // -    -
        // [2] + 3 = [5]
        // =    =
        // 4    1
        MathCrossBoard(
            id = 3,
            title = "Triple Intersect",
            difficulty = Difficulty.MEDIUM,
            rows = 5,
            cols = 5,
            rewardCoins = 50,
            equations = standard5x5Equations(),
            cells = listOf(
                MathCrossCell(0, 0, CrossCellType.NUMBER_FIXED, initialText = "6", solutionValue = 6),
                MathCrossCell(0, 1, CrossCellType.OPERATOR, initialText = "*"),
                MathCrossCell(0, 2, CrossCellType.NUMBER_INPUT, initialText = "", solutionValue = 4),
                MathCrossCell(0, 3, CrossCellType.EQUALS, initialText = "="),
                MathCrossCell(0, 4, CrossCellType.NUMBER_FIXED, initialText = "24", solutionValue = 24),

                MathCrossCell(1, 0, CrossCellType.OPERATOR, initialText = "-"),
                MathCrossCell(1, 1, CrossCellType.EMPTY),
                MathCrossCell(1, 2, CrossCellType.OPERATOR, initialText = "-"),
                MathCrossCell(1, 3, CrossCellType.EMPTY),
                MathCrossCell(1, 4, CrossCellType.EMPTY),

                MathCrossCell(2, 0, CrossCellType.NUMBER_INPUT, initialText = "", solutionValue = 2),
                MathCrossCell(2, 1, CrossCellType.OPERATOR, initialText = "+"),
                MathCrossCell(2, 2, CrossCellType.NUMBER_FIXED, initialText = "3", solutionValue = 3),
                MathCrossCell(2, 3, CrossCellType.EQUALS, initialText = "="),
                MathCrossCell(2, 4, CrossCellType.NUMBER_INPUT, initialText = "", solutionValue = 5),

                MathCrossCell(3, 0, CrossCellType.EQUALS, initialText = "="),
                MathCrossCell(3, 1, CrossCellType.EMPTY),
                MathCrossCell(3, 2, CrossCellType.EQUALS, initialText = "="),
                MathCrossCell(3, 3, CrossCellType.EMPTY),
                MathCrossCell(3, 4, CrossCellType.EMPTY),

                MathCrossCell(4, 0, CrossCellType.NUMBER_FIXED, initialText = "4", solutionValue = 4),
                MathCrossCell(4, 1, CrossCellType.EMPTY),
                MathCrossCell(4, 2, CrossCellType.NUMBER_FIXED, initialText = "1", solutionValue = 1),
                MathCrossCell(4, 3, CrossCellType.EMPTY),
                MathCrossCell(4, 4, CrossCellType.EMPTY)
            )
        ),

        // Board 4: Hard
        // [9] + 6 = [15]
        // -    /
        // 5 * [2] = 10
        // =    =
        // [4]  3
        MathCrossBoard(
            id = 4,
            title = "Factor Cross",
            difficulty = Difficulty.HARD,
            rows = 5,
            cols = 5,
            rewardCoins = 60,
            equations = standard5x5Equations(),
            cells = listOf(
                MathCrossCell(0, 0, CrossCellType.NUMBER_INPUT, initialText = "", solutionValue = 9),
                MathCrossCell(0, 1, CrossCellType.OPERATOR, initialText = "+"),
                MathCrossCell(0, 2, CrossCellType.NUMBER_FIXED, initialText = "6", solutionValue = 6),
                MathCrossCell(0, 3, CrossCellType.EQUALS, initialText = "="),
                MathCrossCell(0, 4, CrossCellType.NUMBER_INPUT, initialText = "", solutionValue = 15),

                MathCrossCell(1, 0, CrossCellType.OPERATOR, initialText = "-"),
                MathCrossCell(1, 1, CrossCellType.EMPTY),
                MathCrossCell(1, 2, CrossCellType.OPERATOR, initialText = "/"),
                MathCrossCell(1, 3, CrossCellType.EMPTY),
                MathCrossCell(1, 4, CrossCellType.EMPTY),

                MathCrossCell(2, 0, CrossCellType.NUMBER_FIXED, initialText = "5", solutionValue = 5),
                MathCrossCell(2, 1, CrossCellType.OPERATOR, initialText = "*"),
                MathCrossCell(2, 2, CrossCellType.NUMBER_INPUT, initialText = "", solutionValue = 2),
                MathCrossCell(2, 3, CrossCellType.EQUALS, initialText = "="),
                MathCrossCell(2, 4, CrossCellType.NUMBER_FIXED, initialText = "10", solutionValue = 10),

                MathCrossCell(3, 0, CrossCellType.EQUALS, initialText = "="),
                MathCrossCell(3, 1, CrossCellType.EMPTY),
                MathCrossCell(3, 2, CrossCellType.EQUALS, initialText = "="),
                MathCrossCell(3, 3, CrossCellType.EMPTY),
                MathCrossCell(3, 4, CrossCellType.EMPTY),

                MathCrossCell(4, 0, CrossCellType.NUMBER_INPUT, initialText = "", solutionValue = 4),
                MathCrossCell(4, 1, CrossCellType.EMPTY),
                MathCrossCell(4, 2, CrossCellType.NUMBER_FIXED, initialText = "3", solutionValue = 3),
                MathCrossCell(4, 3, CrossCellType.EMPTY),
                MathCrossCell(4, 4, CrossCellType.EMPTY)
            )
        ),

        // Board 5: Expert
        // [7] * 3 = 21
        // +    +
        // [9] - [4] = 5
        // =    =
        // 16   7
        MathCrossBoard(
            id = 5,
            title = "The Cross Nexus",
            difficulty = Difficulty.EXPERT,
            rows = 5,
            cols = 5,
            rewardCoins = 80,
            equations = standard5x5Equations(),
            cells = listOf(
                MathCrossCell(0, 0, CrossCellType.NUMBER_INPUT, initialText = "", solutionValue = 7),
                MathCrossCell(0, 1, CrossCellType.OPERATOR, initialText = "*"),
                MathCrossCell(0, 2, CrossCellType.NUMBER_FIXED, initialText = "3", solutionValue = 3),
                MathCrossCell(0, 3, CrossCellType.EQUALS, initialText = "="),
                MathCrossCell(0, 4, CrossCellType.NUMBER_FIXED, initialText = "21", solutionValue = 21),

                MathCrossCell(1, 0, CrossCellType.OPERATOR, initialText = "+"),
                MathCrossCell(1, 1, CrossCellType.EMPTY),
                MathCrossCell(1, 2, CrossCellType.OPERATOR, initialText = "+"),
                MathCrossCell(1, 3, CrossCellType.EMPTY),
                MathCrossCell(1, 4, CrossCellType.EMPTY),

                MathCrossCell(2, 0, CrossCellType.NUMBER_INPUT, initialText = "", solutionValue = 9),
                MathCrossCell(2, 1, CrossCellType.OPERATOR, initialText = "-"),
                MathCrossCell(2, 2, CrossCellType.NUMBER_INPUT, initialText = "", solutionValue = 4),
                MathCrossCell(2, 3, CrossCellType.EQUALS, initialText = "="),
                MathCrossCell(2, 4, CrossCellType.NUMBER_FIXED, initialText = "5", solutionValue = 5),

                MathCrossCell(3, 0, CrossCellType.EQUALS, initialText = "="),
                MathCrossCell(3, 1, CrossCellType.EMPTY),
                MathCrossCell(3, 2, CrossCellType.EQUALS, initialText = "="),
                MathCrossCell(3, 3, CrossCellType.EMPTY),
                MathCrossCell(3, 4, CrossCellType.EMPTY),

                MathCrossCell(4, 0, CrossCellType.NUMBER_FIXED, initialText = "16", solutionValue = 16),
                MathCrossCell(4, 1, CrossCellType.EMPTY),
                MathCrossCell(4, 2, CrossCellType.NUMBER_FIXED, initialText = "7", solutionValue = 7),
                MathCrossCell(4, 3, CrossCellType.EMPTY),
                MathCrossCell(4, 4, CrossCellType.EMPTY)
            )
        )
    )

    fun getBoard(id: Int): MathCrossBoard? = boards.find { it.id == id }
}
