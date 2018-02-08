import java.nio.file.Files
import java.nio.file.Paths

fun readAllLines(fileUrl: String): List<String> {
	return Files.readAllLines(Paths.get(fileUrl))!!
}

fun readFirstLine(fileUrl: String): String {
	return readAllLines(fileUrl)[0]
}

fun readFirstInt(fileUrl: String): Int {
	return readFirstLine(fileUrl).toInt()
}

fun List<String>.toInts() = map { it.toInt() }