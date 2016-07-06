package com.rartworks.engine.utils

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Matrix4

/**
 * Implementation of [render] for [SpriteBatch].
 */
inline fun SpriteBatch.render(color: Color, transform: (Matrix4) -> (Matrix4), draw: () -> (Unit)) {
	render({ this.color }, { this.color = it }, { this.transformMatrix }, { this.transformMatrix = it }, color, transform, draw)
}

/**
 * Implementation of [render] for [ShapeRenderer].
 */
inline fun ShapeRenderer.render(color: Color, transform: (Matrix4) -> (Matrix4), draw: () -> (Unit)) {
	render({ this.color }, { this.color = it }, { this.transformMatrix }, { this.transformMatrix = it }, color, transform, draw)
}

/**
 * Implementation of [changeColorAndThen] for [SpriteBatch].
 */
inline fun SpriteBatch.changeColorAndThen(color: Color, action: () -> (Unit)) {
	changeColorAndThen({ this.color }, { this.color = it }, color, action)
}


/**
 * Implementation of [changeColorAndThen] for [ShapeRenderer].
 */
inline fun ShapeRenderer.changeColorAndThen(color: Color, action: () -> (Unit)) {
	changeColorAndThen({ this.color }, { this.color = it }, color, action)
}

/**
 * Changes the [alpha], [transform]s the matrix, and then runs an action to [draw].
 */
inline fun render(getColor: () -> (Color), setColor: (Color) -> (Unit), getMatrix: () -> (Matrix4), setMatrix: (Matrix4) -> (Unit), color: Color, transform: (Matrix4) -> (Matrix4), draw: () -> (Unit)) {
	changeColorAndThen(getColor, setColor, color) {
		preserveMatrixWhen(getMatrix, setMatrix) {
			val matrix = TempMatrix4().set(getMatrix())
			setMatrix(transform(matrix))
			draw()
		}
	}
}

/**
 * Runs the [action] without altering the transformation matrix.
 */
inline fun preserveMatrixWhen(getMatrix: () -> (Matrix4), setMatrix: (Matrix4) -> (Unit), action: () -> (Unit)) {
	val oldTransformMatrix = Matrix4().set(getMatrix())
	action()
	setMatrix(oldTransformMatrix)
}

/**
 * Temporary changes the color of the batch, executes the [action], and restore it.
 */
inline fun changeColorAndThen(getColor: () -> (Color), setColor: (Color) -> (Unit), newColor: Color, action: () -> (Unit)) {
	if (newColor.a == 0f) return

	val color = getColor()
	val oldRed = color.r
	val oldGreen = color.g
	val oldBlue = color.b
	val oldAlpha = color.a

	// modify the color and draw
	color.mul(newColor)
	setColor(color)
	action()

	// restore the previous color
	color.r = oldRed
	color.g = oldGreen
	color.b = oldBlue
	color.a = oldAlpha
	setColor(color)
}
