package com.rartworks.engine.events

import com.badlogic.gdx.math.Vector2
import com.rartworks.engine.utils.TempVector2

/**
 * An action that can be executed by tapping on the screen.
 */
abstract class TapAction() {
	private var isActive = false
	private var initialPosition = Vector2()
	private var pointer = 0

	/**
	 * Start event handler. Fires the [onStart] function.
	 */
	fun start(x: Int, y: Int, pointer: Int) {
		this.isActive = true
		this.initialPosition.set(x.toFloat(), y.toFloat())
		this.pointer = pointer

		this.onStart()
	}

	/**
	 * Stop event handler. Fires the [onStop] only if the [pointer] is the same as the current.
	 */
	fun stopIfAppropriate(x: Int, y: Int, pointer: Int) {
		if (this.isMyPointer(pointer)) {
			this.isActive = false

			val finalPosition = this.getVectorFrom(x, y)
			val direction = finalPosition.sub(this.initialPosition)
			this.onStop(direction)
		}
	}

	/**
	 * Drag event handler. Fires the [onDrag] only if the [pointer] is the same as the current.
	 */
	fun dragIfAppropriate(x: Int, y: Int, pointer: Int) {
		if (this.isMyPointer(pointer))
			this.onDrag(this.initialPosition, this.getVectorFrom(x, y))
	}

	/**
	 * On drag user handler.
	 */
	open fun onDrag(initialPosition: Vector2, currentPosition: Vector2) { }

	/**
	 * On start user handler.
	 */
	abstract fun onStart()

	/**
	 * On stop user handler.
	 */
	abstract fun onStop(direction: Vector2)

	private fun isMyPointer(pointer: Int) =
		this.isActive && this.pointer == pointer

	private fun getVectorFrom(x: Int, y: Int) = TempVector2(x.toFloat(), y.toFloat())
}
