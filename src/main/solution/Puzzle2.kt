package main.solution

import main.core.Puzzle

class Puzzle2(inputData: List<String>) : Puzzle(inputData) {

    override fun firstPuzzleSolution(inputData: List<String>): Any {
        return inputData
            .asSequence()
            .map { report ->
                report
                    .split(PUZZLE_ROW_DATA_SPLITTER)
                    .map(String::toInt)
            }.filter(::isSafeReport)
            .count()
    }

    override fun secondPuzzleSolution(inputData: List<String>): Any {
        return inputData
            .asSequence()
            .map { report ->
                report
                    .split(PUZZLE_ROW_DATA_SPLITTER)
                    .map(String::toInt)
            }.count { report ->
                isSafeReport(report) || hasSafeVariant(report)
            }
    }

    private fun isSafeReport(report: List<Int>): Boolean {
        val firstInReport= report.first()

        val isIncreased = report.first() < report.last()
        var lastNum = firstInReport

        (1 ..< report.size).forEach { index ->
            val cNum = report[index]

            val diff = if (isIncreased) cNum - lastNum else lastNum - cNum
            val isCorrectOrder = if (isIncreased) cNum > lastNum else cNum < lastNum

            if (!isCorrectOrder || diff < 1 || diff > 3) {
                return false
            }

            lastNum = cNum
        }

        return true
    }

    private fun hasSafeVariant(report: List<Int>): Boolean {
        val possibleVariance = report.indices.map { index ->
            report.subList(0, index) + report.subList(index + 1, report.size)
        }

        return possibleVariance.any(::isSafeReport)
    }

    companion object {
        const val INPUT_FILE_NAME = "puzzle_2_input.txt"
        const val INPUT_TEST_FILE_NAME = "testdata/puzzle_2_input.txt"
    }
}

private const val PUZZLE_ROW_DATA_SPLITTER = " "