package com.rartworks.engine.drawables

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2
import com.rartworks.engine.rendering.Drawable
import com.rartworks.engine.rendering.IComplexDrawable

/**
 * Implementation of [IComplexDrawable] with a mutable list of childs.
 */
open class ComplexDrawable : IComplexDrawable {
	override val position = Vector2()
	override val color = Color().set(Color.WHITE)
	override var scale = 1f
	override var rotation = 0f
	override val origin = Vector2(0.5f, 0.5f)

	override val childs: MutableList<Drawable> = arrayListOf()

	override val width: Float get() {
		if (this.childs.isEmpty()) return 0f

		val min = this.childs.minBy { it.position.x }!!
		val max = this.childs.maxBy { it.position.x + it.width }!!
		return (max.position.x + max.width) - (min.position.x)
	}

	override val height: Float get() {
		if (this.childs.isEmpty()) return 0f

		val min = this.childs.minBy({ it.position.y })!!
		val max = this.childs.maxBy({ it.position.y + it.height })!!
		return (max.position.y + max.height) - (min.position.y)
	}
}
