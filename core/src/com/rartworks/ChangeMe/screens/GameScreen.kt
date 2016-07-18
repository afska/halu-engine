package com.rartworks.ChangeMe.screens

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TmxMapLoader
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
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
	lateinit var tiledMap: TiledMap
	lateinit var tiledMapRenderer: OrthogonalTiledMapRenderer

	private lateinit var gameController: InputHandler
	private val alphaTween = QuadTween(this)
	val renderer = Renderer(this.dimensions)
	private val preRenderMethod: () -> (Unit) = {
		this.renderer.camera.update()
		this.tiledMapRenderer.setView(this.renderer.camera)
		this.tiledMapRenderer.render()
	}
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
		val halu = MovieClip(AssetsFactory.createMovieClipInfo("iddle", CollisionInfo(1, 2)))
		halu.scale = 0.3f
		this.addChild(halu)

		this.tiledMap = TmxMapLoader().load("myshittymap.tmx")
		this.tiledMapRenderer = OrthogonalTiledMapRenderer(this.tiledMap)
	}

	override fun render(delta: Float) {
		if (!AssetsLoader.manager.update()) return

		this.update(delta)
		this.alphaTween.update(delta)

		this.renderer.render(this.renderMethod, this.preRenderMethod)
	}

	override fun dispose() {
		this.renderer.dispose()
	}
}
