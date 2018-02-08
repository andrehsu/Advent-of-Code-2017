import java.util.*
import kotlin.collections.HashSet

val input = readAllLines("Day 12/input.txt")
val testInput = readAllLines("Day 12/test_input.txt")

object Day12P1 {
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

object Day12P2 {
	@JvmStatic
	fun main(args: Array<String>) {
		println(solveP2(input))
	}
	
	object Test {
		@JvmStatic
		fun main(args: Array<String>) {
			println(solveP2(testInput))
		}
	}
}

fun solveP1(input: List<String>): Int {
	val programs = parseToMap(input)
	
	var count = 0
	val traversed = mutableSetOf<String>()
	val queue = LinkedList<String>()
	queue.add("0")
	while (queue.isNotEmpty()) {
		val program = queue.pop()
		if (program !in traversed) {
			traversed.add(program)
			queue += programs[program]!!
			
			count++
		}
	}
	
	return count
}

fun solveP2(input: List<String>): Int {
	val programs = parseToMap(input).toMutableMap()
	
	var groups = 0
	
	val traversed = mutableSetOf<String>()
	while (programs.isNotEmpty()) {
		val queue = LinkedList<String>()
		queue.add(programs.keys.first())
		
		while (queue.isNotEmpty()) {
			val program = queue.pop()
			if (program !in traversed) {
				traversed.add(program)
				queue += programs[program]!!
				programs.remove(program)
			}
		}
		
		groups++
	}
	
	return groups
}

private fun parseToMap(input: List<String>): Map<String, Set<String>> {
	val programs = mutableMapOf<String, MutableSet<String>>()
	for (line in input) {
		val (program, right) = line.split(" <-> ")
		val links = right.split(", ")
		
		for (link in links) {
			programs.getOrPut(program, { HashSet() }).add(link)
		}
	}
	return programs
}