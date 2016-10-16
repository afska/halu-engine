package com.rartworks.engine.collisions

/**
 * Contains a [shape] and info container for collision filtering.
 */
class CollisionInfo(val shape: CollisionShape, private val id: Int, vararg private val collidesWith: Int) {
	val categoryBits: Short get() = this.id.toShort()
	val maskBits: Short

	init {
		val others = this.collidesWith.toList()

		this.maskBits = others.fold(0) { prev, elem ->
			prev.or(elem)
		}.toShort()
	}

	/**
	 * Sets the [maskBits] to the body.
	 */
	fun enable() {
		this.setMaskBits(this.maskBits)
	}

	/**
	 * Clear the body's maskBits to disable collisions.
	 */
	fun disable() {
		this.setMaskBits(0)
	}

	/**
	 * Sets the [maskBits] to the body.
	 */
	private fun setMaskBits(maskBits: Short) {
		this.shape.body.fixtureList.forEach {
			val filterData = it.filterData
			filterData.maskBits = maskBits
			it.filterData = filterData
		}
	}
}
