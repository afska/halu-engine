package com.rartworks.engine.collisions

import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.physics.box2d.Body
import com.rartworks.engine.AssetsFactory
import com.rartworks.engine.drawables.MovieClip
import com.rartworks.engine.utils.addTrailingZeros
import com.rartworks.engine.utils.createBody
import com.rartworks.engine.utils.doIfExists
import java.util.*
import com.badlogic.gdx.physics.box2d.World as Box2dWorld

/**
 * A polygon used by the engine for collision detection.
 * It tracks the movieClip parameters, and replicates its variables in the *Box2d* world.
 */
class Polygon(private val movieClip: MovieClip) {
	private val box2dWorld = AssetsFactory.box2dWorld

	private var currentBody: Body? = null
	private var currentProperties: HashMap<String, Number> = hashMapOf(
		Pair("frameIndex", 0), Pair("scale", 1f)
	)
	private var outOfSync = false

	private val framesCount: Int
	private val currentFrameName: String get() {
		val info = this.movieClip.info

		return if (this.framesCount == 1)
			info.regionName
		else {
			val index = (this.movieClip.currentFrameIndex + 1).addTrailingZeros(4)
			"${info.regionName}_$index"
		}
	}

	init {
		this.framesCount = this.movieClip.info.frames.count()
		this.loadBody()
	}

	/**
	 * Keeps track of the current frame and scale of the movie clip.
	 * If it has changed, recreates the adapted polygon.
	 */
	fun updateWorld() {
		this.syncWithMovieClip("frameIndex", this.movieClip.currentFrameIndex)
		this.syncWithMovieClip("scale", this.movieClip.scale)
		if (this.outOfSync) {
			this.loadBody()
			this.outOfSync = false
		}

		val rotationInRadians = this.movieClip.rotation * MathUtils.degreesToRadians
		val cornerPosition = this.movieClip.toCornerPosition()

		this.currentBody?.setTransform(cornerPosition, rotationInRadians)
	}

	/**
	 * If a value doesn't match, it marks the [Polygon] as "out of sync".
	 */
	private fun syncWithMovieClip(name: String, value: Number) {
		if (this.currentProperties[name] != value) {
			this.currentProperties[name] = value
			this.outOfSync = true
		}
	}

	/**
	 * Reloads the *Box2d* body.
	 */
	private fun loadBody() {
		this.currentBody.doIfExists {
			this.box2dWorld.destroyBody(this.currentBody)
		}

		this.currentBody = AssetsFactory.polygons.createBody(
			this.box2dWorld, this.currentFrameName,
			this.movieClip.scaledWidth, this.movieClip.info.collisionInfo
		)
	}
}
