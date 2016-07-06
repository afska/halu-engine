package com.rartworks.engine.drawables.shapes

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.rartworks.engine.rendering.*

/**
 * A line [from] a point [to] another, with a [lineWidth].
 */
open class Line(val from: Vector2, val to: Vector2, var lineWidth: Float) : Drawable {
	override val position = Vector2()
	override val color = Color().set(Color.WHITE)
	override var scale = 1f
	override var rotation = 0f
	override val origin = Vector2(0.5f, 0.5f)
	override val width: Float get() = 1f + this.to.x - this.from.x
	override val height: Float get() = 1f + this.to.y - this.from.y

	override fun draw(shapeRenderer: ShapeRenderer) {
		shapeRenderer.rectLine(this.from.x, this.from.y, this.to.x, this.to.y, this.lineWidth - 1)
	}

	override fun draw(spriteBatch: SpriteBatch) { }
}
