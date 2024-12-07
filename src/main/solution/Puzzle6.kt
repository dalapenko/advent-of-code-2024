package main.solution

import main.core.Puzzle
import main.solution.Puzzle6.Direction.*

class Puzzle6(inputData: List<String>) : Puzzle(inputData) {

    override fun firstPuzzleSolution(inputData: List<String>): Any {
        val currentMap = inputData.map(String::toCharArray).toTypedArray()

        val startRow = currentMap.indexOfFirst { it.contains(START_MARK) }
        val startCol = inputData[startRow].indexOf(START_MARK)

        val startPoint = Point(x = startCol, y = startRow)

        return simulateGuard(startPoint, currentMap).visitedPoint
    }

    override fun secondPuzzleSolution(inputData: List<String>): Any {
        val currentMap = inputData.map(String::toCharArray).toTypedArray()

        val startRow = currentMap.indexOfFirst { it.contains(START_MARK) }
        val startCol = inputData[startRow].indexOf(START_MARK)

        val startPoint = Point(x = startCol, y = startRow)

        val height = currentMap.size
        val width = currentMap.first().size

        val possibleMaps = mutableListOf<Array<CharArray>>()

        for (row in 0 ..< height) {
            for (col in 0 ..< width) {
                if (currentMap[row][col] == BARRIER_MARK) continue

                currentMap
                    .map(CharArray::copyOf)
                    .toTypedArray()
                    .also { map ->
                        map[row][col] = BARRIER_MARK
                    }.let(possibleMaps::add)
            }
        }

        return possibleMaps
            .count { map ->
                !simulateGuard(startPoint, map).hasExit
            }
    }

    private fun simulateGuard(startPoint: Point, map: Array<CharArray>): SimulationResult {
        val height = map.size
        val width = map.first().size

        val visitedPoints = mutableSetOf<Pair<Point, Direction>>()

        var cPoint = startPoint
        var cDirection = UP // everytime start with up

        while (cPoint.y in 1..< height && cPoint.x in 1 ..< width) {
            val nextStep = cPoint.getNext(cDirection)
            val isNextStepPossible = nextStep.y in 0 ..< height && nextStep.x in 0 ..< width

            if (isNextStepPossible && map[nextStep.y][nextStep.x] == BARRIER_MARK) {
                cDirection = cDirection.getNext()
                continue
            }

            if(!visitedPoints.add(cPoint to cDirection)) {
                return SimulationResult(
                    visitedPoint = visitedPoints.map(Pair<Point, Direction>::first).toSet().count(),
                    hasExit = false
                )
            }

            cPoint = nextStep
        }

        return SimulationResult(
            visitedPoint = visitedPoints.map(Pair<Point, Direction>::first).toSet().count(),
            hasExit = true
        )
    }

    private data class SimulationResult(val visitedPoint: Int, val hasExit: Boolean)
    private data class Point(val x: Int, val y: Int) {

        fun getNext(direction: Direction): Point {
            return when (direction) {
                UP -> Point(this.x, this.y - 1)
                RIGHT -> Point(this.x + 1, this.y)
                DOWN -> Point(this.x, this.y + 1)
                LEFT -> Point(this.x - 1, this.y)
            }
        }
    }
    private enum class Direction {
        UP, RIGHT, DOWN, LEFT;

        fun getNext(): Direction {
            return when (this) {
                UP -> RIGHT
                RIGHT -> DOWN
                DOWN -> LEFT
                LEFT -> UP
            }
        }
    }

    companion object {
        const val INPUT_FILE_NAME = "puzzle_6_input.txt"
        const val INPUT_TEST_FILE_NAME = "testdata/puzzle_6_input.txt"
    }
}

private const val START_MARK = '^'
private const val BARRIER_MARK = '#'