package com.rartworks.engine.drawables

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.rartworks.engine.rendering.Drawable

/**
 * A label with text.
 */
open class Label(private val bitmapFont: BitmapFont, text: String = "") : Drawable {
	var text: String = ""
		set(value) {
			field = value
			this.glyphLayout.setText(this.bitmapFont, value)
		}

	override val position = Vector2()
	override val color = Color().set(Color.WHITE)
	override var scale = 1f
	override var rotation = 0f
	override val origin = Vector2(0.5f, 0.5f)
	override val width: Float get() = this.glyphLayout.width
	override val height: Float get() = this.glyphLayout.height

	private val glyphLayout = GlyphLayout(this.bitmapFont, this.text)
	private val tmpColor = Color()

	init {
		this.text = text
	}

	override fun draw(spriteBatch: SpriteBatch) {
		this.tmpColor.set(this.bitmapFont.color)

		this.bitmapFont.color.mul(spriteBatch.color)
		this.bitmapFont.draw(
			spriteBatch,
			this.text,
			0f,
			this.height // the texture is y-flipped
		)

		this.bitmapFont.color.set(this.tmpColor)
	}
}
