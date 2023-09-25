package com.rlabs.engine.tween

import aurelienribon.tweenengine.TweenAccessor

/**
 * A *TweenEngine*'s [TweenAccessor] for [Value] types.
 */
class ValueAccessor : TweenAccessor<Value> {
	override fun getValues(target: Value, tweenType: Int, returnValues: FloatArray): Int {
		returnValues[0] = target.value
		return 1
	}

	override fun setValues(target: Value, tweenType: Int, newValues: FloatArray) {
		target.value = newValues[0]
	}
}
