import kotlin.system.measureTimeMillis

val aStart = 116
val bStart = 299

object Day15P1 {
	@JvmStatic
	fun main(args: Array<String>) {
		println(measureTimeMillis {
			println(solveP1(aStart, bStart))
		})
	}
	
	object Test {
		@JvmStatic
		fun main(args: Array<String>) {
			println(solveP1(65, 8921))
		}
	}
}

object Day15P2 {
	@JvmStatic
	fun main(args: Array<String>) {
		println(solveP2(aStart, bStart))
	}
	
	object Test {
		@JvmStatic
		fun main(args: Array<String>) {
			println(solveP2(65, 8921))
		}
	}
}

fun solveP1(aStart: Int, bStart: Int): Int {
	var a = aStart.toLong()
	var b = bStart.toLong()
	
	var count = 0
	
	for (i in 0 until 40_000_000) {
		a = a * 16807 % 2147483647
		b = b * 48271 % 2147483647
		if (a and 0xFFFF == b and 0xFFFF) count++
	}
	return count
}

fun solveP2(aStart: Int, bStart: Int): Int {
	var a = aStart.toLong()
	var b = bStart.toLong()
	var count = 0
	
	for (i in 0 until 5_000_000) {
		do a = a * 16807 % 2147483647 while (a % 4 != 0L)
		do b = b * 48271 % 2147483647 while (b % 8 != 0L)
		if (a and 0xFFFF == b and 0xFFFF) count++
	}
	return count
}
