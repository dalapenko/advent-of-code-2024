package main.solution

import main.core.Puzzle
import kotlin.math.abs

class Puzzle1(inputData: List<String>) : Puzzle(inputData) {

    override fun firstPuzzleSolution(inputData: List<String>): Any {
        val firstColumn = mutableListOf<Int>()
        val secondColumn = mutableListOf<Int>()

        inputData
            .forEach {
                val splittedRow = it.split(PUZZLE_COLUMN_SPLITTER)
                firstColumn.add(splittedRow.first().toInt())
                secondColumn.add(splittedRow.last().toInt())
            }

        firstColumn.sort()
        secondColumn.sort()

        var totalDistance = 0

        for (index in firstColumn.indices) {
            totalDistance += abs(firstColumn[index] - secondColumn[index])
        }

        return totalDistance
    }

    override fun secondPuzzleSolution(inputData: List<String>): Any {
        val secondColumn = mutableMapOf<Int, Int>()

        return inputData
            .map {
                val splittedRow = it.split(PUZZLE_COLUMN_SPLITTER)
                val secondNumber = splittedRow.last().toInt()
                secondColumn[secondNumber] = secondColumn.getOrDefault(secondNumber, 0).inc()

                return@map splittedRow.first().toInt()
            }.fold(0) { totalDiff, number ->
                totalDiff + (number * secondColumn.getOrDefault(number, 0))
            }
    }

    companion object {
        const val INPUT_FILE_NAME = "puzzle_1_input.txt"
        const val INPUT_TEST_FILE_NAME = "testdata/puzzle_1_input.txt"
    }
}

private const val PUZZLE_COLUMN_SPLITTER = "   "