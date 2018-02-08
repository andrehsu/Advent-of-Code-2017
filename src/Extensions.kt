import kotlin.math.absoluteValue

fun Char.toNumericInt() = Character.getNumericValue(this)

operator fun <T> List<T>.component6(): T = get(5)
operator fun <T> List<T>.component7(): T = get(6)
operator fun <T> List<T>.component8(): T = get(7)
operator fun <T> List<T>.component9(): T = get(8)

operator fun String.invoke(index: Int): Char {
	return if (index >= 0) {
		this[index]
	} else {
		this[length + index]
	}
}

operator fun String.invoke(beginIndex: Int = 0, endIndex: Int = length): String {
	val i = if (beginIndex >= 0) {
		beginIndex
	} else {
		length + beginIndex
	}
	
	val j = if (endIndex >= 0) {
		endIndex
	} else {
		length + endIndex
	}
	
	return this.substring(i, j)
}

private val symbolTable = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ+_"


fun Int.toRadix(radix: Int, padLength: Int = -1): String {
	require(radix in 0..64)
	
	val sb = StringBuilder()
	if (this == 0) {
		sb.append('0')
	} else {
		var rem = absoluteValue
		while (rem > 0) {
			sb.append(symbolTable[rem % radix])
			rem /= radix
		}
		
		sb.reverse()
	}
	
	return if (padLength >= 0)
		sb.toString().padStart(padLength, '0')
	else
		sb.toString()
}

fun Long.toRadix(radix: Int, padLength: Int = -1): String {
	require(radix in 0..64)
	
	val sb = StringBuilder()
	if (this == 0L) {
		sb.append('0')
	} else {
		var rem = absoluteValue
		while (rem > 0) {
			sb.append(symbolTable[(rem % radix).toInt()])
			rem /= radix
		}
		
		sb.reverse()
	}
	
	return if (padLength >= 0)
		sb.toString().padStart(padLength, '0')
	else
		sb.toString()
}

fun <T> MutableList<T>.swap(i: Int, j: Int) {
	require(i in 0 until size && j in 0 until size)
	val temp = this[i]
	this[i] = this[j]
	this[j] = temp
}