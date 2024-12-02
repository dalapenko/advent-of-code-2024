import main.solution.Puzzle2

fun main() {
    val puzzle = Puzzle2(readFromRes(Puzzle2.INPUT_FILE_NAME))
    puzzle.printSecondPuzzleAnswer()
}

fun readFromRes(fileName: String): List<String> {
    val data = {}.javaClass.getResource(fileName)
        ?: throw IllegalArgumentException("Resource not found")

    return data.readText().lines()
}