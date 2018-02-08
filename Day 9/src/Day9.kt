import java.util.regex.Pattern

val input = readFirstLine("Day 9/input.txt")

object Day9P1 {
	@JvmStatic
	fun main(args: Array<String>) {
		println(solveP1(input))
	}
	
	object Test {
		@JvmStatic
		fun main(args: Array<String>) {
			println(solveP1("{}"))
			println(solveP1("{{{}}}"))
			println(solveP1("{{},{}}"))
			println(solveP1("{{{},{},{{}}}}"))
			println(solveP1("{<a>,<a>,<a>,<a>}"))
			println(solveP1("{{<ab>},{<ab>},{<ab>},{<ab>}}"))
			println(solveP1("{{<!!>},{<!!>},{<!!>},{<!!>}}"))
			println(solveP1("{{<a!>},{<a!>},{<a!>},{<ab>}}"))
		}
	}
}

object Day9P2 {
	@JvmStatic
	fun main(args: Array<String>) {
		println(solveP2(input))
	}
	
	object Test {
		@JvmStatic
		fun main(args: Array<String>) {
			println(solveP2("<>"))
			println(solveP2("<random characters>"))
			println(solveP2("<<<<>"))
			println(solveP2("<{!>}>"))
			println(solveP2("<!!>"))
			println(solveP2("<!!!>>"))
			println(solveP2("<{o\"i!a,<{i<a>"))
		}
	}
}

fun solveP1(input: String): Int {
	val sb = StringBuilder(removeCancelled(input))
	
	var i = 0
	var garbageStart = -1
	var garbage = false
	while (i < sb.length) {
		if (!garbage && sb[i] == '<') {
			garbage = true
			garbageStart = i
		} else if (garbage && sb[i] == '>') {
			sb.delete(garbageStart + 1, i)
			garbage = false
			i = garbageStart
		}
		i++
	}
	
	val cleanedInput = sb.toString()
	
	fun sumOfScore(str: String, score: Int): Int {
		var sum = 0
		sum += score
		var depth = 0
		var start = -1
		for (i in 1 until (str.length - 1)) {
			if (str[i] == '{') {
				if (depth == 0) {
					start = i
				}
				depth++
			} else if (str[i] == '}') {
				depth--
				if (depth == 0) {
					sum += sumOfScore(str(start, i + 1), score + 1)
				}
			}
		}
		return sum
	}
	
	return sumOfScore(cleanedInput, 1)
}

fun solveP2(input: String): Int {
	val str = removeCancelled(input)
	
	var garbageCount = 0
	
	var i = 0
	while (i < str.length) {
		if (str[i] == '<') {
			val end = str.indexOf('>', i)
			garbageCount += end - (i + 1)
			i = end
		}
		i++
	}
	
	return garbageCount
}

private fun removeCancelled(input: String) = input.replace("!.".toRegex(), "")