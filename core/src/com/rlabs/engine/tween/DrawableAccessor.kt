package com.rlabs.engine.tween

import aurelienribon.tweenengine.TweenAccessor
import com.rlabs.engine.rendering.Drawable

/**
 * A *TweenEngine*'s [TweenAccessor] for [Drawable] types.
 */
class DrawableAccessor : TweenAccessor<Drawable> {
	override fun getValues(target: Drawable, tweenType: Int, returnValues: FloatArray): Int {
		when (tweenType) {
			DrawableTweenParameter.X.id -> {
				returnValues[0] = target.position.x
				return 1
			}
			DrawableTweenParameter.Y.id -> {
				returnValues[0] = target.position.y
				return 1
			}
			DrawableTweenParameter.RED.id -> {
				returnValues[0] = target.color.r
				return 1
			}
			DrawableTweenParameter.GREEN.id -> {
				returnValues[0] = target.color.g
				return 1
			}
			DrawableTweenParameter.BLUE.id -> {
				returnValues[0] = target.color.b
				return 1
			}
			DrawableTweenParameter.ALPHA.id -> {
				returnValues[0] = target.alpha
				return 1
			}
			DrawableTweenParameter.SCALE.id -> {
				returnValues[0] = target.scale
				return 1
			}
			DrawableTweenParameter.ROTATION.id -> {
				returnValues[0] = target.rotation
				return 1
			}
			else -> return 0
		}
	}

	override fun setValues(target: Drawable, tweenType: Int, newValues: FloatArray) {
		when (tweenType) {
			DrawableTweenParameter.X.id -> target.position.x = newValues[0]
			DrawableTweenParameter.Y.id -> target.position.y = newValues[0]
			DrawableTweenParameter.RED.id -> target.color.r = newValues[0]
			DrawableTweenParameter.GREEN.id -> target.color.g = newValues[0]
			DrawableTweenParameter.BLUE.id -> target.color.b = newValues[0]
			DrawableTweenParameter.ALPHA.id -> target.alpha = newValues[0]
			DrawableTweenParameter.SCALE.id -> target.scale = newValues[0]
			DrawableTweenParameter.ROTATION.id -> target.rotation = newValues[0]
		}
	}
}
