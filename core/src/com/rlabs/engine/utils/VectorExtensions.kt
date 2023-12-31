package com.rlabs.engine.utils

import com.badlogic.gdx.math.Matrix4
import com.badlogic.gdx.math.Vector2

private val tmpVector2 = Vector2()
private val tmpMatrix4 = Matrix4()
fun TempVector2(x: Float = 0f, y: Float = 0f) = tmpVector2.set(x, y)
fun TempMatrix4() = tmpMatrix4.setToTranslation(0f, 0f, 0f)

/**
 * Adds to the vector [another] and scale it by [delta].
 */
fun Vector2.addScl(another: Vector2, delta: Float) {
	val value = TempVector2().set(another).scl(delta)
	this.add(value)
}
