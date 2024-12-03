package main.solution

import main.core.Puzzle

class Puzzle3(inputData: List<String>) : Puzzle(inputData) {

    override fun firstPuzzleSolution(inputData: List<String>): Any {
        return countSumOfMultiplayer(inputData)
    }

    override fun secondPuzzleSolution(inputData: List<String>): Any {
        return inputData
            .asPlainText()
            .let(String::getSubstringThatDo)
            .let(::countSumOfMultiplayer)
    }

    private fun countSumOfMultiplayer(inputData: List<String>): Int {
        return inputData
            .asSequence()
            .flatMap(multiplayerRegex::findAll)
            .map(MatchResult::value)
            .flatMap(numberRegex::findAll)
            .map(MatchResult::value)
            .map(String::asNumberPair)
            .map(Pair<Int, Int>::multiply)
            .sum()
    }

    companion object {
        const val INPUT_FILE_NAME = "puzzle_3_input.txt"
        const val INPUT_TEST_FILE_NAME = "testdata/puzzle_3_input.txt"
    }
}

private fun String.asNumberPair(): Pair<Int, Int> {
    return with(split(NUMBER_SPLITTER)) {
        first().toInt() to last().toInt()
    }
}

private fun Pair<Int, Int>.multiply(): Int {
    return first * second
}

private fun List<String>.asPlainText(): String {
    return joinToString(EMPTY_SEPARATOR)
}

private fun String.getSubstringThatDo(isLastDo: Boolean = true): List<String> {
    val substringList = mutableListOf<String>()
    var cIndex = 0
    var isDo = isLastDo

    while (cIndex < lastIndex) {
        val searchPhrase = if (isDo) STOP_SUBSTRING_PHRASE else START_SUBSTRING_PHRASE
        val indexOfOccurrence = indexOf(searchPhrase, cIndex)

        if (indexOfOccurrence == -1) {
            if (isDo) substringList.add(substring(cIndex, length))
            cIndex = length
        } else {
            if (isDo) substringList.add(substring(cIndex, indexOfOccurrence))
            cIndex = indexOfOccurrence + searchPhrase.length
            isDo = !isDo
        }
    }

    return substringList
}

private val multiplayerRegex by lazy {
    MULTIPLAYER_SUBSTRING_REGEX.toRegex()
}

private val numberRegex by lazy {
    NUMBER_SUBSTRING_REGEX.toRegex()
}

private const val MULTIPLAYER_SUBSTRING_REGEX = "mul\\(\\d+,\\d+\\)"
private const val NUMBER_SUBSTRING_REGEX = "\\d+,\\d+"
private const val NUMBER_SPLITTER = ","
private const val EMPTY_SEPARATOR = ""

private const val STOP_SUBSTRING_PHRASE = "don't()"
private const val START_SUBSTRING_PHRASE = "do()"
