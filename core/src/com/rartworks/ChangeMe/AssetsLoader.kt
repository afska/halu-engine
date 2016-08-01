package com.rartworks.ChangeMe

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.utils.I18NBundle
import com.rartworks.engine.AssetsFactory
import com.badlogic.gdx.physics.box2d.World as Box2dWorld

/**
 * The manager of all the game assets.
 */
object AssetsLoader {
	private val FONT_PATH = "fonts/debrie-bold.ttf"
	private val I18N_PATH = "locales/messages"

	val locales: I18NBundle get() = AssetsFactory.getI18n(I18N_PATH)
	val font: BitmapFont get() = AssetsFactory.getFont(FONT_PATH)

	val progress: Float get() = this.manager.progress
	val manager: AssetManager get() = AssetsFactory.manager

	/**
	 * Loads the background synchronously and the rest asynchronously.
	 */
	fun load() {
		AssetsFactory.init()

		// (load the splash screen assets synchronously)
		//AssetsFactory.loadTexture("world/night.png")
		//this.manager.finishLoading()


		// (load the rest asynchronously)
		AssetsFactory
			.load("demo.atlas", "polygons.json")
			.loadFont(FONT_PATH, 42, FONT_PATH)
			.loadI18nBundle(I18N_PATH)
	}

	/**
	 * Loads a bit more and returns if the load has finished.
	 */
	fun update(): Boolean {
		val hasFinished = this.manager.update()
		return hasFinished
	}

	/**
	 * Disposes all the assets.
	 */
	fun dispose() {
		AssetsFactory.dispose()
	}
}
