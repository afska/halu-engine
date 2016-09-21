package com.rartworks.engine.collisions

import com.badlogic.gdx.physics.box2d.Body
import com.rartworks.engine.AssetsFactory
import com.rartworks.engine.drawables.MovieClip
import com.rartworks.engine.utils.createCircleBody
import com.badlogic.gdx.physics.box2d.World as Box2dWorld

/**
 * A bounding circle used by the engine for collision detection.
 */
class BoundingCircle() : CollisionShape {
	private val box2dWorld = AssetsFactory.box2dWorld

	override lateinit var body: Body

	private lateinit var movieClip: MovieClip

	/**
	 * Saves the [movieClip] and creates the [Body].
	 */
	override fun initialize(movieClip: MovieClip) {
		this.movieClip = movieClip

		this.body = AssetsFactory.polygons.createCircleBody(
			this.box2dWorld, this.movieClip.scaledWidth, this.movieClip.info.collisionInfo!!
		)
	}

	/**
	 * Updates the position of the shape.
	 */
	override fun updateWorld() {
		this.body.setTransform(this.movieClip.toOriginPosition(), 0f)
	}
}
