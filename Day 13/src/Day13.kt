import kotlin.system.measureTimeMillis

val input = readAllLines("Day 13/input.txt")
val testInput = readAllLines("Day 13/test_input.txt")

object Day13P1 {
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

object Day13P2 {
	@JvmStatic
	fun main(args: Array<String>) {
		println(measureTimeMillis {
			println(solveP2(input))
		})
	}
	
	object Test {
		@JvmStatic
		fun main(args: Array<String>) {
			println(solveP2(testInput))
		}
	}
}

private fun parseToMap(input: List<String>): Map<Int, ScannerState> {
	val map = mutableMapOf<Int, ScannerState>()
	for (line in input) {
		val (k, v) = line.split(": ").map { it.toInt() }
		map[k] = ScannerState(v, 0, true)
	}
	return map
}

private data class ScannerState(val range: Int, var position: Int, var movingDown: Boolean) {
	fun advance() {
		if (position == range - 1) {
			movingDown = false
		} else if (position == 0) {
			movingDown = true
		}
		
		if (movingDown) position++
		else position--
	}
}

fun solveP1(input: List<String>): Int {
	val scanners = parseToMap(input)
	
	var depth = -1
	var severity = 0
	
	val end = scanners.keys.max()!!
	
	for (i in 0..end) {
		depth++
		val scanner = scanners[depth]
		if (scanner?.position == 0) {
			severity += depth * scanner.range
		}
		scanners.values.forEach { it.advance() }
	}
	
	return severity
}

fun solveP2(input: List<String>): Int {
	val scanners = parseToMap(input)
	
	var delay = 0
	val packets = mutableMapOf<Int, Int>()
	
	val end = scanners.keys.max()!!
	
	while (true) {
		packets[delay] = -1
		delay++
		
		packets.entries.forEach { it.setValue(it.value + 1) }
		
		packets.entries.removeAll { scanners[it.value]?.position == 0 }
		
		scanners.values.forEach { it.advance() }
		
		val endEntry = packets.entries.firstOrNull { it.value == end }
		if (endEntry != null) {
			return endEntry.key
		}
	}
}