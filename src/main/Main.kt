import main.solution.Puzzle3

fun main() {
    val puzzle = Puzzle3(readFromRes(Puzzle3.INPUT_FILE_NAME))

    puzzle.printSecondPuzzleAnswer()
}

fun readFromRes(fileName: String): List<String> {
    val data = {}.javaClass.getResource(fileName)
        ?: throw IllegalArgumentException("Resource not found")

    return data.readText().lines()
}