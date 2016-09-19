package com.rartworks.engine.collisions

import com.rartworks.engine.drawables.MovieClip

/**
 * Represents a shape used by the world for collisions.
 * The [MovieClip] calls to [initialize] when it's ready.
 */
interface CollisionShape {
	fun initialize(movieClip: MovieClip)
	fun updateWorld()
}
