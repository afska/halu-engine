package com.rartworks.ChangeMe

import com.badlogic.gdx.Screen
import com.rartworks.engine.apis.MobileServices
import com.rartworks.engine.rendering.Dimensions

interface GameContext {
	val mobileServices: MobileServices?
	val dimensions: Dimensions

	fun setScreen(screen: Screen)
}
