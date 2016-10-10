package com.rartworks.engine.events

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputProcessor
import com.rartworks.engine.rendering.Dimensions

/**
 * A wrapper of an *libgdx*'s [InputProcessor], with a simplified interface.
 * The handlers can return `false` to keep the propagation of the event.
 */
open class InputHandler(private val dimensions: Dimensions) : InputProcessor {
	private val EMPTY_HANDLER: (Int, Int, Int) -> (Boolean) = { _1, _2, _3 -> false }

	var onTouchDownAction = EMPTY_HANDLER ; private set
	var onTouchDraggedAction = EMPTY_HANDLER ; private set
	var onTouchUpAction = EMPTY_HANDLER ; private set

	/**
	 * Registers this as the current input processor.
	 */
	open fun register(): InputHandler {
		Gdx.input.inputProcessor = this
		return this
	}

	/**
	 * Removes the current input processor.
	 */
	open fun unregister(): InputHandler {
		Gdx.input.inputProcessor = null
		return this
	}

	fun onTouchDown(action: (Int, Int, Int) -> (Any)): InputHandler {
		this.onTouchDownAction = handlerFor(action)
		return this
	}

	fun onTouchDragged(action: (Int, Int, Int) -> (Any)): InputHandler {
		this.onTouchDraggedAction = handlerFor(action)
		return this
	}

	fun onTouchUp(action: (Int, Int, Int) -> (Any)): InputHandler {
		this.onTouchUpAction = handlerFor(action)
		return this
	}

	override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
		return this.onTouchDownAction(adjustX(screenX), adjustY(screenY), pointer)
	}

	override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean {
		return this.onTouchDraggedAction(adjustX(screenX), adjustY(screenY), pointer)
	}

	override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
		return this.onTouchUpAction(adjustX(screenX), adjustY(screenY), pointer)
	}

	/**
	 * Clears all handlers.
	 */
	fun clear() {
		this.onTouchDownAction = EMPTY_HANDLER
		this.onTouchDraggedAction = EMPTY_HANDLER
		this.onTouchUpAction = EMPTY_HANDLER
	}

	/**
	 * Returns a wrapper of the handler that checks its return value.
	 */
	private inline fun handlerFor(crossinline action: (Int, Int, Int) -> (Any)): (Int, Int, Int) -> (Boolean) {
		return { x, y, pointer -> shouldStopPropagation(action(x, y, pointer)) }
	}

	/**
	 * Returns if the [actionReturnValue] isn't `false` and it should stop propagation.
	 */
	private fun shouldStopPropagation(actionReturnValue: Any): Boolean {
		return actionReturnValue != false
	}

	/**
	 * Converts the [screenX] to virtual screen coordinates.
	 */
	private fun adjustX(screenX: Int): Int {
		val screenWidth = Gdx.graphics.width
		return screenX * dimensions.width.toInt() / screenWidth
	}

	/**
	 * Converts the [screenY] to virtual screen coordinates.
	 */
	private fun adjustY(screenY: Int): Int {
		val screenHeight = Gdx.graphics.height
		val fixedScreenY = Math.abs(screenY - screenHeight)
		return fixedScreenY * dimensions.height.toInt() / screenHeight
	}

	// ---

	override fun mouseMoved(screenX: Int, screenY: Int) = false
	override fun scrolled(amount: Int) = false
	override fun keyDown(keyCode: Int) = false
	override fun keyUp(keyCode: Int) = false
	override fun keyTyped(character: Char) = false
}
