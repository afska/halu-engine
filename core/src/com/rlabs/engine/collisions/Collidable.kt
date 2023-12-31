package com.rlabs.engine.collisions

import com.badlogic.gdx.math.Vector2
import com.rlabs.engine.rendering.Drawable

/**
 * A [Drawable] that can collide with another [Collidable]s.
 */
interface Collidable : Drawable {
	fun onCollide(another: Collidable, points: Array<Vector2>)
}
