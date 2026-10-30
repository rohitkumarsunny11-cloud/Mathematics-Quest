package com.example.data

import com.example.model.Difficulty
import com.example.model.LevelPuzzle
import com.example.model.PuzzleType
import com.example.model.VisualData

val levelsHard: List<LevelPuzzle> = listOf(
    LevelPuzzle(
        id = 61,
        difficulty = Difficulty.HARD,
        type = PuzzleType.SEQUENCE,
        title = "Power Progression",
        question = "What is the next number in this triple progression?",
        visual = VisualData.SequenceVisual(listOf("2", "6", "18", "54", "162", "?")),
        correctAnswer = "486",
        hint1 = "Each number is multiplied by 3.",
        hint2 = "Calculate 162 × 3.",
        hint3 = "162 × 3 = 486.",
        explanation = "Geometric sequence with ratio r = 3: 162 × 3 = 486."
    ),
    LevelPuzzle(
        id = 62,
        difficulty = Difficulty.HARD,
        type = PuzzleType.RIDDLE,
        title = "Geometric Angle Mystery",
        question = "In a right-angled triangle, one acute angle is twice the other. What is the measure of the smaller acute angle in degrees?",
        visual = VisualData.SimpleVisual("Right triangle (90°) | Angles: x and 2x | x = ?°"),
        correctAnswer = "30",
        hint1 = "The two acute angles in a right triangle sum to 90°.",
        hint2 = "x + 2x = 90° => 3x = 90°.",
        hint3 = "90 ÷ 3 = 30°.",
        explanation = "x + 2x = 90° => 3x = 90° => x = 30° (angles are 30°, 60°, 90°)."
    ),
    LevelPuzzle(
        id = 63,
        difficulty = Difficulty.HARD,
        type = PuzzleType.CRYPTARITHM,
        title = "Cryptarithm SEND",
        question = "In the famous puzzle SEND + MORE = MONEY, what single digit does the leading letter M represent?",
        visual = VisualData.SimpleVisual("  SEND\n+ MORE\n------\n MONEY | M = ?"),
        correctAnswer = "1",
        hint1 = "The sum of two 4-digit numbers can at most be 19,998.",
        hint2 = "The only possible carry into a new 5th column is 1.",
        hint3 = "Therefore, M must be 1.",
        explanation = "Adding two 4-digit numbers can produce at most a carry of 1 in the ten-thousands place, so M = 1."
    ),
    LevelPuzzle(
        id = 64,
        difficulty = Difficulty.HARD,
        type = PuzzleType.RIDDLE,
        title = "Square Area from Perimeter",
        question = "A square has a perimeter of 40 cm. What is its area in square cm?",
        visual = VisualData.SimpleVisual("Perimeter = 40 cm | Area = ? cm²"),
        correctAnswer = "100",
        hint1 = "A square has 4 equal sides.",
        hint2 = "Side length = 40 ÷ 4 = 10 cm.",
        hint3 = "Area = Side² = 10 × 10 = 100.",
        explanation = "Side = 40 / 4 = 10 cm. Area = 10² = 100 cm²."
    ),
    LevelPuzzle(
        id = 65,
        difficulty = Difficulty.HARD,
        type = PuzzleType.RIDDLE,
        title = "Binary to Decimal",
        question = "Convert the binary number 11010 into standard decimal (base 10).",
        visual = VisualData.SimpleVisual("11010₂ = ?₁₀"),
        correctAnswer = "26",
        hint1 = "Powers of 2 from right to left: 1, 2, 4, 8, 16.",
        hint2 = "Add the active bits: 16 + 8 + 0 + 2 + 0.",
        hint3 = "16 + 8 + 2 = 26.",
        explanation = "1×2⁴ + 1×2³ + 0×2² + 1×2¹ + 0×2⁰ = 16 + 8 + 0 + 2 + 0 = 26."
    ),
    LevelPuzzle(
        id = 66,
        difficulty = Difficulty.HARD,
        type = PuzzleType.EQUATION,
        title = "Fractional Equation",
        question = "Solve for x: 3/x + 2/x = 1/2.",
        visual = VisualData.SimpleVisual("3/x + 2/x = 1/2 | x = ?"),
        correctAnswer = "10",
        hint1 = "Combine terms with common denominator: (3 + 2)/x = 5/x.",
        hint2 = "5/x = 1/2.",
        hint3 = "Cross-multiply: x = 5 × 2 = 10.",
        explanation = "5/x = 1/2 => x = 5 × 2 = 10."
    ),
    LevelPuzzle(
        id = 67,
        difficulty = Difficulty.HARD,
        type = PuzzleType.SHAPE_ALGEBRA,
        title = "Shape Quad System",
        question = "Find the numerical value of Triangle (🔺).",
        visual = VisualData.ShapeAlgebraVisual(
            listOf(
                "⭐ × ⭐ = 49",
                "⭐ + 🔶 = 15",
                "🔶 × 🔺 = 32"
            ),
            "🔺 = ?"
        ),
        correctAnswer = "4",
        hint1 = "⭐ = √49 = 7.",
        hint2 = "7 + 🔶 = 15 => 🔶 = 8.",
        hint3 = "8 × 🔺 = 32 => 🔺 = 4.",
        explanation = "⭐ = 7. Then 🔶 = 15 - 7 = 8. Finally 🔺 = 32 ÷ 8 = 4."
    ),
    LevelPuzzle(
        id = 68,
        difficulty = Difficulty.HARD,
        type = PuzzleType.RIDDLE,
        title = "Logic Balance",
        question = "4 Spheres and 1 Cube balance with 2 Cubes and 1 Sphere. How many Spheres balance exactly 1 Cube?",
        visual = VisualData.BalanceVisual("4 Spheres + 1 Cube", "2 Cubes + 1 Sphere", "1 Cube = ? Spheres"),
        correctAnswer = "3",
        hint1 = "Subtract 1 Sphere and 1 Cube from both sides.",
        hint2 = "3 Spheres = 1 Cube.",
        hint3 = "1 Cube is equal to 3 Spheres.",
        explanation = "4S + 1C = 2C + 1S => 3S = 1C. Thus 1 Cube balances 3 Spheres."
    ),
    LevelPuzzle(
        id = 69,
        difficulty = Difficulty.HARD,
        type = PuzzleType.RIDDLE,
        title = "Prime Factor Count",
        question = "How many total prime factors (counting duplicates) does the number 72 have?",
        visual = VisualData.SimpleVisual("72 = 2^a × 3^b | Total Prime Factors (a + b) = ?"),
        correctAnswer = "5",
        hint1 = "Find prime factorization: 72 = 8 × 9.",
        hint2 = "8 = 2³, 9 = 3².",
        hint3 = "3 twos and 2 threes: 3 + 2 = 5.",
        explanation = "72 = 2 × 2 × 2 × 3 × 3 = 2³ × 3². Total count of prime factors = 3 + 2 = 5."
    ),
    LevelPuzzle(
        id = 70,
        difficulty = Difficulty.HARD,
        type = PuzzleType.CLOCK_MATH,
        title = "Clock Angle at 3:30",
        question = "What is the angle in degrees between the hour and minute hands at 3:30?",
        visual = VisualData.ClockVisual(listOf("3:30")),
        correctAnswer = "75",
        hint1 = "At 3:30, minute hand is at 6 (180°).",
        hint2 = "Hour hand is halfway between 3 (90°) and 4 (120°): 105°.",
        hint3 = "180° - 105° = 75°.",
        explanation = "Minute hand = 180°. Hour hand = 30° × 3.5 = 105°. Angle = 180° - 105° = 75°."
    ),
    LevelPuzzle(
        id = 71,
        difficulty = Difficulty.HARD,
        type = PuzzleType.MATRIX,
        title = "Matrix Cubic Difference",
        question = "Analyze each row and find the missing value ?.",
        visual = VisualData.MatrixVisual(
            listOf(
                listOf("1", "2", "7"),
                listOf("2", "3", "19"),
                listOf("3", "4", "?")
            )
        ),
        correctAnswer = "37",
        hint1 = "Row 1: 2³ - 1³ = 8 - 1 = 7.",
        hint2 = "Row 2: 3³ - 2³ = 27 - 8 = 19.",
        hint3 = "Row 3: 4³ - 3³ = 64 - 27 = 37.",
        explanation = "Each row's third value is the difference of cubes (b³ - a³): 4³ - 3³ = 64 - 27 = 37."
    ),
    LevelPuzzle(
        id = 72,
        difficulty = Difficulty.HARD,
        type = PuzzleType.RIDDLE,
        title = "Age Problem",
        question = "A mother is 4 times as old as her daughter. In 20 years, she will be twice as old. How old is the daughter now?",
        visual = VisualData.SimpleVisual("Mother = 4 × Daughter | In 20 yrs: Mother = 2 × Daughter"),
        correctAnswer = "10",
        hint1 = "Let daughter = D, mother = 4D.",
        hint2 = "4D + 20 = 2(D + 20) => 4D + 20 = 2D + 40.",
        hint3 = "2D = 20 => D = 10.",
        explanation = "4D + 20 = 2(D + 20) => 4D + 20 = 2D + 40 => 2D = 20 => D = 10."
    ),
    LevelPuzzle(
        id = 73,
        difficulty = Difficulty.HARD,
        type = PuzzleType.RIDDLE,
        title = "Dice Roll Sum",
        question = "When rolling two standard 6-sided dice, how many distinct ordered pairs (Die 1, Die 2) sum to 7?",
        visual = VisualData.SimpleVisual("Die 1 + Die 2 = 7 | Total Pairs = ?"),
        correctAnswer = "6",
        hint1 = "List pairs: (1,6), (2,5), (3,4)...",
        hint2 = "Also consider (4,3), (5,2), (6,1).",
        hint3 = "There are 6 pairs.",
        explanation = "The pairs are (1,6), (2,5), (3,4), (4,3), (5,2), (6,1) — total of 6 outcomes."
    ),
    LevelPuzzle(
        id = 74,
        difficulty = Difficulty.HARD,
        type = PuzzleType.RIDDLE,
        title = "Handshake Theorem",
        question = "In a meeting of 8 people, everyone shakes hands with everyone else once. How many total handshakes occur?",
        visual = VisualData.SimpleVisual("8 People | Mutual Handshakes = ?"),
        correctAnswer = "28",
        hint1 = "Use combination formula: n × (n - 1) / 2.",
        hint2 = "8 × 7 / 2.",
        hint3 = "56 / 2 = 28.",
        explanation = "Total handshakes = 8 × 7 / 2 = 28."
    ),
    LevelPuzzle(
        id = 75,
        difficulty = Difficulty.HARD,
        type = PuzzleType.RIDDLE,
        title = "Hexagon Diagonals",
        question = "How many diagonals can be drawn inside a regular 6-sided polygon (hexagon)?",
        visual = VisualData.SimpleVisual("Hexagon (n = 6) | Diagonals = ?"),
        correctAnswer = "9",
        hint1 = "Formula for diagonals of an n-sided polygon is n(n - 3) / 2.",
        hint2 = "Substitute n = 6: 6 × (6 - 3) / 2.",
        hint3 = "6 × 3 / 2 = 9.",
        explanation = "Number of diagonals = n(n - 3) / 2 = 6 × 3 / 2 = 9."
    ),
    LevelPuzzle(
        id = 76,
        difficulty = Difficulty.HARD,
        type = PuzzleType.SEQUENCE,
        title = "Alternating Sign Series",
        question = "What number completes this alternating geometric series?",
        visual = VisualData.SequenceVisual(listOf("5", "-10", "20", "-40", "80", "?")),
        correctAnswer = "-160",
        hint1 = "Common ratio is negative 2 (-2).",
        hint2 = "Multiply 80 by -2.",
        hint3 = "80 × (-2) = -160.",
        explanation = "Geometric progression with common ratio r = -2: 80 × (-2) = -160."
    ),
    LevelPuzzle(
        id = 77,
        difficulty = Difficulty.HARD,
        type = PuzzleType.PYRAMID,
        title = "Pyramid Product Center",
        question = "In this multiplicative pyramid, each block is the product of two blocks below it. What is the middle base block?",
        visual = VisualData.PyramidVisual(
            listOf(
                listOf("180"),
                listOf("15", "12"),
                listOf("5", "?", "4")
            )
        ),
        correctAnswer = "3",
        hint1 = "Left middle block: 5 × ? = 15.",
        hint2 = "Right middle block: ? × 4 = 12.",
        hint3 = "Both equations confirm ? = 3.",
        explanation = "5 × ? = 15 => ? = 3, and 3 × 4 = 12. Both agree."
    ),
    LevelPuzzle(
        id = 78,
        difficulty = Difficulty.HARD,
        type = PuzzleType.RIDDLE,
        title = "Remainders Riddle",
        question = "Find the smallest positive integer that leaves remainder 2 when divided by 3, and remainder 3 when divided by 5.",
        visual = VisualData.SimpleVisual("N mod 3 = 2 | N mod 5 = 3 | Smallest N = ?"),
        correctAnswer = "8",
        hint1 = "Numbers with remainder 3 when divided by 5: 3, 8, 13, 18...",
        hint2 = "Check each modulo 3: 3 mod 3 = 0, 8 mod 3 = 2.",
        hint3 = "8 matches both conditions.",
        explanation = "8 = 2 × 3 + 2 (remainder 2), and 8 = 1 × 5 + 3 (remainder 3). Smallest positive solution is 8."
    ),
    LevelPuzzle(
        id = 79,
        difficulty = Difficulty.HARD,
        type = PuzzleType.EQUATION,
        title = "System of Reciprocals",
        question = "If a + b = 10 and 1/a + 1/b = 5/12, what is the product (a × b)?",
        visual = VisualData.SimpleVisual("a + b = 10 | 1/a + 1/b = 5/12 | a × b = ?"),
        correctAnswer = "24",
        hint1 = "1/a + 1/b = (a + b) / (a × b).",
        hint2 = "10 / (a × b) = 5/12.",
        hint3 = "a × b = 10 × 12 / 5 = 24.",
        explanation = "(a + b) / (ab) = 5/12 => 10 / (ab) = 5/12 => 5 × ab = 120 => ab = 24."
    ),
    LevelPuzzle(
        id = 80,
        difficulty = Difficulty.HARD,
        type = PuzzleType.CRYPTARITHM,
        title = "Cryptarithm TWO",
        question = "In TWO + TWO = FOUR, if O = 4, R = 8, and T = 7, what digit is W?",
        visual = VisualData.SimpleVisual("  7 W 4\n+ 7 W 4\n-------\n1 4 U 8 | W = ?"),
        correctAnswer = "3",
        hint1 = "In units column: 4 + 4 = 8 (no carry).",
        hint2 = "In hundreds column: 7 + 7 = 14 (no carry into hundreds from tens).",
        hint3 = "So W + W must be less than 10. If W = 3, U = 6.",
        explanation = "734 + 734 = 1468. O=4, R=8, T=7, W=3, U=6, F=1. All distinct digits, so W = 3."
    )
)
