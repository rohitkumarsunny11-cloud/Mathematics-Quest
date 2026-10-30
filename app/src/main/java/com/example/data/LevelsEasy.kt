package com.example.data

import com.example.model.Difficulty
import com.example.model.LevelPuzzle
import com.example.model.PuzzleType
import com.example.model.VisualData

val levelsEasy: List<LevelPuzzle> = listOf(
    LevelPuzzle(
        id = 1,
        difficulty = Difficulty.EASY,
        type = PuzzleType.SEQUENCE,
        title = "Even Pace",
        question = "What number replaces the question mark?",
        visual = VisualData.SequenceVisual(listOf("2", "4", "6", "8", "?")),
        correctAnswer = "10",
        hint1 = "Check the difference between consecutive numbers.",
        hint2 = "Each number increases by 2.",
        hint3 = "8 + 2 = 10.",
        explanation = "The pattern is an arithmetic progression with a common difference of +2 (2, 4, 6, 8, 10)."
    ),
    LevelPuzzle(
        id = 2,
        difficulty = Difficulty.EASY,
        type = PuzzleType.EQUATION,
        title = "Missing Partner",
        question = "Find the missing value to balance the equation.",
        visual = VisualData.SimpleVisual("7 + ? = 15"),
        correctAnswer = "8",
        hint1 = "Subtract 7 from 15.",
        hint2 = "15 - 7 gives the missing number.",
        hint3 = "7 + 8 = 15.",
        explanation = "Isolating the unknown: ? = 15 - 7 = 8."
    ),
    LevelPuzzle(
        id = 3,
        difficulty = Difficulty.EASY,
        type = PuzzleType.PYRAMID,
        title = "Triangle Summit",
        question = "Each block is the sum of the two blocks directly below it.",
        visual = VisualData.PyramidVisual(
            listOf(
                listOf("?"),
                listOf("6", "9")
            )
        ),
        correctAnswer = "15",
        hint1 = "Add the two numbers on the bottom row.",
        hint2 = "6 + 9 = ?",
        hint3 = "The sum is 15.",
        explanation = "The top block equals the sum of its supporting blocks: 6 + 9 = 15."
    ),
    LevelPuzzle(
        id = 4,
        difficulty = Difficulty.EASY,
        type = PuzzleType.SHAPE_ALGEBRA,
        title = "Star Balance",
        question = "Find the numerical value of one Star (⭐).",
        visual = VisualData.ShapeAlgebraVisual(
            listOf("⭐ + ⭐ = 12"),
            "⭐ = ?"
        ),
        correctAnswer = "6",
        hint1 = "Two identical symbols add up to 12.",
        hint2 = "Divide 12 by 2.",
        hint3 = "12 / 2 = 6.",
        explanation = "Since 2 × ⭐ = 12, each star is ⭐ = 12 ÷ 2 = 6."
    ),
    LevelPuzzle(
        id = 5,
        difficulty = Difficulty.EASY,
        type = PuzzleType.SEQUENCE,
        title = "Step by Three",
        question = "Find the next number in this sequence.",
        visual = VisualData.SequenceVisual(listOf("3", "6", "9", "12", "?")),
        correctAnswer = "15",
        hint1 = "Notice how much is added at each step.",
        hint2 = "This is the 3 times table.",
        hint3 = "12 + 3 = 15.",
        explanation = "Adding 3 to each consecutive term: 12 + 3 = 15."
    ),
    LevelPuzzle(
        id = 6,
        difficulty = Difficulty.EASY,
        type = PuzzleType.SEQUENCE,
        title = "Double Trouble",
        question = "What number comes next in this doubling chain?",
        visual = VisualData.SequenceVisual(listOf("1", "2", "4", "8", "?")),
        correctAnswer = "16",
        hint1 = "Each number is multiplied by 2.",
        hint2 = "Double 8.",
        hint3 = "8 × 2 = 16.",
        explanation = "Each term is twice the preceding term: 8 × 2 = 16."
    ),
    LevelPuzzle(
        id = 7,
        difficulty = Difficulty.EASY,
        type = PuzzleType.SHAPE_ALGEBRA,
        title = "Apple & Banana",
        question = "What is the value of Banana (🍌)?",
        visual = VisualData.ShapeAlgebraVisual(
            listOf(
                "🍎 + 🍎 = 10",
                "🍎 + 🍌 = 9"
            ),
            "🍌 = ?"
        ),
        correctAnswer = "4",
        hint1 = "First find the value of 🍎.",
        hint2 = "🍎 = 5. Now replace 🍎 in the second equation.",
        hint3 = "5 + 🍌 = 9, so 🍌 = 4.",
        explanation = "From equation 1: 2 × 🍎 = 10 => 🍎 = 5. Then 5 + 🍌 = 9 => 🍌 = 4."
    ),
    LevelPuzzle(
        id = 8,
        difficulty = Difficulty.EASY,
        type = PuzzleType.SEQUENCE,
        title = "Reverse Countdown",
        question = "Complete the descending sequence.",
        visual = VisualData.SequenceVisual(listOf("30", "25", "20", "15", "?")),
        correctAnswer = "10",
        hint1 = "Numbers are decreasing at a steady rate.",
        hint2 = "Subtract 5 from each term.",
        hint3 = "15 - 5 = 10.",
        explanation = "The sequence subtracts 5 at every step: 15 - 5 = 10."
    ),
    LevelPuzzle(
        id = 9,
        difficulty = Difficulty.EASY,
        type = PuzzleType.OPERATOR,
        title = "Operator Hunter",
        question = "Which math symbol (+, -, *, /) makes the equation true?",
        visual = VisualData.SimpleVisual("18 ? 6 = 3"),
        correctAnswer = "/",
        acceptedAnswers = listOf("/", "÷", "divide"),
        hint1 = "Is the result smaller than 18? Consider subtraction or division.",
        hint2 = "18 divided by 6 equals 3.",
        hint3 = "The division sign / or ÷.",
        explanation = "18 divided by 6 is 3, so the operator is /."
    ),
    LevelPuzzle(
        id = 10,
        difficulty = Difficulty.EASY,
        type = PuzzleType.EQUATION,
        title = "Cent Century",
        question = "What number added to 45 equals 100?",
        visual = VisualData.SimpleVisual("45 + ? = 100"),
        correctAnswer = "55",
        hint1 = "Subtract 45 from 100.",
        hint2 = "100 - 40 = 60, then 60 - 5 = ?",
        hint3 = "100 - 45 = 55.",
        explanation = "100 - 45 = 55."
    ),
    LevelPuzzle(
        id = 11,
        difficulty = Difficulty.EASY,
        type = PuzzleType.SEQUENCE,
        title = "Square Step",
        question = "Look closely at the numbers. What comes next?",
        visual = VisualData.SequenceVisual(listOf("1", "4", "9", "16", "?")),
        correctAnswer = "25",
        hint1 = "These are square numbers (1×1, 2×2, 3×3...).",
        hint2 = "The 5th square number is 5 × 5.",
        hint3 = "5 × 5 = 25.",
        explanation = "These are squares: 1², 2², 3², 4², so next is 5² = 25."
    ),
    LevelPuzzle(
        id = 12,
        difficulty = Difficulty.EASY,
        type = PuzzleType.MATRIX,
        title = "Mini Matrix",
        question = "Find the missing number in the third row.",
        visual = VisualData.MatrixVisual(
            listOf(
                listOf("2", "3", "5"),
                listOf("4", "5", "9"),
                listOf("6", "7", "?")
            )
        ),
        correctAnswer = "13",
        hint1 = "Look across the rows from left to right.",
        hint2 = "The first two numbers add up to the third number in each row.",
        hint3 = "6 + 7 = 13.",
        explanation = "Row 1: 2 + 3 = 5. Row 2: 4 + 5 = 9. Row 3: 6 + 7 = 13."
    ),
    LevelPuzzle(
        id = 13,
        difficulty = Difficulty.EASY,
        type = PuzzleType.SHAPE_ALGEBRA,
        title = "Circle & Triangle",
        question = "What is the numerical value of Circle (🟢)?",
        visual = VisualData.ShapeAlgebraVisual(
            listOf(
                "🟢 + 🔺 = 14",
                "🔺 = 6"
            ),
            "🟢 = ?"
        ),
        correctAnswer = "8",
        hint1 = "Substitute the triangle value into the first equation.",
        hint2 = "🟢 + 6 = 14.",
        hint3 = "14 - 6 = 8.",
        explanation = "🟢 + 6 = 14 => 🟢 = 14 - 6 = 8."
    ),
    LevelPuzzle(
        id = 14,
        difficulty = Difficulty.EASY,
        type = PuzzleType.CLOCK_MATH,
        title = "Clock Advance",
        question = "The clock jumps forward by 1 hour and 15 minutes each step. What is the next time?",
        visual = VisualData.ClockVisual(listOf("1:00", "2:15", "3:30", "?")),
        correctAnswer = "4:45",
        acceptedAnswers = listOf("4:45", "04:45"),
        hint1 = "Add 1 hour and 15 minutes to 3:30.",
        hint2 = "3:30 + 1 hour is 4:30. Now add 15 minutes.",
        hint3 = "4:30 + 15 min = 4:45.",
        explanation = "Interval is +1 hr 15 min: 3:30 + 1:15 = 4:45."
    ),
    LevelPuzzle(
        id = 15,
        difficulty = Difficulty.EASY,
        type = PuzzleType.EQUATION,
        title = "Product Match",
        question = "Find the missing multiplier.",
        visual = VisualData.SimpleVisual("8 × ? = 72"),
        correctAnswer = "9",
        hint1 = "Divide 72 by 8.",
        hint2 = "Think of your 8 times multiplication table.",
        hint3 = "8 × 9 = 72.",
        explanation = "72 ÷ 8 = 9."
    ),
    LevelPuzzle(
        id = 16,
        difficulty = Difficulty.EASY,
        type = PuzzleType.PYRAMID,
        title = "Pyramid Base",
        question = "Find the missing base block.",
        visual = VisualData.PyramidVisual(
            listOf(
                listOf("20"),
                listOf("8", "12"),
                listOf("3", "5", "?")
            )
        ),
        correctAnswer = "7",
        hint1 = "Each block is the sum of the two directly underneath it.",
        hint2 = "The middle bottom block is 5. 5 + ? = 12.",
        hint3 = "12 - 5 = 7.",
        explanation = "Supporting blocks for 12 are 5 and ?: 5 + ? = 12 => ? = 7."
    ),
    LevelPuzzle(
        id = 17,
        difficulty = Difficulty.EASY,
        type = PuzzleType.SEQUENCE,
        title = "The Odd Leap",
        question = "Find the next number in this increasing pattern.",
        visual = VisualData.SequenceVisual(listOf("1", "3", "7", "13", "21", "?")),
        correctAnswer = "31",
        hint1 = "Look at the differences: 3-1=2, 7-3=4, 13-7=6, 21-13=8.",
        hint2 = "The difference increases by 2 each step (+2, +4, +6, +8, +10).",
        hint3 = "21 + 10 = 31.",
        explanation = "Differences are +2, +4, +6, +8, so the next difference is +10: 21 + 10 = 31."
    ),
    LevelPuzzle(
        id = 18,
        difficulty = Difficulty.EASY,
        type = PuzzleType.RIDDLE,
        title = "Balance Beam",
        question = "3 cubes of weight 6 kg each balance exactly with 2 identical cylinders. What is the weight of 1 cylinder?",
        visual = VisualData.BalanceVisual("3 Cubes (6 kg each)", "2 Cylinders (X kg each)", "1 Cylinder = ?"),
        correctAnswer = "9",
        hint1 = "Find total weight on the left: 3 × 6 = 18 kg.",
        hint2 = "The two cylinders together weigh 18 kg.",
        hint3 = "18 ÷ 2 = 9 kg.",
        explanation = "Left weight = 3 × 6 = 18 kg. Two cylinders weigh 18 kg, so one cylinder = 18 ÷ 2 = 9 kg."
    ),
    LevelPuzzle(
        id = 19,
        difficulty = Difficulty.EASY,
        type = PuzzleType.OPERATOR,
        title = "Missing Sign",
        question = "Which operator (+, -, *, /) completes the equation?",
        visual = VisualData.SimpleVisual("7 ? 8 = 56"),
        correctAnswer = "*",
        acceptedAnswers = listOf("*", "x", "×", "multiply"),
        hint1 = "7 multiplied by what gives 56?",
        hint2 = "7 times 8 is 56.",
        hint3 = "Use the multiplication symbol * or x.",
        explanation = "7 × 8 = 56, so the missing operator is *."
    ),
    LevelPuzzle(
        id = 20,
        difficulty = Difficulty.EASY,
        type = PuzzleType.EQUATION,
        title = "Trio Addition",
        question = "Three identical numbers add up to 27. What is that number?",
        visual = VisualData.SimpleVisual("? + ? + ? = 27"),
        correctAnswer = "9",
        hint1 = "Three equal parts make 27.",
        hint2 = "Divide 27 by 3.",
        hint3 = "27 / 3 = 9.",
        explanation = "3 × ? = 27 => ? = 27 ÷ 3 = 9."
    )
)
