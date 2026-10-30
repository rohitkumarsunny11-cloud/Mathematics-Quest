package com.example.model

enum class CrossCellType {
    NUMBER_INPUT,
    NUMBER_FIXED,
    OPERATOR,
    EQUALS,
    EMPTY
}

data class MathCrossCell(
    val row: Int,
    val col: Int,
    val type: CrossCellType,
    val initialText: String = "",
    val solutionValue: Int = 0,
    val isEditable: Boolean = (type == CrossCellType.NUMBER_INPUT)
)

data class CrossEquation(
    val isRow: Boolean,
    val index: Int, // row or col index
    val leftRow: Int, val leftCol: Int,
    val opRow: Int, val opCol: Int,
    val rightRow: Int, val rightCol: Int,
    val resultRow: Int, val resultCol: Int
)

data class MathCrossBoard(
    val id: Int,
    val title: String,
    val difficulty: Difficulty,
    val rows: Int,
    val cols: Int,
    val cells: List<MathCrossCell>,
    val equations: List<CrossEquation>,
    val rewardCoins: Int = 40
) {
    fun getCell(r: Int, c: Int): MathCrossCell? {
        return cells.find { it.row == r && it.col == c }
    }

    fun isSolved(userInputs: Map<Pair<Int, Int>, String>): Boolean {
        // First verify all input cells are non-empty and match solution or satisfy all equations
        for (eq in equations) {
            val leftVal = getCellValue(eq.leftRow, eq.leftCol, userInputs) ?: return false
            val rightVal = getCellValue(eq.rightRow, eq.rightCol, userInputs) ?: return false
            val resultVal = getCellValue(eq.resultRow, eq.resultCol, userInputs) ?: return false
            val opCell = getCell(eq.opRow, eq.opCol) ?: return false

            val expected = when (opCell.initialText) {
                "+" -> leftVal + rightVal
                "-" -> leftVal - rightVal
                "*" -> leftVal * rightVal
                "/" -> if (rightVal != 0 && leftVal % rightVal == 0) leftVal / rightVal else return false
                else -> return false
            }
            if (expected != resultVal) return false
        }
        return true
    }

    private fun getCellValue(r: Int, c: Int, userInputs: Map<Pair<Int, Int>, String>): Int? {
        val cell = getCell(r, c) ?: return null
        return if (cell.isEditable) {
            userInputs[Pair(r, c)]?.toIntOrNull()
        } else {
            cell.initialText.toIntOrNull()
        }
    }
}
