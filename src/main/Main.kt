import main.solution.Puzzle6

fun main() {
    val puzzle = Puzzle6(readFromRes(Puzzle6.INPUT_FILE_NAME))

    puzzle.printFirstPuzzleAnswer()
    puzzle.printSecondPuzzleAnswer()
}

fun readFromRes(fileName: String): List<String> {
    val data = {}.javaClass.getResource(fileName)
        ?: throw IllegalArgumentException("Resource not found")

    return data.readText().lines()
}