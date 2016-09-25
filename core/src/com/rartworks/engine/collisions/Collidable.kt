package com.rartworks.engine.collisions

import com.badlogic.gdx.math.Vector2
import com.rartworks.engine.rendering.Drawable

interface Collidable : Drawable {
	fun onCollide(another: Collidable, points: Array<Vector2>)
}
