val input = readAllLines("Day 23/input.txt")

object Day23P1 {
	@JvmStatic
	fun main(args: Array<String>) {
		println(solveP1(input))
	}
}

object Day23P2 {
	@JvmStatic
	fun main(args: Array<String>) {
		println(solveP2(input))
	}
}

fun solveP1(code: List<String>): Int {
	var count = 0
	
	val registers = mutableMapOf<String, Long>()
	for (c in 'a'..'h') {
		registers[c.toString()] = 0
	}
	
	var i = 0
	while (i in 0 until code.size) {
		val tokens = code[i].split(' ')
		when (tokens[0]) {
			"set" -> registers[tokens[1]] = registers.valueOf(tokens[2])
			"sub" -> registers[tokens[1]] = registers[tokens[1]]!! - registers.valueOf(tokens[2])
			"mul" -> {
				registers[tokens[1]] = registers[tokens[1]]!! * registers.valueOf(tokens[2])
				count++
			}
			"jnz" -> if (registers.valueOf(tokens[1]) != 0L) i = (i + registers.valueOf(tokens[2]) - 1).toInt()
		}
		i++
	}
	
	return count
}

fun solveP2(code: List<String>): Int {
	val n = code[0].split(' ')[2].toInt()
	val r = mutableMapOf(
			'b' to n,
			'c' to n
	).toDefaultMap { 0 }
	r['b'] = r['b'] * 100 + 100000
	r['c'] = r['b'] + 17000
	do {
		r['f'] = 1
		r['d'] = 2
		var d = r['d']
		while (d * d < r['b']) {
			if (r['b'] % d == 0) {
				r['f'] = 0
				break
			}
			d++
		}
		if (r['f'] == 0)
			r['h']++
		r['g'] = r['b'] - r['c']
		r['b'] += 17
	} while (r['g'] != 0)
	
	return r['h']
}

fun Map<String, Long>.valueOf(identifier: String): Long {
	return identifier.toLongOrNull() ?: this[identifier]!!
}