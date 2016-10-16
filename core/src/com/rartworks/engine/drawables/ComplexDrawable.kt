package com.rartworks.engine.drawables

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2
import com.rartworks.engine.rendering.Drawable
import com.rartworks.engine.rendering.IComplexDrawable
import com.rartworks.engine.utils.fastMaxBy
import com.rartworks.engine.utils.fastMinBy

/**
 * Implementation of [IComplexDrawable] with a mutable list of childs of type [T].
 */
open class ComplexDrawableOf<T : Drawable> : IComplexDrawable<T> {
	override val position = Vector2()
	override val color = Color().set(Color.WHITE)
	override var scale = 1f
	override var rotation = 0f
	override val origin = Vector2(0.5f, 0.5f)

	override val childs: MutableList<T> = arrayListOf()

	override val width: Float get() {
		if (this.childs.isEmpty()) return 0f

		val min = this.childs.fastMinBy { it.position.x }!!
		val max = this.childs.fastMaxBy { it.position.x + it.width }!!
		return (max.position.x + max.width) - (min.position.x)
	}

	override val height: Float get() {
		if (this.childs.isEmpty()) return 0f

		val min = this.childs.fastMinBy({ it.position.y })!!
		val max = this.childs.fastMaxBy({ it.position.y + it.height })!!
		return (max.position.y + max.height) - (min.position.y)
	}
}

/**
 * A normal [Drawable], but it can have other [Drawable]s as [childs].
 */
open class ComplexDrawable : ComplexDrawableOf<Drawable>() { }
