package com.rartworks.engine.tween

import aurelienribon.tweenengine.*
import com.rartworks.engine.rendering.*

/**
 * A tween for [Drawable]s that uses the *easeInOutQuad* function.
 */
class QuadTween(private val drawable: Drawable) {
	private val tweenManager = TweenManager()

	init {
		Tween.registerAccessor(drawable.javaClass, DrawableAccessor())
	}

	fun update(delta: Float) = this.tweenManager.update(delta)

	/**
	 * Starts a tween that changes the parameter [parameter] to [newValue] in [duration] seconds.
	 * It is possible to [configure] the tween using custom *TweenEngine* options.
	 */
	fun start(newValue: Float, duration: Float, configure: (Tween) -> (Tween) = { it }, parameter: TweenParameter = TweenParameter.ALPHA, onFinish: () -> (Unit) = {}) {
		val callback = object : TweenCallback {
			override fun onEvent(type: Int, source: BaseTween<*>) {
				onFinish()
			}
		}

		val tween = Tween
			.to(this.drawable, parameter.id, duration)
			.target(newValue)
			.ease(TweenEquations.easeInOutQuad)

		configure(tween)
			.setCallback(callback)
			.setCallbackTriggers(TweenCallback.COMPLETE)
			.start(this.tweenManager)
	}

	/**
	 * Removes all the running tweens.
	 */
	fun clear() {
		this.tweenManager.killAll()
	}
}
