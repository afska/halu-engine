package com.rartworks.engine.ui

/**
 * A button that can be [pressed] or not.
 */
abstract class TwoStatesButton(regionName: String, override val isVisible: () -> (Boolean), private var pressed: Boolean = true) : Button(regionName, isVisible) {
	private val frameIndex: Int get() = if (this.pressed) 1 else 0

	init {
		this.pause()
		this.updateFrame()
	}

	/**
	 * Changes the state of the button.
	 */
	override fun click() {
		this.pressed = !this.pressed

		if (this.pressed)
			this.onPressed()
		else
			this.onUnpressed()

		this.updateFrame()
	}


	abstract fun onUnpressed()

	/**
	 * Updates the button sprite.
	 */
	private fun updateFrame() {
		this.goTo(this.frameIndex)
	}
}
