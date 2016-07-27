package com.rartworks.ChangeMe.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.rartworks.ChangeMe.*
import com.rartworks.engine.apis.*
import com.rartworks.engine.desktop.*

/**
 * Launcher for desktop.
 */
object DesktopLauncher {
	@JvmStatic fun main(arg: Array<String>) {
		val mobileServicesMock = MobileServices(GooglePlayServicesMock(), InAppBillingServicesMock())
		val game = GameCore(mobileServicesMock)
		val config = LwjglApplicationConfiguration()
		config.title = "ChangeMe"
		config.width = game.dimensions.width.toInt()
		config.height = game.dimensions.height.toInt()
		LwjglApplication(game, config)
	}
}
