import kotlin.system.measureTimeMillis

val input = readAllLines("Day 7/input.txt")
val testInput = readAllLines("Day 7/test_input.txt")

object Day7P1 {
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

object Day7P2 {
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

fun solveP1(input: List<String>): String {
	val (parent, childrens) = input.filter { "->" in it }
			.map {
				val (first, second) = it.split(" \\(\\d+\\) -> ".toRegex())
				first to second.split(", ")
			}.unzip()
	val children = childrens.flatten()
	return parent.filter { it !in children }[0]
}

fun solveP2(input: List<String>): Int {
	data class Program(var weight: Int) {
		var children: List<Program> = listOf()
		var totalWeight = 0
	}
	
	val programs = input.map {
		val name = it.substring(0, it.indexOf(" ("))
		val weight = it.substring(it.indexOf('(') + 1, it.indexOf(')')).toInt()
		name to Program(weight)
	}.toMap()
	
	val bottomMost = programs[run {
		val candidates = programs.keys.toMutableList()
		input.filter { "->" in it }
				.forEach {
					val (parentName, childrenString) = it.split(" \\(\\d+\\) -> ".toRegex())
					programs[parentName]!!.children = childrenString.split(", ")
							.onEach { candidates.remove(it) }
							.map { programs[it]!! }
				}
		candidates[0]
	}]!!
	
	fun updateTotalWeight(parent: Program) {
		parent.children.forEach { updateTotalWeight(it) }
		parent.totalWeight = parent.weight + parent.children.sumBy { it.totalWeight }
	}
	updateTotalWeight(bottomMost)
	
	fun findUnbalancedWeight(parent: Program): Int {
		return if (parent.children.distinctBy { it.totalWeight }.size <= 1) { // if there are no children, or all of its children are balanced, it means that the program is the one that needs balancing
			-1
		} else {
			val sorted = parent.children.sortedBy { it.totalWeight }
			val (odd, correct) = if (sorted.first().totalWeight != sorted[sorted.size / 2].totalWeight)
				sorted.first() to sorted.last()
			else sorted.last() to sorted.first()
			
			val recurRet = findUnbalancedWeight(odd)
			// recursively locate the reason why the odd program is odd
			if (recurRet == -1) { // if the odd program is odd because of its weight, return the correct weight
				correct.totalWeight - odd.children.sumBy { it.totalWeight }
			} else { // if the solution was found somewhere down the tree in some other program, pass it up
				recurRet
			}
		}
	}
	return findUnbalancedWeight(bottomMost)
}
