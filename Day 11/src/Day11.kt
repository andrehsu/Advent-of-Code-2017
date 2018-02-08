import kotlin.math.absoluteValue

val input = readFirstLine("Day 11/input.txt")

object Day11P1 {
	@JvmStatic
	fun main(args: Array<String>) {
		println(solveP1(input))
	}
	
	object Test {
		@JvmStatic
		fun main(args: Array<String>) {
			println(solveP1("ne,ne,ne"))
			println(solveP1("ne,ne,sw,sw"))
			println(solveP1("ne,ne,s,s"))
			println(solveP1("se,sw,se,sw,sw"))
		}
	}
}

object Day11P2{
	@JvmStatic
	fun main(args: Array<String>) {
		println(solveP2(input))
	}
}

fun solveP1(input: String): Int {
	val steps = input.split(',')
	var x = 0
	var y = 0
	var z = 0
	
	for (step in steps) {
		when (step) {
			"n" -> {
				z--
				y++
			}
			"ne" -> {
				z--
				x++
			}
			"se" -> {
				y--
				x++
			}
			"s" -> {
				y--
				z++
			}
			"sw" -> {
				z++
				x--
			}
			"nw" -> {
				y++
				x--
			}
		}
	}
	
	
	return (x.absoluteValue + y.absoluteValue + z.absoluteValue) / 2
}

fun solveP2(input: String): Int {
	val steps = input.split(',')
	var x = 0
	var y = 0
	var z = 0
	var max = 0
	
	for (step in steps) {
		when (step) {
			"n" -> {
				z--
				y++
			}
			"ne" -> {
				z--
				x++
			}
			"se" -> {
				y--
				x++
			}
			"s" -> {
				y--
				z++
			}
			"sw" -> {
				z++
				x--
			}
			"nw" -> {
				y++
				x--
			}
		}
		if (x > max) {
			max = x
			
		}
		if (y > max) {
			max = y
		}
		if (z > max) {
			max = z
		}
	}
	
	
	return max
}