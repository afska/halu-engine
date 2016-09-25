package com.rartworks.engine.utils

import aurelienribon.bodyeditor.BodyEditorLoader
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.CircleShape
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.rartworks.engine.collisions.CollisionInfo
import com.rartworks.engine.drawables.MovieClip
import com.badlogic.gdx.physics.box2d.World as Box2dWorld

/**
 * Creates a Box2d body of a [movieClip] in a [world] with a [width].
 * The fixture is a polygon based on the [name].
 */
fun BodyEditorLoader.createPolygonBody(world: Box2dWorld, movieClip: MovieClip, width: Float, name: String): Body {
	return createBox2dBody(world, movieClip.info.collisionInfo!!) { body, fixture ->
		this.attachFixture(body, name, fixture, width)
		body.userData = movieClip
	}
}

/**
 * Creates a Box2d body of a [movieClip] in a [world] with a [width].
 * The fixture is a circle.
 */
fun BodyEditorLoader.createCircleBody(world: Box2dWorld, movieClip: MovieClip, width: Float): Body {
	return createBox2dBody(world, movieClip.info.collisionInfo!!) { body, fixture ->
		fixture.shape = CircleShape()
		fixture.shape.radius = width / 2
		body.createFixture(fixture)
		body.userData = movieClip
	}
}

/**
 * Forces the world to simulate, to check collisions.
 */
fun Box2dWorld.checkContacts() {
	this.step(1f, 1, 1)
}

/**
 * Creates a [Body] and calls [addFixture] after. Returns the [Body].
 */
private inline fun createBox2dBody(world: Box2dWorld, collisionInfo: CollisionInfo, addFixture: (Body, FixtureDef) -> (Unit)): Body {
	val bodyDef = BodyDef()
	bodyDef.type = BodyDef.BodyType.DynamicBody

	val body = world.createBody(bodyDef)
	body.userData = collisionInfo.categoryBits

	val fixture = FixtureDef()
	fixture.filter.categoryBits = collisionInfo.categoryBits
	fixture.filter.maskBits = collisionInfo.maskBits

	addFixture(body, fixture)

	return body
}
