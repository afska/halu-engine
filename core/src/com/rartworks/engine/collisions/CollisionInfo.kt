package com.rartworks.engine.collisions

/**
 * Info container for collision filtering.
 */
class CollisionInfo(private val id: Int, vararg private val collidesWith: Int) {
	val categoryBits: Short get() = this.id.toShort()
	val maskBits: Short

	init {
		val others = this.collidesWith.toList()

		this.maskBits = others.fold(0) { prev, elem ->
			prev.or(elem)
		}.toShort()
	}
}
