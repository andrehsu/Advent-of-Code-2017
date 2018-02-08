import kotlin.math.absoluteValue

typealias Matrix = MutableMap<Pair<Int, Int>, Int>

val input = readFirstInt("Day 3/input.txt")

const val Left = 0
const val Right = 1
const val Up = 2
const val Down = 3

fun manhattanDistance(x: Int, y: Int) = x.absoluteValue + y.absoluteValue

object Day3P1 {
	@JvmStatic
	fun main(args: Array<String>) {
		println(solveP1(input))
	}
}

object Day3P2 {
	@JvmStatic
	fun main(args: Array<String>) {
		println(solveP2(input))
	}
}

fun solveP1(n: Int): Int {
	var direction = Right
	var x = 0
	var y = 0
	var i = 1
	var segment_len = 1
	var segment_i = 0
	while (i < n) {
		when (direction) {
			Left -> x--
			Right -> x++
			Up -> y++
			Down -> y--
		}
		i++
		segment_i++
		if (segment_i == segment_len) {
			segment_i = 0
			direction = when (direction) {
				Up -> {
					segment_len++
					Left
				}
				Left -> Down
				Down -> {
					segment_len++
					Right
				}
				Right -> {
					Up
				}
				else -> throw RuntimeException()
			}
		}
	}
	
	return manhattanDistance(x, y)
}

fun solveP2(n: Int): Int {
	val map = mutableMapOf<Pair<Int, Int>, Int>()
	map.put(0 to 0, 1)
	var direction = Up
	var x = 1
	var y = 0
	var value = 1
	var segment_len = 1
	var segment_i = 0
	
	while (value < n) {
		value = sumOfAdjacent(map, x, y)
		map[x to y] = value
		when (direction) {
			Left -> x--
			Right -> x++
			Up -> y++
			Down -> y--
		}
		segment_i++
		if (segment_i == segment_len) {
			segment_i = 0
			direction = when (direction) {
				Up -> {
					segment_len++
					Left
				}
				Left -> Down
				Down -> {
					segment_len++
					Right
				}
				Right -> {
					Up
				}
				else -> throw RuntimeException()
			}
		}
	}
	
	return value
}

fun sumOfAdjacent(map: Matrix, x: Int, y: Int): Int {
	var sum = 0
	for (x_ in (x - 1)..(x + 1)) {
		for (y_ in (y - 1)..(y + 1)) {
			if (!(x_ == x && y_ == y)) {
				sum += map[x_ to y_] ?: 0
			}
		}
	}
	return sum
}