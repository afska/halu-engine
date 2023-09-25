package com.rlabs.engine.tween

import aurelienribon.tweenengine.Tween

/**
 * A [QuadTween] for [Value]s.
 */
class ValueQuadTween(value: Value) : QuadTween<Value>(value) {
	init {
		Tween.registerAccessor(value.javaClass, ValueAccessor())
	}

	fun start(newValue: Float, duration: Float, configure: (Tween) -> (Tween) = { it }, onFinish: () -> (Unit) = {}) {
		super.start(newValue, duration, configure, 0, onFinish)
	}
}
