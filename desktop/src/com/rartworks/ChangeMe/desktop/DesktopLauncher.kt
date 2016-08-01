package com.rartworks.ChangeMe.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.rartworks.ChangeMe.GameCore
import com.rartworks.engine.apis.MobileServices
import com.rartworks.engine.desktop.GooglePlayServicesMock
import com.rartworks.engine.desktop.InAppBillingServicesMock

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
