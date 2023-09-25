package com.rlabs.ChangeMe.screens

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.rlabs.ChangeMe.AssetsLoader
import com.rlabs.ChangeMe.GameContext
import com.rlabs.engine.drawables.ComplexDrawable
import com.rlabs.engine.drawables.Label
import com.rlabs.engine.rendering.Dimensions
import com.rlabs.engine.rendering.Renderer
import com.rlabs.engine.rendering.Screen

/**
 * The SplashScreen that shows the percentage of loaded sprites.
 */
class SplashScreen(private val game: GameContext) : ComplexDrawable(), Screen {
	private val dimensions: Dimensions get() = this.game.dimensions

	private val progressFont = BitmapFont()
	private val progressLabel = this.addChild(Label(this.progressFont))
	private val renderer = Renderer(this.dimensions)
	private val renderMethod: (SpriteBatch) -> (Unit) = { spriteBatch ->
		this.render(spriteBatch)
	}

	init {
		this.progressFont.color = Color.LIGHT_GRAY
		this.progressLabel.position.set(this.dimensions.width / 2 - 20, this.dimensions.height / 2)
		this.progressLabel.scale = 5f
	}

	override fun render(delta: Float) {
		if (AssetsLoader.update()) {
			this.game.setScreen(GameScreen(this.game))
			return this.dispose()
		}

		this.progressLabel.text = "${(AssetsLoader.progress * 100).toInt()}%"
		this.renderer.render(this.renderMethod)
	}

	override fun dispose() {
		this.progressFont.dispose()
	}

	override fun resume() {
		// reloads the splash screen because the *progressFont* could be lost.
		this.game.setScreen(SplashScreen(this.game))
		this.dispose()
	}
}
