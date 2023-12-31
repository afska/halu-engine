package com.rlabs.engine.ui

import com.rlabs.engine.AssetsFactory
import com.rlabs.engine.drawables.MovieClip

/**
 * A button that can be pressed.
 */
abstract class Button(regionName: String, override val isVisible: () -> (Boolean)) : MovieClip(AssetsFactory.createMovieClipInfo(regionName)), AutoHidingThing {
	init {
		this.alpha = 0f
	}

	override fun update(delta: Float) {
		super<MovieClip>.update(delta)
		super<AutoHidingThing>.update(delta)
	}

	/**
	 * Fires the [onPressed] handler.
	 */
	open fun click() {
		this.onPressed()
	}

	abstract protected fun onPressed()
}
