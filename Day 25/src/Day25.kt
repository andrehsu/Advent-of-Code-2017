import State.Actions

object Day25P1 {
	@JvmStatic
	fun main(args: Array<String>) {
		val a = State(
				Actions(1, 1),
				Actions(0, -1))
		val b = State(
				Actions(1, 1),
				Actions(0, 1))
		val c = State(
				Actions(1, -1),
				Actions(1, -1))
		val d = State(
				Actions(0, -1),
				Actions(1, 1))
		val e = State(
				Actions(1, -1),
				Actions(0, 1))
		val f = State(
				Actions(0, 1),
				Actions(0, 1))
		
		a.on0.next = b
		a.on1.next = d
		b.on0.next = c
		b.on1.next = f
		c.on0.next = c
		c.on1.next = a
		d.on0.next = e
		d.on1.next = a
		e.on0.next = a
		e.on1.next = b
		f.on0.next = c
		f.on1.next = e
		
		println(solveP1(a, 12317297))
	}
}


fun solveP1(stateA: State, checksumSteps: Int): Int {
	val tape = mutableMapOf<Int, Byte>().toDefaultMap { 0 }
	var i = 0
	var state = stateA
	
	repeat(checksumSteps) {
		val action = if (tape[i] == 0.toByte()) state.on0 else state.on1
		
		tape[i] = action.write
		i += action.move
		state = action.next
	}
	
	return tape.values.sum()
}


data class State(val on0: Actions, val on1: Actions) {
	data class Actions(val write: Byte, val move: Int) {
		lateinit var next: State
	}
}