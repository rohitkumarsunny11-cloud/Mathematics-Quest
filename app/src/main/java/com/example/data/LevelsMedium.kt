package com.example.data

import com.example.model.Difficulty
import com.example.model.LevelPuzzle
import com.example.model.PuzzleType
import com.example.model.VisualData

val levelsMedium: List<LevelPuzzle> = listOf(
    LevelPuzzle(
        id = 41,
        difficulty = Difficulty.MEDIUM,
        type = PuzzleType.EQUATION,
        title = "Nested BODMAS",
        question = "Evaluate the expression following operator precedence.",
        visual = VisualData.SimpleVisual("6 + 3 × (8 - 2) ÷ 2 = ?"),
        correctAnswer = "15",
        hint1 = "Calculate inside parentheses first: (8 - 2) = 6.",
        hint2 = "Next, multiply and divide from left to right: 3 × 6 = 18, then 18 ÷ 2 = 9.",
        hint3 = "Finally add: 6 + 9 = 15.",
        explanation = "Parentheses: 8 - 2 = 6. Multiplication: 3 × 6 = 18. Division: 18 ÷ 2 = 9. Addition: 6 + 9 = 15."
    ),
    LevelPuzzle(
        id = 42,
        difficulty = Difficulty.MEDIUM,
        type = PuzzleType.SEQUENCE,
        title = "Geometric Progression",
        question = "What is the next number in this doubling sequence?",
        visual = VisualData.SequenceVisual(listOf("3", "6", "12", "24", "48", "?")),
        correctAnswer = "96",
        hint1 = "Every number is multiplied by 2.",
        hint2 = "Multiply 48 by 2.",
        hint3 = "48 × 2 = 96.",
        explanation = "Geometric progression with common ratio r = 2: 48 × 2 = 96."
    ),
    LevelPuzzle(
        id = 43,
        difficulty = Difficulty.MEDIUM,
        type = PuzzleType.SHAPE_ALGEBRA,
        title = "Shape Multiplication & Sum",
        question = "Find the numerical value of Diamond (🔶).",
        visual = VisualData.ShapeAlgebraVisual(
            listOf(
                "🔵 + 🔵 = 16",
                "🔵 × 🔶 = 72"
            ),
            "🔶 = ?"
        ),
        correctAnswer = "9",
        hint1 = "Find Circle (🔵) first: 16 ÷ 2 = 8.",
        hint2 = "Substitute: 8 × 🔶 = 72.",
        hint3 = "72 ÷ 8 = 9.",
        explanation = "🔵 = 16 ÷ 2 = 8. Then 8 × 🔶 = 72 => 🔶 = 72 ÷ 8 = 9."
    ),
    LevelPuzzle(
        id = 44,
        difficulty = Difficulty.MEDIUM,
        type = PuzzleType.LOGIC_GRID,
        title = "Magic Square Sum",
        question = "In a standard 3x3 magic square with distinct digits 1 to 9, what is the constant sum of each row, column, and diagonal?",
        visual = VisualData.SimpleVisual("3x3 Magic Square (Digits 1–9) | Constant Sum = ?"),
        correctAnswer = "15",
        hint1 = "The total sum of digits 1 through 9 is 45.",
        hint2 = "The 3 rows share this total sum equally.",
        hint3 = "45 ÷ 3 = 15.",
        explanation = "Sum of digits 1 to 9 is 45. In 3 rows: 45 ÷ 3 = 15 (Magic constant = 15)."
    ),
    LevelPuzzle(
        id = 45,
        difficulty = Difficulty.MEDIUM,
        type = PuzzleType.OPERATOR,
        title = "Missing Operator Pair",
        question = "Which two symbols make this valid? (Format: '/ +' or '/+')",
        visual = VisualData.SimpleVisual("12 ? 4 ? 2 = 5"),
        correctAnswer = "/ +",
        acceptedAnswers = listOf("/ +", "/+", "divide add", "/, +"),
        hint1 = "12 divided by 4 gives 3.",
        hint2 = "Then 3 plus 2 gives 5.",
        hint3 = "The symbols are / and +.",
        explanation = "12 / 4 + 2 = 3 + 2 = 5."
    ),
    LevelPuzzle(
        id = 46,
        difficulty = Difficulty.MEDIUM,
        type = PuzzleType.SEQUENCE,
        title = "Double Difference",
        question = "Find the next number in this sequence.",
        visual = VisualData.SequenceVisual(listOf("2", "5", "11", "23", "47", "?")),
        correctAnswer = "95",
        hint1 = "Notice that each number is approximately twice the previous.",
        hint2 = "Rule: (previous × 2) + 1. 2×2+1=5, 5×2+1=11, 11×2+1=23, 23×2+1=47.",
        hint3 = "47 × 2 + 1 = 95.",
        explanation = "Recursive relation: a_n = 2 × a_{n-1} + 1. 47 × 2 + 1 = 94 + 1 = 95."
    ),
    LevelPuzzle(
        id = 47,
        difficulty = Difficulty.MEDIUM,
        type = PuzzleType.RIDDLE,
        title = "Sum of First Ten Odds",
        question = "What is the sum of the first 10 positive odd integers: 1 + 3 + 5 + ... + 19?",
        visual = VisualData.SimpleVisual("1 + 3 + 5 + ... + 19 = ?"),
        correctAnswer = "100",
        hint1 = "The sum of the first n odd numbers is always n².",
        hint2 = "Here n = 10.",
        hint3 = "10² = 100.",
        explanation = "Formula for sum of first n odd numbers: S_n = n². For n = 10, S_10 = 10² = 100."
    ),
    LevelPuzzle(
        id = 48,
        difficulty = Difficulty.MEDIUM,
        type = PuzzleType.MATRIX,
        title = "Pythagorean Trio",
        question = "Each row forms a Pythagorean triple (a² + b² = c²). Find ?.",
        visual = VisualData.MatrixVisual(
            listOf(
                listOf("3", "4", "5"),
                listOf("6", "8", "10"),
                listOf("5", "12", "?")
            )
        ),
        correctAnswer = "13",
        hint1 = "3² + 4² = 9 + 16 = 25 = 5².",
        hint2 = "Calculate 5² + 12² = 25 + 144 = 169.",
        hint3 = "√169 = 13.",
        explanation = "5² + 12² = 25 + 144 = 169 = 13², completing the Pythagorean triple (5, 12, 13)."
    ),
    LevelPuzzle(
        id = 49,
        difficulty = Difficulty.MEDIUM,
        type = PuzzleType.RIDDLE,
        title = "Percentage Leap",
        question = "A jacket costs $80. It is discounted by 25%. What is the sale price in dollars?",
        visual = VisualData.SimpleVisual("Original: $80 | Discount: 25% | Final Price = ?"),
        correctAnswer = "60",
        hint1 = "25% is equal to one-quarter (1/4).",
        hint2 = "1/4 of $80 is $20.",
        hint3 = "$80 - $20 = $60.",
        explanation = "Discount = 25% of 80 = 20. Sale price = 80 - 20 = $60."
    ),
    LevelPuzzle(
        id = 50,
        difficulty = Difficulty.MEDIUM,
        type = PuzzleType.SHAPE_ALGEBRA,
        title = "Three Equations System",
        question = "Find the numerical value of Green Circle (🟢).",
        visual = VisualData.ShapeAlgebraVisual(
            listOf(
                "🔺 + 🔺 = 20",
                "🔺 × 🟦 = 50",
                "🟦 + 🟢 = 12"
            ),
            "🟢 = ?"
        ),
        correctAnswer = "7",
        hint1 = "🔺 = 20 ÷ 2 = 10.",
        hint2 = "10 × 🟦 = 50 => 🟦 = 5.",
        hint3 = "5 + 🟢 = 12 => 🟢 = 7.",
        explanation = "🔺 = 10. Then 10 × 🟦 = 50 => 🟦 = 5. Finally, 5 + 🟢 = 12 => 🟢 = 12 - 5 = 7."
    ),
    LevelPuzzle(
        id = 51,
        difficulty = Difficulty.MEDIUM,
        type = PuzzleType.EQUATION,
        title = "Factorial Countdown",
        question = "Calculate the value of 4! - 3!.",
        visual = VisualData.SimpleVisual("4! - 3! = ?"),
        correctAnswer = "18",
        hint1 = "n! is the product of all integers up to n.",
        hint2 = "4! = 4 × 3 × 2 × 1 = 24. 3! = 3 × 2 × 1 = 6.",
        hint3 = "24 - 6 = 18.",
        explanation = "4! = 24 and 3! = 6. 24 - 6 = 18."
    ),
    LevelPuzzle(
        id = 52,
        difficulty = Difficulty.MEDIUM,
        type = PuzzleType.CLOCK_MATH,
        title = "Clock Hands Opposite",
        question = "In a 12-hour cycle, how many times do the hour and minute hands point in exact opposite directions (180°)?",
        visual = VisualData.ClockVisual(listOf("180° Straight Line")),
        correctAnswer = "11",
        hint1 = "Think about whether it happens once every hour, except between 5 and 7.",
        hint2 = "In 12 hours, the minute hand makes 12 laps while the hour hand makes 1 lap (difference = 11).",
        hint3 = "The exact count is 11 times.",
        explanation = "The minute hand completes 12 revolutions while the hour hand completes 1, giving 12 - 1 = 11 occurrences per 12-hour period."
    ),
    LevelPuzzle(
        id = 53,
        difficulty = Difficulty.MEDIUM,
        type = PuzzleType.MATRIX,
        title = "Sum of Squares Matrix",
        question = "Discover the formula connecting the columns and find ?.",
        visual = VisualData.MatrixVisual(
            listOf(
                listOf("2", "3", "13"),
                listOf("3", "4", "25"),
                listOf("4", "5", "?")
            )
        ),
        correctAnswer = "41",
        hint1 = "Row 1: 2² + 3² = 4 + 9 = 13.",
        hint2 = "Row 2: 3² + 4² = 9 + 16 = 25.",
        hint3 = "Row 3: 4² + 5² = 16 + 25 = 41.",
        explanation = "The third column is the sum of squares of the first two numbers: 4² + 5² = 16 + 25 = 41."
    ),
    LevelPuzzle(
        id = 54,
        difficulty = Difficulty.MEDIUM,
        type = PuzzleType.RIDDLE,
        title = "Coin Denominations",
        question = "You have 10 coins consisting only of 5¢ and 10¢ coins, worth 70¢ in total. How many 10¢ coins do you have?",
        visual = VisualData.SimpleVisual("10 coins (5¢ and 10¢) | Total = 70¢ | Count of 10¢ = ?"),
        correctAnswer = "4",
        hint1 = "If all 10 coins were 5¢, total would be 50¢.",
        hint2 = "Each 10¢ coin adds 5¢ to the total. Need 20¢ more.",
        hint3 = "20¢ ÷ 5¢ = 4 coins of 10¢.",
        explanation = "Let x be number of 10¢ coins: 10x + 5(10 - x) = 70 => 5x + 50 = 70 => 5x = 20 => x = 4."
    ),
    LevelPuzzle(
        id = 55,
        difficulty = Difficulty.MEDIUM,
        type = PuzzleType.RIDDLE,
        title = "Speed and Distance",
        question = "A car travels at a constant speed of 60 km/h. How many kilometers does it travel in 45 minutes?",
        visual = VisualData.SimpleVisual("Speed = 60 km/h | Time = 45 min | Distance = ? km"),
        correctAnswer = "45",
        hint1 = "45 minutes is 3/4 of an hour.",
        hint2 = "Distance = Speed × Time.",
        hint3 = "60 × (3/4) = 45 km.",
        explanation = "45 minutes = 0.75 hours. Distance = 60 km/h × 0.75 h = 45 km."
    ),
    LevelPuzzle(
        id = 56,
        difficulty = Difficulty.MEDIUM,
        type = PuzzleType.PYRAMID,
        title = "Pyramid Reverse Solve",
        question = "Find the missing block ? in the second row.",
        visual = VisualData.PyramidVisual(
            listOf(
                listOf("50"),
                listOf("?", "28"),
                listOf("10", "12", "16")
            )
        ),
        correctAnswer = "22",
        hint1 = "Look at the top block: ? + 28 = 50.",
        hint2 = "Also check the bottom: 10 + 12 = ?.",
        hint3 = "50 - 28 = 22, and 10 + 12 = 22.",
        explanation = "From top: 50 - 28 = 22. Checking bottom: 10 + 12 = 22. Both confirm ? = 22."
    ),
    LevelPuzzle(
        id = 57,
        difficulty = Difficulty.MEDIUM,
        type = PuzzleType.EQUATION,
        title = "Modulo Wonder",
        question = "What is the remainder when (17 × 19) is divided by 5?",
        visual = VisualData.SimpleVisual("(17 × 19) mod 5 = ?"),
        correctAnswer = "3",
        hint1 = "17 leaves remainder 2 when divided by 5.",
        hint2 = "19 leaves remainder 4 when divided by 5.",
        hint3 = "2 × 4 = 8. 8 mod 5 = 3.",
        explanation = "Using modular arithmetic: 17 ≡ 2 (mod 5) and 19 ≡ 4 (mod 5). 2 × 4 = 8 ≡ 3 (mod 5)."
    ),
    LevelPuzzle(
        id = 58,
        difficulty = Difficulty.MEDIUM,
        type = PuzzleType.EQUATION,
        title = "Missing Exponent",
        question = "Find the missing power ? in: 2^? + 2^3 = 24.",
        visual = VisualData.SimpleVisual("2^? + 2³ = 24"),
        correctAnswer = "4",
        hint1 = "Evaluate 2³ = 8.",
        hint2 = "2^? + 8 = 24 => 2^? = 16.",
        hint3 = "16 is 2⁴, so ? = 4.",
        explanation = "2^? = 24 - 8 = 16 = 2⁴, so ? = 4."
    ),
    LevelPuzzle(
        id = 59,
        difficulty = Difficulty.MEDIUM,
        type = PuzzleType.SEQUENCE,
        title = "Sequence of Triangles",
        question = "These are triangular numbers. What is the 7th triangular number?",
        visual = VisualData.SequenceVisual(listOf("1", "3", "6", "10", "15", "21", "?")),
        correctAnswer = "28",
        hint1 = "Differences increase by 1: +2, +3, +4, +5, +6, +7.",
        hint2 = "Add 7 to 21.",
        hint3 = "21 + 7 = 28.",
        explanation = "Formula is n(n+1)/2. For n=7: 7 × 8 / 2 = 28. (21 + 7 = 28)."
    ),
    LevelPuzzle(
        id = 60,
        difficulty = Difficulty.MEDIUM,
        type = PuzzleType.CRYPTARITHM,
        title = "Cryptarithm AB",
        question = "If AB + BA = 77 where A and B are single digits, what is the value of (A + B)?",
        visual = VisualData.SimpleVisual("AB + BA = 77 | A + B = ?"),
        correctAnswer = "7",
        hint1 = "AB is (10A + B) and BA is (10B + A).",
        hint2 = "(10A + B) + (10B + A) = 11A + 11B = 11(A + B).",
        hint3 = "11(A + B) = 77 => A + B = 7.",
        explanation = "AB + BA = (10A + B) + (10B + A) = 11(A + B) = 77 => A + B = 7."
    )
)
