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

	private lateinit var body: Body

	override fun initialize(movieClip: MovieClip) {
		this.body = AssetsFactory.polygons.createCircleBody(
			this.box2dWorld, movieClip.scaledWidth, movieClip.info.collisionInfo
		)
	}

	override fun updateWorld() { }
}
