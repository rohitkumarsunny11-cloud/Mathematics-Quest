package com.example.data

import com.example.model.Difficulty
import com.example.model.LevelPuzzle
import com.example.model.PuzzleType
import com.example.model.VisualData
import kotlin.random.Random

object InfiniteLevelGenerator {

    fun generateLevel(id: Int): LevelPuzzle {
        require(id > 100) { "InfiniteLevelGenerator only handles levels 101 and above. Given: $id" }

        val seed = id.toLong() * 999983L + 42L
        val rng = Random(seed)

        val difficulty = when {
            id <= 140 -> Difficulty.MEDIUM
            id <= 200 -> Difficulty.HARD
            else -> Difficulty.EXPERT
        }

        val rewardCoins = when {
            id <= 140 -> 45
            id <= 200 -> 60
            id <= 300 -> 75
            else -> 90
        }

        // Rotate through archetypes based on ID and seed
        val archetype = (id + rng.nextInt(100)) % 7

        return when (archetype) {
            0 -> generateArithmeticSequence(id, difficulty, rewardCoins, rng)
            1 -> generateShapeAlgebra(id, difficulty, rewardCoins, rng)
            2 -> generatePyramidPuzzle(id, difficulty, rewardCoins, rng)
            3 -> generateMatrixPuzzle(id, difficulty, rewardCoins, rng)
            4 -> generateOperatorDiscovery(id, difficulty, rewardCoins, rng)
            5 -> generateBalanceScale(id, difficulty, rewardCoins, rng)
            else -> generateWordAlgebraRiddle(id, difficulty, rewardCoins, rng)
        }
    }

    private fun generateArithmeticSequence(
        id: Int,
        difficulty: Difficulty,
        reward: Int,
        rng: Random
    ): LevelPuzzle {
        val patternType = rng.nextInt(3)
        return when (patternType) {
            0 -> {
                // Linear arithmetic sequence with variable step
                val step = rng.nextInt(3, 12) + (id / 100)
                val start = rng.nextInt(5, 30)
                val term1 = start
                val term2 = term1 + step
                val term3 = term2 + step
                val term4 = term3 + step
                val answer = (term4 + step).toString()

                LevelPuzzle(
                    id = id,
                    difficulty = difficulty,
                    type = PuzzleType.SEQUENCE,
                    title = "Infinite Step #$id",
                    question = "Find the missing number in the progression.",
                    visual = VisualData.SequenceVisual(listOf("$term1", "$term2", "$term3", "$term4", "?")),
                    correctAnswer = answer,
                    hint1 = "Check the difference between consecutive numbers.",
                    hint2 = "Each number increases by $step.",
                    hint3 = "Add $step to $term4: $term4 + $step = $answer.",
                    explanation = "This is an arithmetic progression where each term increases by $step. Therefore, $term4 + $step = $answer.",
                    rewardCoins = reward
                )
            }
            1 -> {
                // Second-order / increasing difference sequence
                val start = rng.nextInt(2, 10)
                val diffBase = rng.nextInt(2, 5)
                val step1 = diffBase
                val step2 = diffBase + 2
                val step3 = diffBase + 4
                val step4 = diffBase + 6

                val n1 = start
                val n2 = n1 + step1
                val n3 = n2 + step2
                val n4 = n3 + step3
                val answer = (n4 + step4).toString()

                LevelPuzzle(
                    id = id,
                    difficulty = difficulty,
                    type = PuzzleType.SEQUENCE,
                    title = "Expanding Delta #$id",
                    question = "Determine the next term in the sequence.",
                    visual = VisualData.SequenceVisual(listOf("$n1", "$n2", "$n3", "$n4", "?")),
                    correctAnswer = answer,
                    hint1 = "Look at how much the step between numbers grows each time.",
                    hint2 = "The differences are +$step1, +$step2, +$step3, ... increasing by 2 each step.",
                    hint3 = "The next step is +$step4. Add $step4 to $n4: $n4 + $step4 = $answer.",
                    explanation = "The difference between consecutive terms increases by 2 each time (+$step1, +$step2, +$step3, +$step4). $n4 + $step4 = $answer.",
                    rewardCoins = reward
                )
            }
            else -> {
                // Two-step rule: Multiply then subtract/add
                val mult = 2
                val add = rng.nextInt(1, 5)
                val t1 = rng.nextInt(2, 7)
                val t2 = t1 * mult + add
                val t3 = t2 * mult + add
                val t4 = t3 * mult + add
                val answer = (t4 * mult + add).toString()

                LevelPuzzle(
                    id = id,
                    difficulty = difficulty,
                    type = PuzzleType.SEQUENCE,
                    title = "Geometric Leap #$id",
                    question = "Discover the operational rule to find the missing term.",
                    visual = VisualData.SequenceVisual(listOf("$t1", "$t2", "$t3", "$t4", "?")),
                    correctAnswer = answer,
                    hint1 = "Try multiplying each number by 2 and see what remains.",
                    hint2 = "The rule is: (previous term × 2) + $add.",
                    hint3 = "Compute ($t4 × 2) + $add = $answer.",
                    explanation = "Each term is generated by multiplying the preceding term by 2 and adding $add. Hence ($t4 × 2) + $add = $answer.",
                    rewardCoins = reward
                )
            }
        }
    }

    private fun generateShapeAlgebra(
        id: Int,
        difficulty: Difficulty,
        reward: Int,
        rng: Random
    ): LevelPuzzle {
        val valA = rng.nextInt(3, 10)
        val valB = rng.nextInt(2, 8)
        val valC = rng.nextInt(2, 6)

        val eq1Sum = valA * 3
        val eq2Sum = valA + valB * 2
        val eq3Sum = valB + valC * 2
        val answer = (valA + valB * valC).toString()

        return LevelPuzzle(
            id = id,
            difficulty = difficulty,
            type = PuzzleType.SHAPE_ALGEBRA,
            title = "Symbolic Matrix #$id",
            question = "Solve for each symbol and calculate the final value.",
            visual = VisualData.ShapeAlgebraVisual(
                equations = listOf(
                    "🔺 + 🔺 + 🔺 = $eq1Sum",
                    "🔺 + 🔷 + 🔷 = $eq2Sum",
                    "🔷 + 🟢 + 🟢 = $eq3Sum"
                ),
                questionLine = "🔺 + 🔷 × 🟢 = ?"
            ),
            correctAnswer = answer,
            hint1 = "From equation 1: 3 × 🔺 = $eq1Sum, so 🔺 = ${valA}.",
            hint2 = "From equation 2: $valA + 2 × 🔷 = $eq2Sum, so 🔷 = $valB. From equation 3: 🟢 = $valC.",
            hint3 = "Remember multiplication comes before addition: 🔺 + (🔷 × 🟢) = $valA + ($valB × $valC) = $answer.",
            explanation = "From row 1, 🔺 = $eq1Sum ÷ 3 = $valA. From row 2, 🔷 = ($eq2Sum - $valA) ÷ 2 = $valB. From row 3, 🟢 = ($eq3Sum - $valB) ÷ 2 = $valC. Evaluating 🔺 + 🔷 × 🟢 gives $valA + ($valB × $valC) = $answer.",
            rewardCoins = reward
        )
    }

    private fun generatePyramidPuzzle(
        id: Int,
        difficulty: Difficulty,
        reward: Int,
        rng: Random
    ): LevelPuzzle {
        val b1 = rng.nextInt(3, 12)
        val b2 = rng.nextInt(2, 10)
        val b3 = rng.nextInt(4, 15)

        val m1 = b1 + b2
        val m2 = b2 + b3
        val top = m1 + m2
        val answer = top.toString()

        return LevelPuzzle(
            id = id,
            difficulty = difficulty,
            type = PuzzleType.PYRAMID,
            title = "Sum Pyramid #$id",
            question = "Each brick is the sum of the two bricks directly beneath it. Find the top brick value.",
            visual = VisualData.PyramidVisual(
                rows = listOf(
                    listOf("?"),
                    listOf("$m1", "$m2"),
                    listOf("$b1", "$b2", "$b3")
                )
            ),
            correctAnswer = answer,
            hint1 = "Each brick equals the sum of the two bricks directly below it.",
            hint2 = "The middle bricks are $m1 and $m2.",
            hint3 = "Add the middle tier bricks: $m1 + $m2 = $answer.",
            explanation = "The top brick is the sum of the two supporting middle bricks: $m1 + $m2 = $answer.",
            rewardCoins = reward
        )
    }

    private fun generateMatrixPuzzle(
        id: Int,
        difficulty: Difficulty,
        reward: Int,
        rng: Random
    ): LevelPuzzle {
        val rowTarget = rng.nextInt(20, 45)
        val r1c1 = rng.nextInt(4, 12)
        val r1c2 = rng.nextInt(5, 12)
        val r1c3 = rowTarget - r1c1 - r1c2

        val r2c1 = rng.nextInt(3, 11)
        val r2c2 = rng.nextInt(4, 13)
        val r2c3 = rowTarget - r2c1 - r2c2

        val r3c1 = rng.nextInt(2, 10)
        val r3c2 = rng.nextInt(6, 14)
        val r3c3 = rowTarget - r3c1 - r3c2
        val answer = r3c3.toString()

        return LevelPuzzle(
            id = id,
            difficulty = difficulty,
            type = PuzzleType.MATRIX,
            title = "Equal Row Grid #$id",
            question = "Each row in the 3×3 grid sums to the same constant. Find the missing number.",
            visual = VisualData.MatrixVisual(
                grid = listOf(
                    listOf("$r1c1", "$r1c2", "$r1c3"),
                    listOf("$r2c1", "$r2c2", "$r2c3"),
                    listOf("$r3c1", "$r3c2", "?")
                )
            ),
            correctAnswer = answer,
            hint1 = "Sum the numbers in the first complete row ($r1c1 + $r1c2 + $r1c3).",
            hint2 = "Every row sums to $rowTarget.",
            hint3 = "For the bottom row: $rowTarget - $r3c1 - $r3c2 = $answer.",
            explanation = "Row 1 sums to $r1c1 + $r1c2 + $r1c3 = $rowTarget. Row 2 sums to $r2c1 + $r2c2 + $r2c3 = $rowTarget. The third row must also sum to $rowTarget: $rowTarget - ($r3c1 + $r3c2) = $answer.",
            rewardCoins = reward
        )
    }

    private fun generateOperatorDiscovery(
        id: Int,
        difficulty: Difficulty,
        reward: Int,
        rng: Random
    ): LevelPuzzle {
        // Rule: a ⊕ b = (a × b) + (a + b)
        val a1 = 3
        val b1 = 4
        val res1 = (a1 * b1) + (a1 + b1) // 12 + 7 = 19

        val a2 = 2
        val b2 = 5
        val res2 = (a2 * b2) + (a2 + b2) // 10 + 7 = 17

        val a3 = rng.nextInt(4, 7)
        val b3 = rng.nextInt(3, 6)
        val answer = ((a3 * b3) + (a3 + b3)).toString()

        return LevelPuzzle(
            id = id,
            difficulty = difficulty,
            type = PuzzleType.OPERATOR,
            title = "Rule Finder #$id",
            question = "Discover the hidden rule linking the inputs to their results:\n$a1 ⊕ $b1 = $res1\n$a2 ⊕ $b2 = $res2\n$a3 ⊕ $b3 = ?",
            visual = VisualData.SimpleVisual(
                promptText = "$a1 ⊕ $b1 = $res1\n$a2 ⊕ $b2 = $res2\n$a3 ⊕ $b3 = ?"
            ),
            correctAnswer = answer,
            hint1 = "Try multiplying the two numbers, and then adding their sum.",
            hint2 = "Rule: a ⊕ b = (a × b) + (a + b). Check $a1×$b1 + ($a1+$b1) = $res1.",
            hint3 = "For $a3 ⊕ $b3: ($a3 × $b3) + ($a3 + $b3) = ${a3 * b3} + ${a3 + b3} = $answer.",
            explanation = "The operator ⊕ calculates (a × b) + (a + b). For $a3 and $b3: (${a3 * b3}) + (${a3 + b3}) = $answer.",
            rewardCoins = reward
        )
    }

    private fun generateBalanceScale(
        id: Int,
        difficulty: Difficulty,
        reward: Int,
        rng: Random
    ): LevelPuzzle {
        val singleWeight = rng.nextInt(4, 12)
        val countLeft = rng.nextInt(2, 5)
        val totalWeight = countLeft * singleWeight

        val answer = singleWeight.toString()

        return LevelPuzzle(
            id = id,
            difficulty = difficulty,
            type = PuzzleType.EQUATION,
            title = "Equilibrium Scale #$id",
            question = "The balance scale is in perfect equilibrium. Find the weight of one 🔷.",
            visual = VisualData.BalanceVisual(
                leftSide = "$countLeft × 🔷",
                rightSide = "${totalWeight}kg",
                queryLine = "1 × 🔷 = ? kg"
            ),
            correctAnswer = answer,
            hint1 = "Both sides of the balance weigh the exact same amount.",
            hint2 = "$countLeft blocks balance ${totalWeight}kg.",
            hint3 = "Divide total weight by block count: $totalWeight ÷ $countLeft = $answer.",
            explanation = "Since $countLeft × 🔷 = ${totalWeight}kg, each 🔷 weighs ${totalWeight} ÷ $countLeft = ${answer}kg.",
            rewardCoins = reward
        )
    }

    private fun generateWordAlgebraRiddle(
        id: Int,
        difficulty: Difficulty,
        reward: Int,
        rng: Random
    ): LevelPuzzle {
        val sonAge = rng.nextInt(8, 16)
        val dadAge = sonAge * 3
        val years = sonAge // in 'years' years: dadAge + years = 2 * (sonAge + years) -> 4 * sonAge = 2 * 2 * sonAge
        // dadAge = 3 * sonAge. After Y years: (3S + Y) = 2 * (S + Y) => 3S + Y = 2S + 2Y => S = Y.
        // So in 'sonAge' years, father is twice as old as son!
        val answer = sonAge.toString()

        return LevelPuzzle(
            id = id,
            difficulty = difficulty,
            type = PuzzleType.RIDDLE,
            title = "Age Paradox #$id",
            question = "A father is 3 times as old as his daughter. In $sonAge years, he will be exactly twice her age. How old is the daughter right now?",
            visual = VisualData.SimpleVisual(
                promptText = "Father = 3 × Daughter\nIn $sonAge years: Father = 2 × Daughter\nDaughter age now = ?"
            ),
            correctAnswer = answer,
            hint1 = "Let daughter's current age be D. Father's current age is 3D.",
            hint2 = "In $sonAge years: (3D + $sonAge) = 2(D + $sonAge).",
            hint3 = "Expand equation: 3D + $sonAge = 2D + ${sonAge * 2} => D = $answer.",
            explanation = "Let D be daughter's age. Father is 3D. In $sonAge years, 3D + $sonAge = 2(D + $sonAge) = 2D + ${sonAge * 2}. Subtracting 2D and $sonAge gives D = $sonAge.",
            rewardCoins = reward
        )
    }
}
