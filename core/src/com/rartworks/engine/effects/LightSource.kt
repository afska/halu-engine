package com.rartworks.engine.effects

import box2dLight.PointLight
import box2dLight.RayHandler
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2
import com.rartworks.engine.*

/**
 * A source of lights that extends the *Box2dLights* [RayHandler].
 */
class LightSource() : RayHandler(AssetsFactory.box2dWorld) {
	init {
		this.setAmbientLight(1f)
	}

	fun addPointLight(position: Vector2, radius: Float, color: Color = Color.WHITE): PointLight {
		val light = PointLight(this, 8, color, radius, position.x, position.y)
		light.isXray = true
		return light
	}
}
