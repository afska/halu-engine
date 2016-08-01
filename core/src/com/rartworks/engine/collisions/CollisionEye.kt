package com.rartworks.engine.collisions

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Contact
import com.badlogic.gdx.physics.box2d.ContactImpulse
import com.badlogic.gdx.physics.box2d.ContactListener
import com.badlogic.gdx.physics.box2d.Manifold
import com.rartworks.engine.AssetsFactory
import com.rartworks.engine.utils.checkContacts
import com.badlogic.gdx.physics.box2d.World as Box2dWorld

/**
 * A collision checker... [onCollide], executes the action.
 */
class CollisionEye(private val onCollide: (Any, Any, Array<Vector2>) -> (Unit)) : ContactListener {
	private val box2dWorld = AssetsFactory.box2dWorld

	/**
	 * Registers the contact listener.
	 */
	fun register(): CollisionEye {
		this.box2dWorld.setContactListener(this)
		return this
	}

	/**
	 * Checks for collisions.
	 */
	fun checkCollisions() {
		this.box2dWorld.checkContacts()
	}

	/**
	 * Collision handler.
	 */
	override fun beginContact(contact: Contact) {
		val bodyA = contact.fixtureA.body
		val bodyB = contact.fixtureB.body

		val dataA = bodyA.userData
		val dataB = bodyB.userData
		this.onCollide(dataA, dataB, contact.worldManifold.points)
	}

	/**
	 * Disables the physical reaction of the collision.
	 */
	override fun preSolve(contact: Contact, oldManifold: Manifold) {
		contact.isEnabled = false
	}

	override fun endContact(contact: Contact) { }
	override fun postSolve(contact: Contact, impulse: ContactImpulse) { }
}
