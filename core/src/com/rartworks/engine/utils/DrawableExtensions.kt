package com.rartworks.engine.utils

import com.rartworks.engine.rendering.Drawable

/**
 * - Inherits the position, alpha, scale, and rotation values from [another] [Drawable].
 * - Executes the [action].
 * - Restores those values.
 */
inline fun Drawable.joinWith(another: Drawable, action: () -> (Unit)) {
	val oldAlpha = this.alpha
	val oldScale = this.scale

	this.position.add(another.position)
	this.alpha *= another.alpha
	this.scale *= another.scale
	this.rotation += another.rotation

	action()

	this.position.sub(another.position)
	this.alpha = oldAlpha
	this.scale = oldScale
	this.rotation -= another.rotation
}
