package com.rartworks.engine.utils

/**
 * Like [forEach], but it doesn't allocate anything.
 */
inline fun <T: Any> List<T>.fastForEach(action: (T)  -> (Unit)) {
	for (i in 0..this.size) {
		val element = this[i]
		action(element)
	}
}

/**
 * Like [minBy], but it doesn't allocate anything.
 */
fun <T: Any> List<T>.fastMinBy(criteria: (T)  -> (Float)): T {
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
fun <T: Any> List<T>.fastMaxBy(criteria: (T)  -> (Float)): T {
	var max = this.first() ; var maxValue = criteria(max)
	this.fastForEach {
		val value = criteria(it)
		if (value > maxValue) {
			max = it ; maxValue = value
		}
	}

	return max
}
