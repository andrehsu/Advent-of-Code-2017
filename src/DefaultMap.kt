class DefaultMap<K, V>(private val backingMap: MutableMap<K, V>, private val default: () -> V) : MutableMap<K, V> by backingMap {
	override fun get(key: K): V {
		return backingMap.getOrElse(key, default)
	}
	
	operator fun set(key: K, value: V) {
		backingMap[key] = value
	}
}

fun <K, V> MutableMap<K, V>.toDefaultMap(default: () -> V): DefaultMap<K, V> {
	return DefaultMap(this, default)
}
