package com.rartworks.ChangeMe.screens

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.rartworks.engine.drawables.*
import com.rartworks.engine.rendering.*
import com.rartworks.engine.tween.*
import com.rartworks.ChangeMe.*
import com.rartworks.ChangeMe.screens.input.*
import com.rartworks.engine.*
import com.rartworks.engine.collisions.CollisionInfo
import com.rartworks.engine.events.*

/**
 * The main screen of the game.
 */
class GameScreen(private val game: GameCore) : ComplexDrawable(), Screen {
	private val TWEEN_TIME = 0.5f

	val dimensions: Dimensions = this.game.dimensions
	lateinit var hello: Label

	private lateinit var gameController: InputHandler
	private val alphaTween = QuadTween(this)
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
		this.addChild(MovieClip(AssetsFactory.createMovieClipInfo("iddle", CollisionInfo(1, 2))))
	}

	override fun render(delta: Float) {
		if (!AssetsLoader.manager.update()) return

		this.update(delta)
		this.alphaTween.update(delta)

		this.renderer.render(this.renderMethod)
	}

	override fun show() {
		this.gameController.register()

		this.alpha = 0f
		this.alphaTween.clear()
		this.alphaTween.start(1f, 1f)
	}

	override fun hide() {
		this.gameController.unregister()
	}

	override fun dispose() {
		this.renderer.dispose()
	}
}
