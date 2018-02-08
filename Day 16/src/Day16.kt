val input = readFirstLine("Day 16/input.txt").split(',')

object Day16P1 {
	@JvmStatic
	fun main(args: Array<String>) {
		println(solveP1(input))
	}
}

object Day16P2 {
	@JvmStatic
	fun main(args: Array<String>) {
		println(solveP2(input))
	}
}

fun solveP1(input: List<String>): String {
	val dancers = ('a'..'p').toMutableList()
	
	mutateDancers(input, dancers)
	
	return dancers.joinToString(separator = "")
}

fun solveP2(input: List<String>): String {
	val dancers = ('a'..'p').toMutableList()
	val endStates = mutableSetOf<List<Char>>()
	var firstRepeat = -1
	var repeatingI = -1
	for (i in 0 until 1_000_000_000) {
		mutateDancers(input, dancers)
		if (dancers in endStates) {
			if (firstRepeat == -1) {
				firstRepeat = i
			} else {
				repeatingI = i
				break
			}
		}
		endStates += dancers.toList()
	}
	
	for (i in 0 until (1_000_000_000 % repeatingI)) {
		mutateDancers(input, dancers)
	}
	return dancers.joinToString(separator = "")
}

private fun mutateDancers(input: List<String>, dancers: MutableList<Char>) {
	for (instruction in input) {
		when (instruction[0]) {
			's' -> {
				val num = instruction.substring(1).toInt()
				val removed = mutableListOf<Char>()
				for (i in 0 until num) {
					removed += dancers.removeAt(16 - num)
				}
				dancers.addAll(0, removed)
			}
			'x' -> {
				val (a, b) = instruction.substring(1).split('/').map { it.toInt() }
				dancers.swap(a, b)
			}
			'p' -> {
				val (a, b) = instruction.substring(1).split('/').map { it[0] }
				dancers.swap(dancers.indexOf(a), dancers.indexOf(b))
			}
		}
	}
}