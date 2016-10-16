package com.rartworks.engine.tween

import aurelienribon.tweenengine.Tween
import aurelienribon.tweenengine.TweenCallback
import aurelienribon.tweenengine.TweenEquations
import aurelienribon.tweenengine.TweenManager

/**
 * A tween for [T]s that uses the *easeInOutQuad* function.
 */
open class QuadTween<T>(private val tweenable: T) {
	private val tweenManager = TweenManager()

	fun update(delta: Float) = this.tweenManager.update(delta)

	/**
	 * Starts a tween that changes the parameter [parameter] to [newValue] in [duration] seconds.
	 * It is possible to [configure] the tween using custom *TweenEngine* options.
	 */
	fun start(newValue: Float, duration: Float, configure: (Tween) -> (Tween) = { it }, parameter: Int, onFinish: () -> (Unit) = {}) {
		val callback = TweenCallback { type, source -> onFinish() }

		val tween = Tween
			.to(this.tweenable, parameter, duration)
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

	/**
	 * Returns if there are pending animations.
	 */
	val isActive: Boolean get() = this.tweenManager.runningTweensCount > 0
}
