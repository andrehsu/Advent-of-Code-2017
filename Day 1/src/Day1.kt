val input = readFirstLine("Day 1/input.txt")

object Day1P1 {
	@JvmStatic
	fun main(args: Array<String>) {
		println(solveP1(input))

	}
}


object Day1P2 {
	@JvmStatic
	fun main(args: Array<String>) {
		println(solveP2(input))
	}
}


fun solveP1(input: String): Int {
	return (input + input[0]).map { it.toNumericInt() }
			.zipWithNext()
			.filter { it.first == it.second }
			.map { it.first }
			.sum()
}

fun solveP2(input: String): Int {
	val mappedInput = input.map { it.toNumericInt() }
	fun getAcross(i: Int): Int {
		val j = (i + (mappedInput.size) / 2).let { if (it >= mappedInput.size) it - mappedInput.size else it }
		return mappedInput[j]
	}

	return input.map { it.toNumericInt() }
			.mapIndexed { index, i -> i to getAcross(index) }
			.filter { it.first == it.second }
			.map { it.first }
			.sum()
}
