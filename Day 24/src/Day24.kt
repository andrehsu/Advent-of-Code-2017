typealias Part = Pair<Int, Int>

val input = readAllLines("Day 24/input.txt")
val testInput = readAllLines("Day 24/test_input.txt")

object Day24P1 {
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

object Day24P2 {
	@JvmStatic
	fun main(args: Array<String>) {
		println(solveP2(input))
	}
}

fun solveP1(input: List<String>): Int {
	val parts = mutableListOf<Part>()
	
	for (line in input) {
		val (a, b) = line.split('/')
		parts += a.toInt() to b.toInt()
	}
	
	fun build(out: MutableList<List<Part>>, building: List<Part>, rem: List<Part>) {
		val connector = building.last().second
		
		val possibleParts = rem.filter { it.first == connector }
		val needReverse = rem.filter { it.second == connector }
		
		if (possibleParts.isEmpty() && needReverse.isEmpty()) {
			out += building
		} else {
			for (part in possibleParts) {
				build(out, building + part, rem - part)
			}
			
			for (part in needReverse) {
				build(out, building + (part.second to part.first), rem - part)
			}
		}
	}
	
	val possible = mutableListOf<List<Part>>()
	
	for (part in parts) {
		if (part.first == 0) {
			build(possible, mutableListOf(part), parts - part)
		}
		
		if (part.second == 0) {
			build(possible, mutableListOf(part.second to part.first), parts - part)
		}
	}
	
	return possible.map { it.sumBy { it.first + it.second } }.max()!!
}

fun solveP2(input: List<String>): Int {
	val parts = mutableListOf<Part>()
	
	for (line in input) {
		val (a, b) = line.split('/')
		parts += a.toInt() to b.toInt()
	}
	
	fun build(out: MutableList<List<Part>>, building: List<Part>, connector: Int, rem: List<Part>) {
		val possibleParts = rem.filter { it.first == connector }
		val needReverse = rem.filter { it.second == connector }
		
		if (possibleParts.isEmpty() && needReverse.isEmpty()) {
			out += building
		} else {
			for (part in possibleParts) {
				build(out, building + part, part.second, rem - part)
			}
			
			for (part in needReverse) {
				build(out, building + part, part.first, rem - part)
			}
		}
	}
	
	val possible = mutableListOf<List<Part>>()
	
	for (part in parts) {
		if (part.first == 0) {
			build(possible, mutableListOf(part), part.second, parts - part)
		}
		
		if (part.second == 0) {
			build(possible, mutableListOf(part), part.first, parts - part)
		}
	}
	
	val max = possible.map { it.size }.max()!!
	
	return possible.filter { it.size == max }.map { it.sumBy { it.first + it.second } }.max()!!
}