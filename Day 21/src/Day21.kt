typealias Pattern = List<List<Boolean>>
typealias MutablePattern = MutableList<MutableList<Boolean>>


val input = readAllLines("Day 21/input.txt")
val testInput = readAllLines("Day 21/test_input.txt")

object Day21P1 {
	@JvmStatic
	fun main(args: Array<String>) {
		println(solveP1(Rules.parse(input), 5))
	}
	
	object Test {
		@JvmStatic
		fun main(args: Array<String>) {
			println(solveP1(Rules.parse(testInput), 2))
		}
	}
}

object Day21P2 {
	@JvmStatic
	fun main(args: Array<String>) {
		println(solveP1(Rules.parse(input), 18))
	}
}

fun solveP1(rules: Rules, iterations: Int): Int {
	var pattern = listOf(listOf(false, true, false), listOf(false, false, true), listOf(true, true, true))
	for (i in 1..iterations) {
		val blockSize = if (pattern.size % 2 == 0) 2 else 3
		val newBlockSize = if (blockSize == 2) 3 else 4
		val dimension = pattern.size / blockSize
		
		val newBlocks = MutableList(newBlockSize * dimension) { MutableList(newBlockSize * dimension) { false } }
		
		for (r in 0 until dimension) {
			for (c in 0 until dimension) {
				val capture: MutablePattern = mutableListOf()
				for (_r in r * blockSize until r * blockSize + blockSize) {
					val row = mutableListOf<Boolean>()
					for (_c in c * blockSize until c * blockSize + blockSize) {
						row += pattern[_r][_c]
					}
					capture += row
				}
				
				val newBlock = rules[capture]
				
				for (_r in 0 until newBlock.size) for (_c in 0 until newBlock.size) {
					newBlocks[r * newBlockSize + _r][c * newBlockSize + _c] = newBlock[_r][_c]
				}
			}
		}
		
		pattern = newBlocks
	}
	
	return pattern.sumBy { it.count { it } }
}


class Rules(private val rules: Map<Pattern, Pattern>) {
	operator fun get(key: Pattern): Pattern = rules[key]!!
	
	companion object {
		fun parse(input: List<String>): Rules {
			val rules = mutableMapOf<Pattern, Pattern>()
			
			for (line in input) {
				val (first, second) = line.split(" => ")
				val value = stringToPattern(second)
				
				val orig = stringToPattern(first)
				val fl_orig = flip(orig)
				val rot90 = flip(transpose(orig))
				val fl_rot90 = flip(transpose(fl_orig))
				val rot180 = flip(transpose(rot90))
				val fl_rot180 = flip(transpose(fl_rot90))
				val rot270 = flip(transpose(rot180))
				val fl_rot270 = flip(transpose(fl_rot180))
				
				rules[orig] = value
				rules[fl_orig] = value
				rules[rot90] = value
				rules[fl_rot90] = value
				rules[rot180] = value
				rules[fl_rot180] = value
				rules[rot270] = value
				rules[fl_rot270] = value
			}
			
			return Rules(rules)
		}
		
		private fun stringToPattern(str: String): Pattern {
			return str.split('/').map { it.map { it == '#' } }
		}
		
		private fun transpose(pattern: Pattern): Pattern {
			val out = MutableList(pattern.size) { MutableList(pattern.size) { true } }
			for (r in pattern.indices) for (c in pattern.indices) {
				out[c][r] = pattern[r][c]
			}
			return out
		}
		
		private fun flip(pattern: Pattern): Pattern {
			val out = MutableList(pattern.size) { MutableList(pattern.size) { true } }
			for (r in pattern.indices) for (c in pattern.indices) {
				out[r][c] = pattern[r][pattern.size - 1 - c]
			}
			return out
		}
	}
}

fun printPattern(pattern: Pattern) {
	for (r in pattern.indices) {
		for (c in pattern[r].indices) {
			if (pattern[r][c])
				print('#')
			else
				print('.')
		}
		println()
	}
}