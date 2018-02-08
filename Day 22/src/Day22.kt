typealias Grid = MutableMap<Int, MutableMap<Int, Int>>

operator fun Grid.get(r: Int, c: Int): Int = this.getOrPut(r, { mutableMapOf() }).getOrDefault(c, 0)

operator fun Grid.set(r: Int, c: Int, value: Int) = this.getOrPut(r, { mutableMapOf() }).set(c, value)

fun Grid.toggle(r: Int, c: Int): Boolean {
	if (this[r, c] == 0)
		this[r, c] = 2
	else
		this[r, c] = 0
	
	return this[r, c] == 2
}

fun Grid.advance(r: Int, c: Int): Boolean {
	this[r, c] = (this[r, c] + 1) % 4
	return this[r, c] == 2
}


val input = readAllLines("Day 22/input.txt")
val testInput = readAllLines("Day 22/test_input.txt")

object Day22P1 {
	@JvmStatic
	fun main(args: Array<String>) {
		println(solveP1(parseGrid(input), 10_000))
	}
	
	object Test {
		@JvmStatic
		fun main(args: Array<String>) {
			println(solveP1(parseGrid(testInput), 70))
			println(solveP1(parseGrid(testInput), 10_000))
		}
	}
}

object Day22P2 {
	@JvmStatic
	fun main(args: Array<String>) {
		println(solveP2(parseGrid(input), 10_000_000))
	}
	
	object Test {
		@JvmStatic
		fun main(args: Array<String>) {
			println(solveP2(parseGrid(testInput), 100))
			println(solveP2(parseGrid(testInput), 10_000_000))
		}
	}
}

fun solveP1(grid: Grid, bursts: Int): Int {
	var infectiousBursts = 0
	
	var r = 0
	var c = 0
	var dir = 0
	
	for (i in 0 until bursts) {
		if (grid[r, c] != 0) {
			dir++
			if (dir == 4) dir = 0
		} else {
			dir--
			if (dir == -1) dir = 3
		}
		
		if (grid.toggle(r, c)) {
			infectiousBursts++
		}
		
		when (dir) {
			0 -> r++
			1 -> c++
			2 -> r--
			3 -> c--
		}
	}
	
	return infectiousBursts
}

fun solveP2(grid: Grid, bursts: Int): Int {
	var infectiousBursts = 0
	
	var r = 0
	var c = 0
	var dir = 0
	
	for (i in 0 until bursts) {
		when (grid[r, c]) {
			0 -> {
				dir--
				if (dir == -1) dir = 3
			}
			2 -> {
				dir++
				if (dir == 4) dir = 0
			}
			3 -> dir = (dir + 2) % 4
		}
		
		if (grid.advance(r, c)) {
			infectiousBursts++
		}
		
		when (dir) {
			0 -> r++
			1 -> c++
			2 -> r--
			3 -> c--
		}
	}
	
	return infectiousBursts
}

fun parseGrid(input: List<String>): Grid {
	val grid: Grid = mutableMapOf()
	
	val iter = input.flatMap { it.map { if (it == '#') 2 else 0 } }.iterator()
	
	val range = input.size / 2
	
	for (r in +range downTo -range) {
		for (c in -range..+range) {
			grid[r, c] = iter.next()
		}
	}
	
	return grid
}

/*fun <T> printGrid(grid: Grid<T>) {
	val maxR = grid.keys.max()!!
	val minR = grid.keys.min()!!
	val columns = grid.flatMap { it.value.keys }
	val maxC = columns.max()!!
	val minC = columns.min()!!
	for (r in maxR downTo minR) {
		for (c in minC..maxC) {
			print(if (grid[r, c]) '#' else '.')
		}
		println()
	}
}*/

