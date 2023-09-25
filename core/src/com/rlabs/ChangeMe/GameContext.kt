package com.rlabs.ChangeMe

import com.badlogic.gdx.Screen
import com.rlabs.engine.apis.MobileServices
import com.rlabs.engine.rendering.Dimensions

interface GameContext {
	val mobileServices: MobileServices?
	val dimensions: Dimensions

	fun setScreen(screen: Screen)
}
