val input = readFirstLine("Day 6/input.txt").split("\\s+".toRegex()).toInts()
val testInput = listOf(0, 2, 7, 0)

object Day6P1 {
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

object Day6P2 {
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

fun solveP1(input: List<Int>): Int {
	val layouts = mutableSetOf<List<Int>>()
	
	val layout = input.toMutableList()
	var step = 0
	while (layout !in layouts) {
		layouts += layout.toList()
		
		var (i, count) = layout.withIndex().maxBy { it.value }!!
		layout[i] = 0
		while (count > 0) {
			i++
			if (i >= layout.size) i = 0
			
			layout[i] += 1
			count--
		}
		
		step++
	}
	
	return step
}

fun solveP2(input: List<Int>): Int {
	val layouts = mutableSetOf<List<Int>>()
	
	val layout = input.toMutableList()
	var step = 0
	var repeatingLayout: List<Int>? = null
	
	while (true) {
		var (i, count) = layout.withIndex().maxBy { it.value }!!
		layout[i] = 0
		while (count > 0) {
			i++
			if (i >= layout.size) i = 0
			
			layout[i] += 1
			count--
		}
		
		step++
		
		if (repeatingLayout == null && layout in layouts) {
			repeatingLayout = layout.toList()
			step = 0
		} else if (repeatingLayout != null && layout == repeatingLayout) {
			return step
		}
		layouts += layout.toList()
	}
}