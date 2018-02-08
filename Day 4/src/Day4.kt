val input = readAllLines("Day 4/input.txt")

object Day4P1 {
	@JvmStatic
	fun main(args: Array<String>) {
		println(solveP1(input))
	}
}

object Day4P2 {
	@JvmStatic
	fun main(args: Array<String>) {
		println(solveP2(input))
	}
}

fun solveP1(input: List<String>) = input.map { it.split(" ") }
		.filter { it.toSet().size == it.size }
		.count()

fun solveP2(input: List<String>) = input.map { it.split(" ").map { it.toList().sorted() } }
		.filter { it.toSet().size == it.size }
		.count()
