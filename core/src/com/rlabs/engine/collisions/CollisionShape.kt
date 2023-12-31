package com.rlabs.engine.collisions

import com.badlogic.gdx.physics.box2d.Body
import com.rlabs.engine.drawables.MovieClip

/**
 * Represents a shape used by the world for collisions.
 * The [MovieClip] calls to [initialize] when it's ready.
 */
interface CollisionShape {
	var body: Body

	fun initialize(movieClip: MovieClip)
	fun updateWorld()
}
