package main.solution

import main.core.Puzzle

class Puzzle6(inputData: List<String>) : Puzzle(inputData) {

    override fun firstPuzzleSolution(inputData: List<String>): Any {
        var currentMap = inputData.map(String::toCharArray).toTypedArray()

        var cRow = currentMap.indexOfFirst { it.contains(START_MARK) }
        var cCol = inputData[cRow].indexOf(START_MARK)

        while (cRow > 0 && cRow < currentMap.size && cCol > 0 && cCol < currentMap.first().size) {
            if (currentMap[cRow - 1][cCol] == BARRIER_MARK) {
                cCol = cRow.also {
                    cRow = currentMap[cRow].lastIndex - cCol
                }
                currentMap = currentMap.rotateCounter90()
                continue
            }

            currentMap[cRow][cCol] = VISITED_MARK
            cRow -= 1
        }

        currentMap = currentMap.rotateCounter90().rotateCounter90()

        return currentMap.sumOf { row ->
            row.count(VISITED_MARK::equals)
        } + 1 // first point
    }

    override fun secondPuzzleSolution(inputData: List<String>): Any {
        return ""
    }

    companion object {
        const val INPUT_FILE_NAME = "puzzle_6_input.txt"
        const val INPUT_TEST_FILE_NAME = "testdata/puzzle_6_input.txt"
    }
}

private fun Array<CharArray>.rotateCounter90(): Array<CharArray> {
    val height = size
    val width = first().size

    val rotated = Array(width) { CharArray(height) }

    for (row in (0 ..< height)) {
        for (col in (0 ..< width)) {
            rotated[width - col - 1][row] = this[row][col]
        }
    }

    return rotated
}

private const val START_MARK = '^'
private const val BARRIER_MARK = '#'
private const val VISITED_MARK = 'X'