package com.rartworks.engine.ui

import com.rartworks.engine.rendering.Drawable
import com.rartworks.engine.utils.decreaseUntil
import com.rartworks.engine.utils.increaseUntil

/**
 * A [Drawable] that hides itself in a fade out when [isVisible] returns false.
 */
interface AutoHidingThing : Drawable {
	val isVisible: () -> (Boolean)
	fun _maxAlpha() = 1f
	fun _hidingSpeed() = 1f

	override fun update(delta: Float) {
		this.autoHide(this._maxAlpha(), delta)
	}

	/**
	 * Depending on [isVisible], it slightly increases or decreases the [alpha].
	 */
	fun autoHide(maxAlpha: Float, delta: Float) {
		val speed = this._hidingSpeed() * delta / (1 / maxAlpha)

		this.alpha =
			if (this.isVisible())
				this.alpha.increaseUntil(speed, maxAlpha)
			else
				this.alpha.decreaseUntil(speed, 0f)
	}
}
