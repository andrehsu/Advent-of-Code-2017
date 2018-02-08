val input = readAllLines("Day 8/input.txt")
val testInput = readAllLines("Day 8/test_input.txt")

object Day8P1 {
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

object Day8P2 {
	@JvmStatic
	fun main(args: Array<String>) {
		println(solveP2(input))
	}
}

fun solveP1(input: List<String>): Int {
	val registers = mutableMapOf<String, Int>().withDefault { 0 }
	
	for (line in input) {
		val t = line.split("\\s+".toRegex())
		val condRegister = t[4]
		val condOp = t[5]
		val condValue = t[6].toInt()
		val condResult = testCond(registers, condRegister, condOp, condValue)
		
		if (condResult) {
			val register = t[0]
			val op = t[1]
			val change = t[2].toInt()
			val value = registers.getValue(register)
			
			if (op == "inc") registers[register] = value + change
			else registers[register] = value - change
		}
	}
	
	return registers.values.max()!!
}

fun solveP2(input: List<String>): Int {
	val registers = mutableMapOf<String, Int>().withDefault { 0 }
	
	var max = -1
	
	for (line in input) {
		val t = line.split("\\s+".toRegex())
		val (register, op, changeStr, _, condRegister, condOp, condValueStr) = t
		val condValue = condValueStr.toInt()
		val change = changeStr.toInt()
		
		val condResult = testCond(registers, condRegister, condOp, condValue)
		
		if (condResult) {
			val value = registers.getValue(register)
			
			val newValue = if (op == "inc") value + change
			else value - change
			registers[register] = newValue
			
			if (newValue > max) max = newValue
		}
	}
	
	return max
}

private fun testCond(registers: Map<String, Int>, condRegister: String, cond: String, condValue: Int): Boolean {
	val registerValue = registers.getValue(condRegister)
	return when (cond) {
		">=" -> registerValue >= condValue
		">" -> registerValue > condValue
		"==" -> registerValue == condValue
		"!=" -> registerValue != condValue
		"<" -> registerValue < condValue
		"<=" -> registerValue <= condValue
		else -> throw RuntimeException("Unrecognized boolean operator")
	}
}