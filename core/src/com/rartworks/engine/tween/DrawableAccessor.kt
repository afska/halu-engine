package com.rartworks.engine.tween

import aurelienribon.tweenengine.TweenAccessor
import com.rartworks.engine.rendering.*

/**
 * A *TweenEngine*'s [TweenAccessor] for [Drawable] types.
 */
class DrawableAccessor : TweenAccessor<Drawable> {
	override fun getValues(target: Drawable, tweenType: Int, returnValues: FloatArray): Int {
		when (tweenType) {
			TweenParameter.X.id -> {
				returnValues[0] = target.position.x
				return 1
			}
			TweenParameter.Y.id -> {
				returnValues[0] = target.position.y
				return 1
			}
			TweenParameter.RED.id -> {
				returnValues[0] = target.color.r
				return 1
			}
			TweenParameter.GREEN.id -> {
				returnValues[0] = target.color.g
				return 1
			}
			TweenParameter.BLUE.id -> {
				returnValues[0] = target.color.b
				return 1
			}
			TweenParameter.ALPHA.id -> {
				returnValues[0] = target.alpha
				return 1
			}
			TweenParameter.SCALE.id -> {
				returnValues[0] = target.scale
				return 1
			}
			TweenParameter.ROTATION.id -> {
				returnValues[0] = target.rotation
				return 1
			}
			else -> return 0
		}
	}

	override fun setValues(target: Drawable, tweenType: Int, newValues: FloatArray) {
		when (tweenType) {
			TweenParameter.X.id -> target.position.x = newValues[0]
			TweenParameter.Y.id -> target.position.y = newValues[0]
			TweenParameter.RED.id -> target.color.r = newValues[0]
			TweenParameter.GREEN.id -> target.color.g = newValues[0]
			TweenParameter.BLUE.id -> target.color.b = newValues[0]
			TweenParameter.ALPHA.id -> target.alpha = newValues[0]
			TweenParameter.SCALE.id -> target.scale = newValues[0]
			TweenParameter.ROTATION.id -> target.rotation = newValues[0]
		}
	}
}
