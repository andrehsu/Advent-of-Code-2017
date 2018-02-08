import java.util.*

val input = 328
val testInput = 3

object Day17P1 {
	@JvmStatic
	fun main(args: Array<String>) {
		println(solveP1(input))
	}
	
	object Test {
		@JvmStatic
		fun main(args: Array<String>) {
			println(solveP1(testInput))
		}
	}
}

object Day17P2 {
	@JvmStatic
	fun main(args: Array<String>) {
		println(solveP2(input))
	}
	
	object Test {
		@JvmStatic
		fun main(args: Array<String>) {
			solveP2(1)
			solveP2(2)
			solveP2(3)
			solveP2(4)
			solveP2(5)
		}
	}
}

fun solveP1(input: Int): Int {
	val buffer = LinkedList<Int>().also { it += 0 }
	
	var i = 0
	for (j in 1..2017) {
		i = (i + input) % buffer.size + 1
		buffer.add(i, j)
	}
	
	return buffer[i + 1]
}

fun solveP2(steps: Int): Int {
	val buffer = LinkedList<Int>()
	buffer.add(0)
	var i = 0
	var lastAdded = -1
	var size = 1
	for (j in 1..50_000_000) {
		i = (i + steps) % size + 1
		if (i == 1) {
			lastAdded = j
		}
		size++
	}
	
	return lastAdded
}