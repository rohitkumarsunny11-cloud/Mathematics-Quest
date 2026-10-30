package com.example.data

import com.example.model.Difficulty
import com.example.model.LevelPuzzle
import com.example.model.PuzzleType
import com.example.model.VisualData

val levelsNormal: List<LevelPuzzle> = listOf(
    LevelPuzzle(
        id = 21,
        difficulty = Difficulty.NORMAL,
        type = PuzzleType.SEQUENCE,
        title = "Fibonacci Starter",
        question = "Each number is the sum of the previous two numbers. What comes next?",
        visual = VisualData.SequenceVisual(listOf("1", "1", "2", "3", "5", "8", "?")),
        correctAnswer = "13",
        hint1 = "Add the last two numbers together.",
        hint2 = "5 + 8 = ?",
        hint3 = "5 + 8 = 13.",
        explanation = "This is the Fibonacci sequence: each term is the sum of the two preceding terms. 5 + 8 = 13."
    ),
    LevelPuzzle(
        id = 22,
        difficulty = Difficulty.NORMAL,
        type = PuzzleType.SHAPE_ALGEBRA,
        title = "Shape Trio System",
        question = "Find the numerical value of Star (⭐).",
        visual = VisualData.ShapeAlgebraVisual(
            listOf(
                "🔷 + 🔷 = 18",
                "⭐ + 🔷 = 15"
            ),
            "⭐ = ?"
        ),
        correctAnswer = "6",
        hint1 = "Solve for Diamond (🔷) first.",
        hint2 = "🔷 = 18 ÷ 2 = 9.",
        hint3 = "⭐ + 9 = 15 => ⭐ = 6.",
        explanation = "2 × 🔷 = 18 => 🔷 = 9. Then ⭐ + 9 = 15 => ⭐ = 15 - 9 = 6."
    ),
    LevelPuzzle(
        id = 23,
        difficulty = Difficulty.NORMAL,
        type = PuzzleType.EQUATION,
        title = "Order of Operations Trap",
        question = "Calculate the exact value using standard order of operations (BODMAS/PEMDAS).",
        visual = VisualData.SimpleVisual("4 + 4 × 4 - 4 = ?"),
        correctAnswer = "16",
        hint1 = "Perform multiplication before addition and subtraction.",
        hint2 = "First calculate 4 × 4 = 16.",
        hint3 = "Now do 4 + 16 - 4.",
        explanation = "Multiplication first: 4 × 4 = 16. Then addition and subtraction left-to-right: 4 + 16 = 20, 20 - 4 = 16."
    ),
    LevelPuzzle(
        id = 24,
        difficulty = Difficulty.NORMAL,
        type = PuzzleType.SEQUENCE,
        title = "Cube Sequence",
        question = "What is the 5th number in this sequence?",
        visual = VisualData.SequenceVisual(listOf("1", "8", "27", "64", "?")),
        correctAnswer = "125",
        hint1 = "Compare to cube powers: 1³=1, 2³=8, 3³=27, 4³=64.",
        hint2 = "Calculate 5 cubed (5 × 5 × 5).",
        hint3 = "25 × 5 = 125.",
        explanation = "The sequence consists of cubes of natural numbers: 1³, 2³, 3³, 4³, 5³ = 125."
    ),
    LevelPuzzle(
        id = 25,
        difficulty = Difficulty.NORMAL,
        type = PuzzleType.PYRAMID,
        title = "Pyramid Multiplication",
        question = "In this multiplication pyramid, the top block is the product of the two bottom blocks.",
        visual = VisualData.PyramidVisual(
            listOf(
                listOf("?"),
                listOf("6", "7")
            )
        ),
        correctAnswer = "42",
        hint1 = "Multiply 6 by 7.",
        hint2 = "Think of 6 × 7.",
        hint3 = "6 × 7 = 42.",
        explanation = "Top block = 6 × 7 = 42."
    ),
    LevelPuzzle(
        id = 26,
        difficulty = Difficulty.NORMAL,
        type = PuzzleType.EQUATION,
        title = "The Double Operator",
        question = "Find the missing number inside the parentheses.",
        visual = VisualData.SimpleVisual("(20 - ?) ÷ 3 = 4"),
        correctAnswer = "8",
        hint1 = "Multiply both sides by 3 to clear the division.",
        hint2 = "20 - ? = 12.",
        hint3 = "20 - 12 = 8.",
        explanation = "(20 - ?) ÷ 3 = 4 => 20 - ? = 12 => ? = 20 - 12 = 8."
    ),
    LevelPuzzle(
        id = 27,
        difficulty = Difficulty.NORMAL,
        type = PuzzleType.EQUATION,
        title = "Target 50",
        question = "Find the unknown number.",
        visual = VisualData.SimpleVisual("3 × ? + 5 = 50"),
        correctAnswer = "15",
        hint1 = "Subtract 5 from 50 first.",
        hint2 = "3 × ? = 45.",
        hint3 = "45 ÷ 3 = 15.",
        explanation = "3 × ? = 50 - 5 = 45 => ? = 45 ÷ 3 = 15."
    ),
    LevelPuzzle(
        id = 28,
        difficulty = Difficulty.NORMAL,
        type = PuzzleType.MATRIX,
        title = "Pattern Matrix 3x3",
        question = "Find the missing number in the third row.",
        visual = VisualData.MatrixVisual(
            listOf(
                listOf("3", "6", "18"),
                listOf("4", "5", "20"),
                listOf("7", "8", "?")
            )
        ),
        correctAnswer = "56",
        hint1 = "Look at the relation between the first two columns and the third.",
        hint2 = "In row 1: 3 × 6 = 18. In row 2: 4 × 5 = 20.",
        hint3 = "7 × 8 = 56.",
        explanation = "Each third column element is the product of the first two elements in that row: 7 × 8 = 56."
    ),
    LevelPuzzle(
        id = 29,
        difficulty = Difficulty.NORMAL,
        type = PuzzleType.RIDDLE,
        title = "Roman Numeral Math",
        question = "Convert to standard numbers and solve: X + XV = ?",
        visual = VisualData.SimpleVisual("X + XV = ?"),
        correctAnswer = "25",
        hint1 = "X = 10, XV = 15.",
        hint2 = "Add 10 and 15.",
        hint3 = "10 + 15 = 25.",
        explanation = "Roman numeral X = 10 and XV = 15. Therefore, 10 + 15 = 25."
    ),
    LevelPuzzle(
        id = 30,
        difficulty = Difficulty.NORMAL,
        type = PuzzleType.RIDDLE,
        title = "Triangle Perimeter",
        question = "An equilateral triangle has a perimeter of 48 cm. What is the length of one side in cm?",
        visual = VisualData.SimpleVisual("Perimeter = 48 cm | Side = ?"),
        correctAnswer = "16",
        hint1 = "An equilateral triangle has 3 sides of equal length.",
        hint2 = "Divide the perimeter by 3.",
        hint3 = "48 ÷ 3 = 16.",
        explanation = "Side length = Perimeter ÷ 3 = 48 ÷ 3 = 16 cm."
    ),
    LevelPuzzle(
        id = 31,
        difficulty = Difficulty.NORMAL,
        type = PuzzleType.SEQUENCE,
        title = "Alternating Sequence",
        question = "Find the missing number following the alternating rule (×5, -5).",
        visual = VisualData.SequenceVisual(listOf("2", "10", "5", "25", "20", "100", "?")),
        correctAnswer = "95",
        hint1 = "Look at the operations: 2×5=10, 10-5=5, 5×5=25, 25-5=20, 20×5=100.",
        hint2 = "The next operation is subtraction by 5.",
        hint3 = "100 - 5 = 95.",
        explanation = "The pattern alternates multiplying by 5 and subtracting 5: 100 - 5 = 95."
    ),
    LevelPuzzle(
        id = 32,
        difficulty = Difficulty.NORMAL,
        type = PuzzleType.SHAPE_ALGEBRA,
        title = "Three Fruit Equation",
        question = "What is the value of Grapes (🍇)?",
        visual = VisualData.ShapeAlgebraVisual(
            listOf(
                "🍎 + 🍎 + 🍎 = 30",
                "🍎 + 🍌 + 🍌 = 20",
                "🍌 - 🍇 = 3"
            ),
            "🍇 = ?"
        ),
        correctAnswer = "2",
        hint1 = "Find 🍎 first: 30 ÷ 3 = 10.",
        hint2 = "10 + 2🍌 = 20 => 2🍌 = 10 => 🍌 = 5.",
        hint3 = "5 - 🍇 = 3 => 🍇 = 2.",
        explanation = "🍎 = 10. Then 10 + 2🍌 = 20 => 🍌 = 5. Finally, 5 - 🍇 = 3 => 🍇 = 2."
    ),
    LevelPuzzle(
        id = 33,
        difficulty = Difficulty.NORMAL,
        type = PuzzleType.CLOCK_MATH,
        title = "Clock Angles",
        question = "At exactly 3:00, what is the smaller angle between the hour hand and minute hand in degrees?",
        visual = VisualData.ClockVisual(listOf("3:00")),
        correctAnswer = "90",
        hint1 = "The clock face is 360 degrees divided into 12 hours (30° per hour).",
        hint2 = "At 3:00, the minute hand points to 12 and the hour hand to 3.",
        hint3 = "3 hours × 30° = 90°.",
        explanation = "Each hour mark is 360° / 12 = 30°. From 12 to 3 is 3 hours: 3 × 30° = 90°."
    ),
    LevelPuzzle(
        id = 34,
        difficulty = Difficulty.NORMAL,
        type = PuzzleType.EQUATION,
        title = "Missing Digit Sum",
        question = "Find the missing single digit ? in the two-digit number 3?.",
        visual = VisualData.SimpleVisual("25 + 3? = 63"),
        correctAnswer = "8",
        hint1 = "Subtract 25 from 63 to find the value of 3?.",
        hint2 = "63 - 25 = 38.",
        hint3 = "Since 3? is 38, ? is 8.",
        explanation = "63 - 25 = 38, so the units digit is 8."
    ),
    LevelPuzzle(
        id = 35,
        difficulty = Difficulty.NORMAL,
        type = PuzzleType.SEQUENCE,
        title = "Prime Leap",
        question = "What is the next prime number in this list?",
        visual = VisualData.SequenceVisual(listOf("2", "3", "5", "7", "11", "13", "?")),
        correctAnswer = "17",
        hint1 = "Notice these are prime numbers (divisible only by 1 and itself).",
        hint2 = "Is 15 prime? No (3×5). Is 17 prime?",
        hint3 = "17 is the next prime number.",
        explanation = "This is the sequence of consecutive prime numbers. Following 13, the next prime is 17."
    ),
    LevelPuzzle(
        id = 36,
        difficulty = Difficulty.NORMAL,
        type = PuzzleType.RIDDLE,
        title = "Average Mystery",
        question = "The average of three numbers is 20. Two of the numbers are 15 and 25. What is the third number?",
        visual = VisualData.SimpleVisual("Average(15, 25, ?) = 20"),
        correctAnswer = "20",
        hint1 = "Total sum of the 3 numbers is 3 × 20 = 60.",
        hint2 = "Sum of known numbers is 15 + 25 = 40.",
        hint3 = "60 - 40 = 20.",
        explanation = "Sum = 3 × 20 = 60. Known numbers sum to 15 + 25 = 40. Third number = 60 - 40 = 20."
    ),
    LevelPuzzle(
        id = 37,
        difficulty = Difficulty.NORMAL,
        type = PuzzleType.PYRAMID,
        title = "Two-Step Pyramid",
        question = "Find the missing base block in this additive pyramid.",
        visual = VisualData.PyramidVisual(
            listOf(
                listOf("48"),
                listOf("20", "28"),
                listOf("8", "12", "?")
            )
        ),
        correctAnswer = "16",
        hint1 = "The middle block on bottom row is 12.",
        hint2 = "12 + ? = 28.",
        hint3 = "28 - 12 = 16.",
        explanation = "The right block in the middle row (28) is supported by 12 and ?: 12 + ? = 28 => ? = 16."
    ),
    LevelPuzzle(
        id = 38,
        difficulty = Difficulty.NORMAL,
        type = PuzzleType.EQUATION,
        title = "Fraction Fraction",
        question = "What is half of a quarter of 80?",
        visual = VisualData.SimpleVisual("1/2 of 1/4 of 80 = ?"),
        correctAnswer = "10",
        hint1 = "First find 1/4 of 80: 80 ÷ 4.",
        hint2 = "80 ÷ 4 = 20. Now find half of 20.",
        hint3 = "20 ÷ 2 = 10.",
        explanation = "(1/2) × (1/4) × 80 = (1/8) × 80 = 10."
    ),
    LevelPuzzle(
        id = 39,
        difficulty = Difficulty.NORMAL,
        type = PuzzleType.MATRIX,
        title = "Grid Center",
        question = "Find the center number in this 3x3 consecutive grid.",
        visual = VisualData.MatrixVisual(
            listOf(
                listOf("1", "2", "3"),
                listOf("4", "?", "6"),
                listOf("7", "8", "9")
            )
        ),
        correctAnswer = "5",
        hint1 = "The numbers 1 through 9 are ordered row by row.",
        hint2 = "What comes after 4 and before 6?",
        hint3 = "The number is 5.",
        explanation = "The 3x3 grid contains integers 1 to 9 sequentially. Between 4 and 6 is 5."
    ),
    LevelPuzzle(
        id = 40,
        difficulty = Difficulty.NORMAL,
        type = PuzzleType.RIDDLE,
        title = "Coin Split",
        question = "A bat and ball cost $110 in total. The bat costs $100 more than the ball. How much does the ball cost in dollars?",
        visual = VisualData.SimpleVisual("Bat + Ball = $110 | Bat = Ball + $100"),
        correctAnswer = "5",
        hint1 = "Be careful! If the ball were $10, the bat would be $110 and total would be $120.",
        hint2 = "Let ball = x. Then x + (x + 100) = 110.",
        hint3 = "2x = 10 => x = 5.",
        explanation = "Ball + (Ball + 100) = 110 => 2 × Ball = 10 => Ball = $5 (Bat is $105)."
    )
)
