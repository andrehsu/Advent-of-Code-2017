import kotlin.math.absoluteValue

val input = parseToParticles(readAllLines("Day 20/input.txt"))

object Day20P1 {
	@JvmStatic
	fun main(args: Array<String>) {
		println(solveP1(input))
	}
}

object Day20P2 {
	@JvmStatic
	fun main(args: Array<String>) {
		println(solveP2(input))
	}
}

fun solveP1(particles: MutableList<Particle>): Int {
	for (i in 1..particles.size) {
		particles.forEach { it.nextTick() }
	}
	
	return particles.minBy { it.position.manhattanDistance }!!.id
}

fun solveP2(particles: MutableList<Particle>): Int {
	for (i in 1..particles.size) {
		particles.forEach { it.nextTick() }
		val collided = particles.groupBy { it.position }.filter { it.value.size > 1 }.flatMap { it.value }
		particles.removeAll(collided)
	}
	return particles.size
}

private fun parseToParticles(input: List<String>): MutableList<Particle> {
	val particles = mutableListOf<Particle>()
	for ((i, line) in input.withIndex()) {
		val (p, v, a) = line
				.replace("(\\w=<)|>".toRegex(), "")
				.split(", ")
				.map { it.split(',').map { it.toInt() } }
		val pV = Triplet.parse(p)
		val vV = Triplet.parse(v)
		val aV = Triplet.parse(a)
		particles += Particle(i, pV, vV, aV)
	}
	return particles
}

data class Triplet(var x: Int, var y: Int, var z: Int) {
	val manhattanDistance
		get() = x.absoluteValue + y.absoluteValue + z.absoluteValue
	
	companion object {
		fun parse(list: List<Int>): Triplet {
			return Triplet(list[0], list[1], list[2])
		}
	}
	
	override fun toString(): String {
		return "($x, $y, $z)"
	}
}

data class Particle(val id: Int, val position: Triplet, val velocity: Triplet, val acceleration: Triplet) {
	fun nextTick() {
		velocity.x += acceleration.x
		velocity.y += acceleration.y
		velocity.z += acceleration.z
		position.x += velocity.x
		position.y += velocity.y
		position.z += velocity.z
	}
	
	override fun toString(): String {
		return "Particle(id=$id, p=$position, v=$velocity, a=$acceleration)"
	}
}