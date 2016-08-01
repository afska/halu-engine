package com.rartworks.engine.ui

import com.rartworks.engine.rendering.Drawable
import com.rartworks.engine.utils.decreaseUntil
import com.rartworks.engine.utils.increaseUntil

/**
 * A [Drawable] that hides itself in a fade out when [isVisible] returns false.
 */
interface AutoHidingWidget : Drawable {
	val isVisible: () -> (Boolean)

	override fun update(delta: Float) {
		this.autoHide(1f, delta)
	}

	fun autoHide(maxAlpha: Float, delta: Float) {
		val speed = 1f * delta / (1 / maxAlpha)

		this.alpha =
			if (this.isVisible())
				this.alpha.increaseUntil(speed, maxAlpha)
			else
				this.alpha.decreaseUntil(speed, 0f)
	}
}
