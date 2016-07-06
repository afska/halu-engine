package com.rartworks.engine.rendering

import com.badlogic.gdx.Screen as GdxScreen

/**
 * A wrapper of the *LibGDX*'s [Screen] that don't forces to override all methods.
 */
interface Screen : com.badlogic.gdx.Screen {
	override fun show() { }

	override fun pause() { }

	override fun resize(width: Int, height: Int) { }

	override fun hide() { }

	override fun render(delta: Float) { }

	override fun resume() { }

	override fun dispose() { }
}
