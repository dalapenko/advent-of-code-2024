import main.solution.Puzzle4

fun main() {
    val puzzle = Puzzle4(readFromRes(Puzzle4.INPUT_FILE_NAME))

//    puzzle.printFirstPuzzleAnswer()
    puzzle.printSecondPuzzleAnswer()
}

fun readFromRes(fileName: String): List<String> {
    val data = {}.javaClass.getResource(fileName)
        ?: throw IllegalArgumentException("Resource not found")

    return data.readText().lines()
}