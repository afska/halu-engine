package com.rartworks.engine.tween

import aurelienribon.tweenengine.Tween
import com.rartworks.engine.rendering.Drawable

/**
 * A [QuadTween] for [Drawable]s.
 */
class DrawableQuadTween(drawable: Drawable) : QuadTween<Drawable>(drawable) {
	init {
		Tween.registerAccessor(drawable.javaClass, DrawableAccessor())
	}

	fun start(newValue: Float, duration: Float, configure: (Tween) -> (Tween) = { it }, parameter: DrawableTweenParameter = DrawableTweenParameter.ALPHA, onFinish: () -> (Unit) = {}) {
		super.start(newValue, duration, configure, parameter.id, onFinish)
	}
}
