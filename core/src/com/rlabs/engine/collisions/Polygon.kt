package com.rlabs.engine.collisions

import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.physics.box2d.Body
import com.rlabs.engine.AssetsFactory
import com.rlabs.engine.drawables.MovieClip
import com.rlabs.engine.utils.addTrailingZeros
import com.rlabs.engine.utils.createPolygonBody
import java.util.*
import com.badlogic.gdx.physics.box2d.World as Box2dWorld

/**
 * A polygon used by the engine for collision detection.
 * It tracks the movieClip parameters, and replicates its variables in the *Box2d* world.
 */
class Polygon() : CollisionShape {
	private val box2dWorld = AssetsFactory.box2dWorld

	override lateinit var body: Body

	private lateinit var movieClip: MovieClip
	private var currentProperties: HashMap<String, Number> = hashMapOf(
		Pair("frameIndex", 0), Pair("scale", 1f)
	)
	private var outOfSync = false

	private var framesCount: Int = 0
	private lateinit var framesNames: List<String>
	private val currentFrameName: String get() {
		return if (this.framesCount == 1)
			this.movieClip.info.regionName
		else
			this.framesNames[this.movieClip.currentFrameIndex]
	}

	/**
	 * Saves the [movieClip] and loads the first [Body].
	 */
	override fun initialize(movieClip: MovieClip) {
		this.movieClip = movieClip

		this.framesCount = this.movieClip.info.frames.count()
		this.framesNames = (0 .. this.framesCount - 1).map {
			val index = (it + 1).addTrailingZeros(4)
			"${this.movieClip.info.regionName}_$index"
		}
		this.loadBody()
	}

	/**
	 * Keeps track of the current frame and scale of the movie clip.
	 * If it has changed, recreates the adapted polygon.
	 */
	override fun updateWorld() {
		this.syncWithMovieClip("frameIndex", this.movieClip.currentFrameIndex)
		this.syncWithMovieClip("scale", this.movieClip.scale)
		if (this.outOfSync) {
			this.reloadBody()
			this.outOfSync = false
		}

		val rotationInRadians = this.movieClip.rotation * MathUtils.degreesToRadians
		val cornerPosition = this.movieClip.toCornerPosition()

		this.body.setTransform(cornerPosition, rotationInRadians)
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
	private fun reloadBody() {
		this.box2dWorld.destroyBody(this.body)
		this.loadBody()
	}

	/**
	 * Loads the *Box2d* body.
	 */
	private fun loadBody() {
		this.body = AssetsFactory.polygons.createPolygonBody(
			this.box2dWorld, this.movieClip,
			this.movieClip.scaledWidth, this.currentFrameName
		)
	}
}
