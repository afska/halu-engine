package com.rlabs.engine.utils

/**
 * Like [forEach], but it doesn't allocate anything.
 */
inline fun <T: Any> List<T>.fastForEach(action: (T)  -> (Unit)) {
	for (i in 0..this.size - 1) {
		val element = this[i]
		action(element)
	}
}

/**
 * Like [forEach], but it doesn't allocate anything.
 */
inline fun <T: Any> Array<T>.fastForEach(action: (T)  -> (Unit)) {
	for (i in 0..this.size - 1) {
		val element = this[i]
		action(element)
	}
}

/**
 * Like [forEach], but it doesn't allocate anything.
 */
inline fun <T: Any> List<T>.fastForEachIndexed(action: (Int, T)  -> (Unit)) {
	for (i in 0..this.size - 1) {
		val element = this[i]
		action(i, element)
	}
}


/**
 * Like [minBy], but it doesn't allocate anything.
 */
inline fun <T: Any> List<T>.fastMinBy(criteria: (T)  -> (Float)): T {
	var min = this.first() ; var minValue = criteria(min)
	this.fastForEach {
		val value = criteria(it)

		if (value < minValue) {
			min = it ;	minValue = value
		}
	}

	return min
}

/**
 * Like [maxBy], but it doesn't allocate anything.
 */
inline fun <T: Any> List<T>.fastMaxBy(criteria: (T)  -> (Float)): T {
	var max = this.first() ; var maxValue = criteria(max)
	this.fastForEach {
		val value = criteria(it)
		if (value > maxValue) {
			max = it ; maxValue = value
		}
	}

	return max
}

/**
 * Returns a random element of the collection.
 */
fun <T: Any> List<T>.getRandomElement(): T {
	val index = 0.toRandom(this.count() - 1)
	return this[index]
}
