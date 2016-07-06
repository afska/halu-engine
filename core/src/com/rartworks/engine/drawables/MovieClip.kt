package com.rartworks.engine.drawables

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Array
import com.rartworks.engine.collisions.*
import com.rartworks.engine.rendering.*

private val FRAME_DURATION = 0.03f

/**
 * An animated movie clip that loops by default.
 */
open class MovieClip(val info: MovieClipInfo) : Animation(FRAME_DURATION, info.frames), Drawable {
	override val position = Vector2()
	override val color = Color().set(Color.WHITE)
	override var scale = 1f
	override var rotation = 0f
	override val origin = Vector2(0.5f, 0.5f)
	override val width: Float get() = this.currentFrame.regionWidth.toFloat()
	override val height: Float get() = this.currentFrame.regionHeight.toFloat()

	val currentFrame: TextureRegion get() = this.getKeyFrame(this.runTime)
	val currentFrameIndex: Int get() = (this.runTime / FRAME_DURATION).toInt()

	private val polygon: Polygon?
	private var isPlaying = true
	private var loop: LoopInfo = LoopInfo(1, info.frames.size)
	private var runTime = 0f

	init {
		this.polygon = if (this.info.collisionInfo != null) Polygon(this) else null
	}

	override fun update(delta: Float) {
		if (this.isPlaying) {
			this.runTime += delta

			val hasToRestartLoop = this.currentFrameIndex > this.loop.to
			if (hasToRestartLoop) this.goTo(this.loop.from)
		}
	}

	override fun updateAbs(delta: Float) {
		this.polygon?.updateWorld()
	}

	override fun draw(spriteBatch: SpriteBatch) {
		spriteBatch.draw(this.currentFrame, 0f, 0f)
	}

	/**
	 * Starts a loop.
	 */
	fun loopWith(loopInfo: LoopInfo) {
		this.loop = loopInfo
		this.goTo(loopInfo.from)
	}

	/**
	 * Goes to the frame in [index].
	 */
	fun goTo(index: Int) { this.runTime = index * FRAME_DURATION }

	fun stop() { this.pause() ; this.goTo(0) }

	fun pause() { this.isPlaying = false }

	fun play() { this.isPlaying = true }

	fun rePlay() { this.stop() ; this.play() }
}

data class MovieClipInfo(
	val regionName: String,
	val frames: Array<Sprite>,
	val collisionInfo: CollisionInfo? = null
)

data class LoopInfo(var from: Int, var to: Int) {
	init {
		this.from = from - 1
		this.to = to - 1
	}
}
