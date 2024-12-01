import main.solution.Puzzle1

fun main() {
    val puzzle = Puzzle1(readFromRes(Puzzle1.INPUT_FILE_NAME))
    puzzle.printSecondPuzzleAnswer()
}

fun readFromRes(fileName: String): List<String> {
    val data = {}.javaClass.getResource(fileName)
        ?: throw IllegalArgumentException("Resource not found")

    return data.readText().lines()
}