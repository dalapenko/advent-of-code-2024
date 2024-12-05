import main.solution.Puzzle5

fun main() {
    val puzzle = Puzzle5(readFromRes(Puzzle5.INPUT_FILE_NAME))

//    puzzle.printFirstPuzzleAnswer()
    puzzle.printSecondPuzzleAnswer()
}

fun readFromRes(fileName: String): List<String> {
    val data = {}.javaClass.getResource(fileName)
        ?: throw IllegalArgumentException("Resource not found")

    return data.readText().lines()
}