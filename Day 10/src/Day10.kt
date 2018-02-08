import kotlin.math.ceil
import kotlin.math.log

val input = readFirstLine("Day 10/input.txt")

object Day10P1 {
	@JvmStatic
	fun main(args: Array<String>) {
		println(solveP1(input))
	}
	
	object Test {
		@JvmStatic
		fun main(args: Array<String>) {
			println(solveP1("3,4,1,5", 5))
		}
	}
}

object Day10P2 {
	@JvmStatic
	fun main(args: Array<String>) {
		println(solveP2(input))
	}
	
	object Test {
		@JvmStatic
		fun main(args: Array<String>) {
			println(solveP2(""))
			println(solveP2("AoC 2017"))
			println(solveP2("1,2,3"))
			println(solveP2("1,2,4"))
		}
	}
}

fun solveP1(input: String, size: Int = 256): Int {
	val lengths = input.split(",").toInts()
	val nums = (0 until size).toMutableList()
	
	shuffleSequence(nums, lengths, 1)
	
	return nums[0] * nums[1]
}

fun solveP2(input: String): String {
	return knotHash(input)
}

fun knotHash(input: String, outputRadix: Int = 16): String {
	val padding = ceil(log(256.0, outputRadix.toDouble())).toInt()
	
	val ascii = input.map { it.toInt() } + listOf(17, 31, 73, 47, 23)
	
	val nums = (0 until 256).toMutableList()
	
	shuffleSequence(nums, ascii, 64)
	
	val sb = StringBuilder()
	
	for (block in nums.chunked(16)) {
		var combined = 0
		for (num in block) {
			combined = combined xor num
		}
		sb.append(combined.toRadix(outputRadix, padding))
	}
	
	return sb.toString()
}

private fun shuffleSequence(nums: MutableList<Int>, lengths: List<Int>, times: Int) {
	var i = 0
	var skipSize = 0
	repeat(times) {
		for (length in lengths) {
			for (j in i until (i + length / 2)) {
				val swapI = j % nums.size
				val swapJ = ((i + length) - (j - i) - 1) % nums.size
				nums.swap(swapI, swapJ)
			}
			val moveForward = length + skipSize++
			i = (i + moveForward) % nums.size
		}
	}
}