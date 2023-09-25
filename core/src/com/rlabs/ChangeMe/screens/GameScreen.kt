package com.rlabs.ChangeMe.screens

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.rlabs.ChangeMe.AssetsLoader
import com.rlabs.ChangeMe.GameContext
import com.rlabs.ChangeMe.screens.input.GameController
import com.rlabs.engine.AssetsFactory
import com.rlabs.engine.collisions.CollisionInfo
import com.rlabs.engine.collisions.Polygon
import com.rlabs.engine.drawables.ComplexDrawable
import com.rlabs.engine.drawables.Label
import com.rlabs.engine.drawables.MovieClip
import com.rlabs.engine.events.InputHandler
import com.rlabs.engine.rendering.Dimensions
import com.rlabs.engine.rendering.Renderer
import com.rlabs.engine.rendering.Screen
import com.rlabs.engine.tween.DrawableQuadTween

/**
 * The main screen of the game.
 */
class GameScreen(private val game: GameContext) : ComplexDrawable(), Screen {
	private val TWEEN_TIME = 0.5f

	val dimensions: Dimensions = this.game.dimensions
	val hello: Label

	private val gameController: InputHandler
	private val alphaTween = DrawableQuadTween(this)
	private val renderer = Renderer(this.dimensions)
	private val renderMethod: (SpriteBatch) -> (Unit) = { spriteBatch ->
		this.render(spriteBatch)
	}

	init {
		this.gameController = GameController(this).register()
		this.alphaTween.start(1f, TWEEN_TIME)

		// Add hello world
		this.hello = Label(AssetsLoader.font, "Hello world!")
		hello.moveTo(this.dimensions.width / 2, this.dimensions.height / 2)
		this.addChild(hello)

		// Add animated sprite
		this.addChild(MovieClip(AssetsFactory.createMovieClipInfo("iddle", CollisionInfo(Polygon(), 1, 2))))
	}

	override fun render(delta: Float) {
		if (!AssetsLoader.manager.update()) return

		this.update(delta)
		this.alphaTween.update(delta)

		this.renderer.render(this.renderMethod)
	}

	override fun dispose() {
		this.renderer.dispose()
	}
}
