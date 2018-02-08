import Direction.*

val input = readAllLines("Day 19/input.txt")
val testInput = readAllLines("Day 19/test_input.txt")

object Day19P1 {
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

object Day19P2 {
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
	val routingTable = input.map { it.toList() }
	
	val encounterOrder = mutableSetOf<Char>()
	
	var r = 0
	var c = routingTable[0].indexOf('|')
	var direction = Down
	
	while (r in 0 until routingTable.size && c in 0 until routingTable[r].size) {
		val tile = routingTable[r][c]
		if (tile == '+') {
			when {
				isValid(routingTable, r, c, direction) -> Unit
				direction == Left || direction == Right -> when {
					isValid(routingTable, r, c, Up) -> direction = Up
					isValid(routingTable, r, c, Down) -> direction = Down
				}
				direction == Up || direction == Down -> when {
					isValid(routingTable, r, c, Left) -> direction = Left
					isValid(routingTable, r, c, Right) -> direction = Right
				}
			}
		} else if (tile.isLetter()) {
			encounterOrder.add(tile)
		}
		
		when (direction) {
			Left -> c--
			Right -> c++
			Up -> r--
			Down -> r++
		}
	}
	
	return encounterOrder.joinToString(separator = "")
}

fun solveP2(input: List<String>): Int {
	val routingTable = input.map { it.toList() }
	
	val encounterOrder = mutableMapOf<Char, Int>()
	
	var r = 0
	var c = routingTable[0].indexOf('|')
	var steps = 0
	var direction = Down
	
	while (r in 0 until routingTable.size && c in 0 until routingTable[r].size) {
		steps++
		val tile = routingTable[r][c]
		if (tile == '+') {
			when {
				isValid(routingTable, r, c, direction) -> Unit
				direction == Left || direction == Right -> when {
					isValid(routingTable, r, c, Up) -> direction = Up
					isValid(routingTable, r, c, Down) -> direction = Down
				}
				direction == Up || direction == Down -> when {
					isValid(routingTable, r, c, Left) -> direction = Left
					isValid(routingTable, r, c, Right) -> direction = Right
				}
			}
		} else if (tile.isLetter()) {
			encounterOrder.put(tile, steps)
		}
		
		when (direction) {
			Left -> c--
			Right -> c++
			Up -> r--
			Down -> r++
		}
	}
	
	return encounterOrder.toList().last().second
}


private enum class Direction {
	Left, Right, Up, Down
}

private fun isValid(routingTable: List<List<Char>>, r: Int, c: Int, direction: Direction): Boolean {
	val tile = when (direction) {
		Left -> routingTable.getOrNull(r)?.getOrNull(c - 1)
		Right -> routingTable.getOrNull(r)?.getOrNull(c + 1)
		Up -> routingTable.getOrNull(r - 1)?.getOrNull(c)
		Down -> routingTable.getOrNull(r + 1)?.getOrNull(c)
	} ?: ' '
	return ((direction == Left || direction == Right) && tile == '-') ||
			((direction == Up || direction == Down) && tile == '|') ||
			tile.isLetter()
}