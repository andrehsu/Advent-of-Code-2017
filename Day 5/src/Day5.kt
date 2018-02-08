val input = readAllLines("Day 5/input.txt").map { it.toInt() }

object Day5P1 {
	@JvmStatic
	fun main(args: Array<String>) {
		println(solveP1(input))
	}
}

object Day5P2 {
	@JvmStatic
	fun main(args: Array<String>) {
		println(solveP2(input))
	}
}

fun solveP1(input: List<Int>): Int {
	val workingSet = input.toMutableList()
	
	var steps = 0
	var i = 0
	
	while (i in 0 until workingSet.size) {
		val instruction = workingSet[i]++
		i += instruction
		steps++
	}
	
	return steps
}

fun solveP2(input: List<Int>): Int {
	val workingSet = input.toMutableList()
	
	var steps = 0
	var i = 0
	
	while (i in 0 until workingSet.size) {
		val instruction = workingSet[i]
		if (instruction >= 3) workingSet[i]--
		else workingSet[i]++
		i += instruction
		steps++
	}
	
	return steps
}
