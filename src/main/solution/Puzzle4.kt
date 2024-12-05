package main.solution

import main.core.Puzzle
import kotlin.math.min

class Puzzle4(inputData: List<String>) : Puzzle(inputData) {

    override fun firstPuzzleSolution(inputData: List<String>): Any {
        return inputData
            .map(String::toCharArray)
            .toTypedArray()
            .let(::countXmasOccurrence)
    }

    override fun secondPuzzleSolution(inputData: List<String>): Any {
        return inputData
            .map(String::toCharArray)
            .toTypedArray()
            .let(::countMasOccurrence)
    }

    private fun countXmasOccurrence(input: Array<CharArray>): Int {
        val height = input.size
        val width = input.firstOrNull()?.size ?: return 0

        return (0 ..< height).sumOf { row ->
            (0 ..< width).sumOf { col ->
                getCountOfOccurrence(input, row, col, XMAS_WORD, Direction.ANY)
            }
        }
    }

    private fun countMasOccurrence(input: Array<CharArray>): Int {
        val height = input.size
        val width = input.firstOrNull()?.size ?: return 0

        return (0 ..< height).sumOf { row ->
            (0 ..< width).count { col ->
                input[row][col] == 'A' && hasDiagonalMas(input, row, col)
            }
        }
    }

    private fun hasDiagonalMas(input: Array<CharArray>, row: Int, col: Int): Boolean {
        val height = input.size
        val width = input.firstOrNull()?.size ?: return false

        val xStart = intArrayOf(col - 1, col + 1)
        val yStart = intArrayOf(row + 1, row - 1)

        var counter = 0

        for (y in yStart) {
            for (x in xStart) {
                if (x >= height || x < 0 || y >= width || y < 0) {
                    return false
                }

                val direction = when {
                    x < col && y > row -> Direction.UP_RIGHT
                    x > col && y > row -> Direction.UP_LEFT
                    x < col && y < row -> Direction.DOWN_RIGHT
                    x > col && y < row -> Direction.DOWN_LEFT
                    else -> return false
                }

                counter += getCountOfOccurrence(input, y, x, MAS_WORD, direction)

                if (counter == 2) return true
            }
        }

        return false
    }

    private fun getCountOfOccurrence(
        matrix: Array<CharArray>,
        row: Int,
        col: Int,
        word: String,
        direction: Direction
    ): Int {
        if (matrix[row][col] != word[0]) return 0

        val height = matrix.size
        val width = matrix.firstOrNull()?.size ?: return 0

        var occurrenceCounter = 0

        val xDirection = direction.x
        val yDirection = direction.y

        for (dir in 0 ..< direction.variantsCount) {
            var cY = row + yDirection[dir]
            var cX = col + xDirection[dir]

            var hasOccurrence = true

            for (i in 1 ..< word.length) {
                if (cY >= height || cY < 0 || cX >= width || cX < 0) {
                    hasOccurrence = false
                    break
                }

                if (matrix[cY][cX] != word[i]) {
                    hasOccurrence = false
                    break
                }

                if (i == word.lastIndex) continue

                cY += yDirection[dir]
                cX += xDirection[dir]
            }

            if (hasOccurrence) {
                occurrenceCounter++
            }
        }

        return occurrenceCounter
    }

    companion object {
        const val INPUT_FILE_NAME = "puzzle_4_input.txt"
        const val INPUT_TEST_FILE_NAME = "testdata/puzzle_4_input.txt"
    }
}

enum class Direction(
    val x: IntArray,
    val y: IntArray
) {

    ANY(
        x = intArrayOf(-1, -1, -1, 0, 0, 1, 1, 1),
        y = intArrayOf(-1, 0, 1, -1, 1, -1, 0, 1)
    ),

    DOWN_RIGHT(
        x = intArrayOf(1),
        y = intArrayOf(1)
    ),

    UP_RIGHT(
        x = intArrayOf(1),
        y = intArrayOf(-1)
    ),

    DOWN_LEFT(
        x = intArrayOf(-1),
        y = intArrayOf(1)
    ),

    UP_LEFT(
        x = intArrayOf(-1),
        y = intArrayOf(-1)
    );

    val variantsCount: Int = min(x.size, y.size)

}

private const val XMAS_WORD = "XMAS"
private const val MAS_WORD = "MAS"
private const val AM_WORD = "AM"
private const val AS_WORD = "AS"