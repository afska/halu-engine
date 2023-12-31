package com.rlabs.engine.rendering

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Matrix4
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.rlabs.engine.utils.TempVector2
import com.rlabs.engine.utils.render

private val NORMAL = Vector3(0f, 0f, 1f)

/**
 * A game object that can be drawable on the screen.
 */
interface Drawable {
	val position: Vector2
	val color: Color
	var scale: Float
	var rotation: Float
	val origin: Vector2
	var alpha: Float
		get() = this.color.a
		set(value) { this.color.a = value }

	val width: Float
	val height: Float
	val scaledWidth: Float get() = this.width * this.scale
	val scaledHeight: Float get() = this.height * this.scale

	fun update(delta: Float) { }

	fun render(spriteBatch: SpriteBatch) {
		spriteBatch.render(this.color, {
			this.transformMatrix(it)
		}) { this.draw(spriteBatch) }
	}

	fun render(shapeRenderer: ShapeRenderer) {
		shapeRenderer.render(this.color, {
			this.transformMatrix(it)
		}) { this.draw(shapeRenderer) }
	}

	fun draw(spriteBatch: SpriteBatch)
	fun draw(shapeRenderer: ShapeRenderer) { }

	// ---

	/**
	 * Does the update with the absolute values inherited from the parent. The override is optional.
	 */
	fun updateAbs(delta: Float) { }

	/**
	 * Moves to a position using the [origin] to center the object.
	 */
	fun moveTo(x: Float, y: Float) {
		this.position.x = x - origin.x * this.width
		this.position.y = y - origin.y * this.height
	}

	/**
	 * The position of the [Drawable] using an [origin] as center.
	 * @return A *TEMPORAL* Vector2.
	 */
	fun toOriginPosition(origin: Vector2 = this.origin): Vector2 {
		return TempVector2(
			origin.x * this.width,
			origin.y * this.height
		).add(this.position)
	}

	/**
	 * The position of the [Drawable] (scaled and rotated with the [origin]) from (0, 0).
	 * @return A *TEMPORAL* Vector2.
	 */
	fun toCornerPosition(): Vector2 {
		// fixing scale...
		val cornerPosition = TempVector2(
			-(this.scaledWidth - this.width) * this.origin.x,
			-(this.scaledHeight - this.height) * this.origin.y
		).add(this.position)

		// fixing rotation...
		val radians = this.rotation * MathUtils.degreesToRadians
		val originX = this.origin.x * this.scaledWidth
		val originY = this.origin.y * this.scaledHeight
		val angle = radians + MathUtils.atan2(originY, originX)
		val R = Math.sqrt((originX * originX + originY * originY).toDouble()).toFloat()
		val offsetX = originX - R * MathUtils.cos(angle)
		val offsetY = originY - R * MathUtils.sin(angle)
		cornerPosition.x += offsetX
		cornerPosition.y += offsetY

		return cornerPosition
	}

	/**
	 * Returns if a point formed by [x] and [y] is inside the [Drawable]'s rectangle or not.
	 */
	fun isIn(x: Int, y: Int, tolerance: Int = 0) =
		 this.position.x - tolerance <= x && x <= this.position.x + this.scaledWidth + tolerance &&
			this.position.y - tolerance <= y && y <= this.position.y + this.scaledHeight + tolerance

	private fun transformMatrix(matrix: Matrix4) =
		matrix
			.translate(this.position.x, this.position.y, 0f)
			.translate(this.origin.x * this.width, this.origin.y * this.height, 0f)
			.scale(this.scale, this.scale, 1f)
			.rotate(NORMAL, this.rotation)
			.translate(-this.origin.x * this.width, -this.origin.y * this.height, 0f)
}
