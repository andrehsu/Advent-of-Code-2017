val input = readAllLines("Day 2/input.txt")

object Day2P1 {
	@JvmStatic
	fun main(args: Array<String>) {
		println(solveP1(input))
	}
}

object Day2P2 {
	@JvmStatic
	fun main(args: Array<String>) {
		println(solveP2(input))
	}
}

fun solveP1(input: List<String>): Int {
	return input.map {
		val sorted = it.split("\\s+".toRegex()).toInts().sorted()
		sorted.last() - sorted.first()
	}.sum()
}

fun solveP2(input: List<String>): Int {
	var sum = 0
	
	lines@ for (line in input) {
		val l = line.split("\\s+".toRegex()).toInts().sortedDescending()
		
		for (i in l.indices) {
			for (j in (i + 1) until l.size) {
				if (l[i] % l[j] == 0) {
					sum += l[i] / l[j]
					continue@lines
				}
			}
		}
	}
	
	return sum
}