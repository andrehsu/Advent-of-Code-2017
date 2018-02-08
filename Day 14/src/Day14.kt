import kotlin.system.measureTimeMillis

val input = readFirstLine("Day 14/input.txt")

object Day14P1 {
	@JvmStatic
	fun main(args: Array<String>) {
		println(solveP1(input))
	}
	
	object Test {
		@JvmStatic
		fun main(args: Array<String>) {
			println(solveP1("flqrgnkx"))
		}
	}
}

object Day14P2 {
	@JvmStatic
	fun main(args: Array<String>) {
			println(solveP2(input))
	}
	
	object Test {
		@JvmStatic
		fun main(args: Array<String>) {
			println(solveP2("flqrgnkx"))
		}
	}
}

fun solveP1(input: String): Int {
	return (0 until 128)
			.map { "$input-$it" }
			.map { knotHash(it, 2) }
			.sumBy {
				it.count { it == '1' }
			}
}

fun solveP2(input: String): Int {
	var regions = 0
	val table = (0 until 128)
			.map { knotHash("$input-$it", 2).map { it == '1' }.toMutableList() }
	
	fun markOffRegion(i: Int, j: Int) {
		table[i][j] = false
		for ((_i, _j) in listOf(i to j - 1, i to j + 1, i - 1 to j, i + 1 to j)) {
			if (_i in 0..127 && _j in 0..127 && table[_i][_j]) {
				markOffRegion(_i, _j)
			}
		}
	}
	
	for (i in 0 until 128) {
		for (j in 0 until 128) {
			if (table[i][j]) {
				markOffRegion(i, j)
				regions++
			}
		}
	}
	
	return regions
}
