package com.rartworks.engine.ui

/**
 * A button that can be [pressed] or not.
 */
abstract class TwoStatesButton(regionName: String, override val isVisible: () -> (Boolean), private val isPressed: () -> (Boolean)) : Button(regionName, isVisible) {
	private val frameIndex: Int get() = if (this.isPressed()) 1 else 0

	init {
		this.pause()
	}

	override fun update(delta: Float) {
		super.update(delta)

		if (this.isVisible()) this.updateFrame()
	}

	/**
	 * Changes the state of the button.
	 */
	override fun click() {
		val isNowPressed = !this.isPressed()

		if (isNowPressed)
			this.onPressed()
		else
			this.onUnpressed()
	}

	abstract fun onUnpressed()

	/**
	 * Updates the button sprite.
	 */
	private fun updateFrame() {
		this.goTo(this.frameIndex)
	}
}
