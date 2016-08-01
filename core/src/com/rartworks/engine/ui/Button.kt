package com.rartworks.engine.ui

import com.rartworks.engine.AssetsFactory
import com.rartworks.engine.drawables.MovieClip

/**
 * A button that can be pressed.
 */
abstract class Button(regionName: String, override val isVisible: () -> (Boolean)) : MovieClip(AssetsFactory.createMovieClipInfo(regionName)), AutoHidingWidget {
	override fun update(delta: Float) {
		super<MovieClip>.update(delta)
		super<AutoHidingWidget>.update(delta)
	}

	/**
	 * Fires the [onPressed] handler.
	 */
	open fun click() {
		this.onPressed()
	}

	abstract protected fun onPressed()
}
