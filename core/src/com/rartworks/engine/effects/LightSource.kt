package com.rartworks.engine.effects

import box2dLight.PointLight
import box2dLight.RayHandler
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2
import com.rartworks.engine.AssetsFactory

/**
 * A source of lights that extends the *Box2dLights* [RayHandler].
 */
class LightSource() : RayHandler(AssetsFactory.box2dWorld) {
	private val RAY_NUMBER = 8

	init {
		this.setAmbientLight(1f)
	}

	/**
	 * Adds a [PointLight] with a [radius] at [position].
	 */
	fun addPointLight(position: Vector2, radius: Float, color: Color = Color.WHITE): PointLight {
		val light = PointLight(this, RAY_NUMBER, color, radius, position.x, position.y)
		light.isXray = true
		return light
	}

	/**
	 * Adds a [ConeLight] with [distance], [directionDegree], and [coneDegree] at [position].
	 */
	fun addConeLight(position: Vector2, distance: Float, directionDegree: Float, coneDegree: Float, color: Color = Color.WHITE): ConeLight {
		val light = ConeLight(this, RAY_NUMBER, color, distance, position.x, position.y, directionDegree, coneDegree)
		light.isXray = true
		return light
	}
}
