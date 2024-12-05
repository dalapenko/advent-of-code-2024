package main.solution

import main.core.Puzzle

class Puzzle5(inputData: List<String>) : Puzzle(inputData) {

    override fun firstPuzzleSolution(inputData: List<String>): Any {
        val splitterIndex = inputData.indexOf(DATA_SEPARATOR)
        val (pageOrderData, updatesData) = inputData.take(splitterIndex) to inputData.drop(splitterIndex + 1)

        val pageOrder = PageOrder(pageOrderData)

        val correctInstructionFilter: (Sequence<Int>) -> Boolean = { iterable ->
            iterable
                .mapIndexed(::Pair)
                .all { (index, number) ->
                    val prevList = iterable.take(index).toSet()
                    val prevRequiredList = pageOrder[number] ?: return@all true

                    !prevList.any(prevRequiredList::contains)
                }
        }

        return updatesData
            .asSequence()
            .map(String::toSequenceOfInt)
            .filter(correctInstructionFilter)
            .map(Sequence<Int>::toList)
            .map(List<Int>::getMidValue)
            .sum()
    }

    override fun secondPuzzleSolution(inputData: List<String>): Any {
        val splitterIndex = inputData.indexOf(DATA_SEPARATOR)
        val (pageOrderData, updatesData) = inputData.take(splitterIndex) to inputData.drop(splitterIndex + 1)

        val pageOrder = PageOrder(pageOrderData)

        val correctInstructionFilter: (Sequence<Int>) -> Boolean = { iterable ->
            iterable
                .mapIndexed(::Pair)
                .all { (index, number) ->
                    val prevList = iterable.take(index).toSet()
                    val prevRequiredList = pageOrder[number] ?: return@all true

                    !prevList.any(prevRequiredList::contains)
                }
        }

        val orderingComparator = Comparator<Int> { num1, num2 ->
            val prevRequiredList = pageOrder[num1] ?: return@Comparator 0

            if (prevRequiredList.contains(num2)) -1 else 0
        }

        val sortedMapper: (Sequence<Int>) -> Sequence<Int> = { iterable ->
            iterable.sortedWith(orderingComparator)
        }

        return updatesData
            .asSequence()
            .map(String::toSequenceOfInt)
            .filterNot(correctInstructionFilter)
            .map(sortedMapper)
            .map(Sequence<Int>::toList)
            .map(List<Int>::getMidValue)
            .sum()
    }

    companion object {
        const val INPUT_FILE_NAME = "puzzle_5_input.txt"
        const val INPUT_TEST_FILE_NAME = "testdata/puzzle_5_input.txt"
    }
}

private class PageOrder(
    initialData: List<String>
) {
    private val pageOrdering = mutableMapOf<Int, MutableList<Int>>()

    init {
        initialData
            .asSequence()
            .map(::splitPageOrderLine)
            .forEach { (before, after) ->
                pageOrdering[before] = pageOrdering
                    .getOrDefault(before, mutableListOf())
                    .also { it.add(after) }
            }
    }

    operator fun get(key: Int): List<Int>? {
        return pageOrdering[key]
    }

    private fun splitPageOrderLine(line: String): Pair<Int, Int> {
        return with(line.split(PAGE_ORDER_SEPARATOR)) {
            first().toInt() to last().toInt()
        }
    }
}

private fun String.toSequenceOfInt(): Sequence<Int> {
    return splitToSequence(INSTRUCTION_SEPARATOR).map(String::toInt)
}

private fun List<Int>.getMidValue(): Int {
    return this[size / 2]
}

private const val DATA_SEPARATOR = ""
private const val PAGE_ORDER_SEPARATOR = "|"
private const val INSTRUCTION_SEPARATOR = ","