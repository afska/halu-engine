package com.rartworks.ChangeMe

import com.badlogic.gdx.Game
import com.badlogic.gdx.physics.box2d.Box2D
import com.rartworks.engine.apis.*
import com.rartworks.engine.rendering.*
import com.rartworks.ChangeMe.screens.*

/**
 * The main class of the game.
 */
class GameCore(val mobileServices: MobileServices) : Game() {
	val dimensions = Dimensions(1280f, 720f)

	/**
	 * Loads everything and sets the SplashScreen.
	 */
	override fun create() {
		Box2D.init()
		GamePreferences.initialize()
		AssetsLoader.load()
		this.setScreen(SplashScreen(this))
	}

	/**
	 * Disposes everything.
	 */
	override fun dispose() {
		super.dispose()
		AssetsLoader.dispose()
	}
}
