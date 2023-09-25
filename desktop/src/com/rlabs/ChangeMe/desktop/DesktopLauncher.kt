package com.rlabs.ChangeMe.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.rlabs.ChangeMe.GameCore

/**
 * Launcher for desktop.
 */
object DesktopLauncher {
	@JvmStatic fun main(arg: Array<String>) {
		val game = GameCore(null)
		val config = LwjglApplicationConfiguration()
		config.title = "ChangeMe"
		config.width = game.dimensions.width.toInt()
		config.height = game.dimensions.height.toInt()
		LwjglApplication(game, config)
	}
}
