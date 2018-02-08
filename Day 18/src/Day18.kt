import java.util.*

val input = readAllLines("Day 18/input.txt")
val testInput = readAllLines("Day 18/test_input.txt")

object Day18P1 {
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

object Day18P2 {
	@JvmStatic
	fun main(args: Array<String>) {
		println(solveP2(input))
	}
}

fun solveP1(code: List<String>): Long {
	val registers = mutableMapOf<String, Long>().withDefault { 0 }
	
	fun valueOf(identifier: String): Long {
		return identifier.toLongOrNull() ?: registers.getValue(identifier)
	}
	
	var lastSound = -1L
	
	var i = 0
	while (i in 0 until code.size) {
		val tokens = code[i].split(' ')
		when (tokens[0]) {
			"snd" -> lastSound = valueOf(tokens[1])
			"set" -> registers[tokens[1]] = valueOf(tokens[2])
			"add" -> registers[tokens[1]] = registers.getValue(tokens[1]) + valueOf(tokens[2])
			"mul" -> registers[tokens[1]] = registers.getValue(tokens[1]) * valueOf(tokens[2])
			"mod" -> registers[tokens[1]] = registers.getValue(tokens[1]) % valueOf(tokens[2])
			"rcv" -> if (lastSound != 0L) return lastSound
			"jgz" -> if (valueOf(tokens[1]) > 0) i = (i + valueOf(tokens[2]) - 1).toInt()
		}
		i++
	}
	
	return -1
}

fun solveP2(code: List<String>): Int {
	class Program(is1: Boolean) {
		private val registers = mutableMapOf<String, Long>().withDefault { 0L }.also { it["p"] = if (is1) 1L else 0L }
		
		private val queue = LinkedList<Long>()
		private var i = 0
		
		var sendCount = 0
			private set
		
		lateinit var other: Program
		
		fun receive(value: Long) {
			queue.addLast(value)
		}
		
		private fun valueOf(identifier: String): Long {
			return identifier.toLongOrNull() ?: registers.getValue(identifier)
		}
		
		fun waiting(): Boolean {
			return queue.isEmpty()
		}
		
		fun processUntilWait() {
			while (i in 0 until code.size) {
				val tokens = code[i].split(' ')
				when (tokens[0]) {
					"snd" -> {
						other.receive(valueOf(tokens[1]))
						sendCount++
					}
					"set" -> registers[tokens[1]] = valueOf(tokens[2])
					"add" -> registers[tokens[1]] = registers.getValue(tokens[1]) + valueOf(tokens[2])
					"mul" -> registers[tokens[1]] = registers.getValue(tokens[1]) * valueOf(tokens[2])
					"mod" -> registers[tokens[1]] = registers.getValue(tokens[1]) % valueOf(tokens[2])
					"rcv" -> {
						if (queue.isEmpty())
							return
						registers[tokens[1]] = queue.removeFirst()
					}
					"jgz" -> if (valueOf(tokens[1]) > 0) i = (i + valueOf(tokens[2]) - 1).toInt()
				}
				i++
			}
		}
	}
	
	val p0 = Program(false)
	val p1 = Program(true)
	p0.other = p1
	p1.other = p0
	
	do {
		p0.processUntilWait()
		p1.processUntilWait()
	}while (!(p0.waiting() && p1.waiting()))
	
	return p1.sendCount
}
