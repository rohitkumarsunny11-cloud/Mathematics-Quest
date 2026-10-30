package com.example.data

import com.example.model.Difficulty
import com.example.model.LevelPuzzle
import com.example.model.PuzzleType
import com.example.model.VisualData

val levelsExpert: List<LevelPuzzle> = listOf(
    LevelPuzzle(
        id = 81,
        difficulty = Difficulty.EXPERT,
        type = PuzzleType.SEQUENCE,
        title = "Collatz Conjecture",
        question = "Following the rule (if even divide by 2, if odd 3n + 1), what is the next number after 26?",
        visual = VisualData.SequenceVisual(listOf("22", "11", "34", "17", "52", "26", "?")),
        correctAnswer = "13",
        hint1 = "26 is an even number.",
        hint2 = "Divide 26 by 2.",
        hint3 = "26 ÷ 2 = 13.",
        explanation = "Collatz rule: for even n, next is n / 2. Since 26 is even, 26 / 2 = 13."
    ),
    LevelPuzzle(
        id = 82,
        difficulty = Difficulty.EXPERT,
        type = PuzzleType.EQUATION,
        title = "Sum of Digits Power",
        question = "What is the sum of the digits of the number 2¹⁰?",
        visual = VisualData.SimpleVisual("2¹⁰ = ? | Sum of its digits = ?"),
        correctAnswer = "7",
        hint1 = "Evaluate 2¹⁰ first.",
        hint2 = "2¹⁰ = 1024.",
        hint3 = "1 + 0 + 2 + 4 = 7.",
        explanation = "2¹⁰ = 1024. The sum of digits is 1 + 0 + 2 + 4 = 7."
    ),
    LevelPuzzle(
        id = 83,
        difficulty = Difficulty.EXPERT,
        type = PuzzleType.RIDDLE,
        title = "Intersecting Chords",
        question = "Two chords AB and CD intersect at point P inside a circle. If AP = 4, PB = 6, and CP = 3, what is PD?",
        visual = VisualData.SimpleVisual("AP × PB = CP × PD | 4 × 6 = 3 × ?"),
        correctAnswer = "8",
        hint1 = "Intersecting Chords Theorem: AP × PB = CP × PD.",
        hint2 = "4 × 6 = 24. 3 × PD = 24.",
        hint3 = "PD = 24 ÷ 3 = 8.",
        explanation = "By the chord intersection theorem, AP × PB = CP × PD => 4 × 6 = 3 × PD => 24 = 3 × PD => PD = 8."
    ),
    LevelPuzzle(
        id = 84,
        difficulty = Difficulty.EXPERT,
        type = PuzzleType.EQUATION,
        title = "Wilson Factorial Modulo",
        question = "Evaluate 4! mod 5.",
        visual = VisualData.SimpleVisual("4! mod 5 = ?"),
        correctAnswer = "4",
        hint1 = "4! = 4 × 3 × 2 × 1 = 24.",
        hint2 = "Divide 24 by 5 and find the remainder.",
        hint3 = "24 = 4 × 5 + 4, remainder is 4.",
        explanation = "4! = 24. 24 mod 5 = 4 (also matches Wilson's Theorem: (p-1)! ≡ -1 ≡ p-1 mod p)."
    ),
    LevelPuzzle(
        id = 85,
        difficulty = Difficulty.EXPERT,
        type = PuzzleType.SEQUENCE,
        title = "Tetrahedral Numbers",
        question = "What is the 6th tetrahedral number in this 3D sphere stacking sequence?",
        visual = VisualData.SequenceVisual(listOf("1", "4", "10", "20", "35", "?")),
        correctAnswer = "56",
        hint1 = "Differences between terms are triangular numbers: +3, +6, +10, +15.",
        hint2 = "The next triangular number to add is 21.",
        hint3 = "35 + 21 = 56.",
        explanation = "Formula is n(n+1)(n+2)/6. For n=6: 6 × 7 × 8 / 6 = 56. (35 + 21 = 56)."
    ),
    LevelPuzzle(
        id = 86,
        difficulty = Difficulty.EXPERT,
        type = PuzzleType.SHAPE_ALGEBRA,
        title = "Complex System",
        question = "If ⭐ = 4, find the value of Triangle (🔺).",
        visual = VisualData.ShapeAlgebraVisual(
            listOf(
                "⭐ × 🔷 = 24",
                "🔷 - 🔺 = 2"
            ),
            "🔺 = ?"
        ),
        correctAnswer = "4",
        hint1 = "Substitute ⭐ = 4 into first equation.",
        hint2 = "4 × 🔷 = 24 => 🔷 = 6.",
        hint3 = "6 - 🔺 = 2 => 🔺 = 4.",
        explanation = "⭐ = 4 => 🔷 = 24 / 4 = 6. Then 6 - 🔺 = 2 => 🔺 = 4."
    ),
    LevelPuzzle(
        id = 87,
        difficulty = Difficulty.EXPERT,
        type = PuzzleType.RIDDLE,
        title = "Permutations Count",
        question = "How many distinct 4-letter anagrams can be formed using all letters of 'MATH'?",
        visual = VisualData.SimpleVisual("Letters: M, A, T, H | Distinct Permutations = ?"),
        correctAnswer = "24",
        hint1 = "All 4 letters are distinct.",
        hint2 = "Number of permutations is 4!.",
        hint3 = "4 × 3 × 2 × 1 = 24.",
        explanation = "Number of permutations of 4 distinct items = 4! = 24."
    ),
    LevelPuzzle(
        id = 88,
        difficulty = Difficulty.EXPERT,
        type = PuzzleType.RIDDLE,
        title = "AM-GM Difference",
        question = "Two positive numbers have Arithmetic Mean = 10 and Geometric Mean = 8. What is their difference (larger minus smaller)?",
        visual = VisualData.SimpleVisual("(a + b)/2 = 10 | √(ab) = 8 | |a - b| = ?"),
        correctAnswer = "12",
        hint1 = "a + b = 20 and a × b = 64.",
        hint2 = "The two numbers multiplying to 64 and adding to 20 are 16 and 4.",
        hint3 = "16 - 4 = 12.",
        explanation = "Roots of x² - 20x + 64 = 0 are 16 and 4. Difference = 16 - 4 = 12."
    ),
    LevelPuzzle(
        id = 89,
        difficulty = Difficulty.EXPERT,
        type = PuzzleType.MATRIX,
        title = "Matrix Determinant",
        question = "Evaluate the determinant of this 2x2 matrix: det = (ad - bc).",
        visual = VisualData.MatrixVisual(
            listOf(
                listOf("5", "3"),
                listOf("2", "4")
            )
        ),
        correctAnswer = "14",
        hint1 = "Multiply top-left by bottom-right: 5 × 4 = 20.",
        hint2 = "Multiply top-right by bottom-left: 3 × 2 = 6.",
        hint3 = "20 - 6 = 14.",
        explanation = "det = (5 × 4) - (3 × 2) = 20 - 6 = 14."
    ),
    LevelPuzzle(
        id = 90,
        difficulty = Difficulty.EXPERT,
        type = PuzzleType.CLOCK_MATH,
        title = "Clock Angle at 2:20",
        question = "What is the angle in degrees between the hour and minute hands at 2:20?",
        visual = VisualData.ClockVisual(listOf("2:20")),
        correctAnswer = "50",
        hint1 = "Minute hand is at 4: 4 × 30° = 120°.",
        hint2 = "Hour hand position: 2 × 30° + 20 × 0.5° = 60° + 10° = 70°.",
        hint3 = "120° - 70° = 50°.",
        explanation = "Minute hand = 120°. Hour hand = 70°. Difference = 120° - 70° = 50°."
    ),
    LevelPuzzle(
        id = 91,
        difficulty = Difficulty.EXPERT,
        type = PuzzleType.RIDDLE,
        title = "The Locker Problem",
        question = "Out of 100 lockers toggled 100 times by 100 students, how many lockers remain open in the end?",
        visual = VisualData.SimpleVisual("100 Lockers | Toggled by students 1..100 | Open Lockers = ?"),
        correctAnswer = "10",
        hint1 = "Only lockers with an odd number of divisors remain open.",
        hint2 = "Perfect square numbers have an odd number of divisors.",
        hint3 = "Count squares from 1² to 10² up to 100.",
        explanation = "Lockers remaining open correspond to perfect squares (1, 4, 9, 16, 25, 36, 49, 64, 81, 100). Total count = 10."
    ),
    LevelPuzzle(
        id = 92,
        difficulty = Difficulty.EXPERT,
        type = PuzzleType.EQUATION,
        title = "Digit Factorial",
        question = "Find the positive integer N such that N! = 120.",
        visual = VisualData.SimpleVisual("N! = 120 | N = ?"),
        correctAnswer = "5",
        hint1 = "Test factorials: 3! = 6, 4! = 24.",
        hint2 = "24 × 5 = 120.",
        hint3 = "N = 5.",
        explanation = "5! = 5 × 4 × 3 × 2 × 1 = 120. Therefore N = 5."
    ),
    LevelPuzzle(
        id = 93,
        difficulty = Difficulty.EXPERT,
        type = PuzzleType.RIDDLE,
        title = "Hexadecimal 2F",
        question = "Convert hexadecimal 2F to decimal (base 10).",
        visual = VisualData.SimpleVisual("2F₁₆ = ?₁₀"),
        correctAnswer = "47",
        hint1 = "F in hex represents 15.",
        hint2 = "2 × 16¹ + 15 × 16⁰.",
        hint3 = "32 + 15 = 47.",
        explanation = "2 × 16 + 15 = 32 + 15 = 47."
    ),
    LevelPuzzle(
        id = 94,
        difficulty = Difficulty.EXPERT,
        type = PuzzleType.SEQUENCE,
        title = "Pisano Period",
        question = "What is the next number in this Fibonacci sequence modulo 3?",
        visual = VisualData.SequenceVisual(listOf("1", "1", "2", "0", "2", "2", "1", "0", "1", "1", "?")),
        correctAnswer = "2",
        hint1 = "Look at the recurring cycle: 1, 1, 2, 0, 2, 2, 1, 0...",
        hint2 = "After 1, 1 comes 2.",
        hint3 = "The next term is 2.",
        explanation = "The Pisano period mod 3 repeats every 8 terms: (1, 1, 2, 0, 2, 2, 1, 0). Following 1, 1 is 2."
    ),
    LevelPuzzle(
        id = 95,
        difficulty = Difficulty.EXPERT,
        type = PuzzleType.EQUATION,
        title = "Vieta's Formula",
        question = "What is the sum of the roots of the quadratic equation x² - 14x + 45 = 0?",
        visual = VisualData.SimpleVisual("x² - 14x + 45 = 0 | Sum of roots = ?"),
        correctAnswer = "14",
        hint1 = "For ax² + bx + c = 0, sum of roots is -b/a.",
        hint2 = "-(-14)/1 = 14.",
        hint3 = "The roots are 5 and 9 (5 + 9 = 14).",
        explanation = "By Vieta's relations, the sum of roots is -(-14) / 1 = 14 (roots are 9 and 5)."
    ),
    LevelPuzzle(
        id = 96,
        difficulty = Difficulty.EXPERT,
        type = PuzzleType.RIDDLE,
        title = "Coin Weighing Puzzle",
        question = "Out of 9 coins with 1 heavier counterfeit, what is the minimum number of balance scale weighings needed to guarantee finding it?",
        visual = VisualData.SimpleVisual("9 coins (1 heavy) | Two-pan balance scale | Min weighings = ?"),
        correctAnswer = "2",
        hint1 = "Split the 9 coins into 3 groups of 3.",
        hint2 = "Weighing 1 finds the heavy group of 3. Weighing 2 finds the heavy coin.",
        hint3 = "3² = 9, so 2 weighings suffice.",
        explanation = "Group into 3, 3, 3. First weighing isolates the 3-coin group with the heavy coin. Second weighing of 1 vs 1 from that group identifies the counterfeit. Total = 2."
    ),
    LevelPuzzle(
        id = 97,
        difficulty = Difficulty.EXPERT,
        type = PuzzleType.EQUATION,
        title = "Difference of Fourth Powers",
        question = "Calculate 3⁴ - 2⁴.",
        visual = VisualData.SimpleVisual("3⁴ - 2⁴ = ?"),
        correctAnswer = "65",
        hint1 = "3⁴ = 81.",
        hint2 = "2⁴ = 16.",
        hint3 = "81 - 16 = 65.",
        explanation = "3⁴ = 81, 2⁴ = 16. 81 - 16 = 65."
    ),
    LevelPuzzle(
        id = 98,
        difficulty = Difficulty.EXPERT,
        type = PuzzleType.LOGIC_GRID,
        title = "Magic Triangle Corners",
        question = "Digits 1 to 6 are placed on a triangle's vertices and side midpoints so each side sums to 9. What is the sum of the 3 corner vertex numbers?",
        visual = VisualData.SimpleVisual("Digits 1–6 on Triangle sides | Side Sum = 9 | Corner Sum = ?"),
        correctAnswer = "6",
        hint1 = "Side sum = 9, 3 sides total = 27.",
        hint2 = "Sum of digits 1..6 is 21. The 3 corners are counted twice: 27 - 21 = 6.",
        hint3 = "The corner sum must be 6 (using corners 1, 2, 3).",
        explanation = "Total side sum is 3 × 9 = 27. Digits 1 to 6 sum to 21. Since the 3 corners are counted twice: 27 - 21 = 6."
    ),
    LevelPuzzle(
        id = 99,
        difficulty = Difficulty.EXPERT,
        type = PuzzleType.EQUATION,
        title = "Infinite Geometric Series",
        question = "What is the exact sum of the infinite series: 1/2 + 1/4 + 1/8 + 1/16 + ...?",
        visual = VisualData.SimpleVisual("1/2 + 1/4 + 1/8 + ... = ?"),
        correctAnswer = "1",
        hint1 = "First term a = 1/2, common ratio r = 1/2.",
        hint2 = "Formula: S = a / (1 - r).",
        hint3 = "(1/2) / (1 - 1/2) = 1.",
        explanation = "Infinite sum = a / (1 - r) = (1/2) / (1/2) = 1."
    ),
    LevelPuzzle(
        id = 100,
        difficulty = Difficulty.EXPERT,
        type = PuzzleType.RIDDLE,
        title = "The Century Grandmaster",
        question = "What is the 100th positive integer that is neither a multiple of 2 nor a multiple of 5?",
        visual = VisualData.SimpleVisual("Coprime to 10: 1, 3, 7, 9, 11, 13, 17, 19... | 100th term = ?"),
        correctAnswer = "249",
        hint1 = "In every block of 10 integers, exactly 4 end in 1, 3, 7, or 9 (coprime to 10).",
        hint2 = "100 terms = 25 blocks of 4. The 100th term is the last in the 25th block.",
        hint3 = "25 × 10 = 250. The last number ending in 9 before 250 is 249.",
        explanation = "Numbers coprime to 10 end in 1, 3, 7, 9 (4 per decade). 100 terms require exactly 25 decades (100 / 4 = 25). The 100th term is the 4th in the 25th decade: 240 + 9 = 249."
    )
)
