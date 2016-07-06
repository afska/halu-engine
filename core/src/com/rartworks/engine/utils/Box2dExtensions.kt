package com.rartworks.engine.utils

import aurelienribon.bodyeditor.BodyEditorLoader
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.rartworks.engine.collisions.*
import com.badlogic.gdx.physics.box2d.World as Box2dWorld

/**
 * Creates a Box2d body in a [world] with a human [name], a [width], and [collisionInfo].
 */
fun BodyEditorLoader.createBody(world: Box2dWorld, name: String, width: Float, collisionInfo: CollisionInfo?): Body {
	val bodyDef = BodyDef()
	bodyDef.type = BodyDef.BodyType.DynamicBody

	val body = world.createBody(bodyDef)
	body.userData = name

	val fixture = FixtureDef()
	collisionInfo.doIfExists {
		fixture.filter.categoryBits = it.categoryBits
		fixture.filter.maskBits = it.maskBits
	}
	this.attachFixture(body, name, fixture, width)

	return body
}

/**
 * Forces the world to simulate, to check collisions.
 */
fun Box2dWorld.checkContacts() {
	this.step(1f, 1, 1)
}
