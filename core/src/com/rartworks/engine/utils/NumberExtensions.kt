package com.rartworks.engine.utils

import com.badlogic.gdx.math.MathUtils
import java.lang.String

/**
 * Increases the number until a max [limit].
 */
fun Float.increaseUntil(delta: Float, limit: Float): Float {
	val result = this + delta
	return if (result > limit) limit else result
}

/**
 * Decreases the number until a min [limit].
 */
fun Float.decreaseUntil(delta: Float, limit: Float): Float {
	val result = this - delta
	return if (result < limit) limit else result
}

/**
 * Generates a random number between this and [max].
 */
fun Float.toRandom(max: Float): Float {
	return this + MathUtils.random() * (max - this)
}

/**
 * Creates a [String] with the [Float] and [digits] trailing zeros.
 */
fun Int.addTrailingZeros(digits: Int) = java.lang.String.format("%0${digits}d", this)
