package com.rartworks.engine.rendering

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.rartworks.engine.utils.*

/**
 * A [Drawable] that contains many [Drawable]s.
 */
interface IComplexDrawable : Drawable {
	val childs: MutableList<Drawable>

	override fun update(delta: Float) {
		this.childs.forEach {
			it.update(delta)

			it.joinWith(this) {
				it.updateAbs(delta)
			}
		}
	}

	override fun draw(spriteBatch: SpriteBatch) {
		this.childs.forEach { it.render(spriteBatch) }
	}

	override fun draw(shapeRenderer: ShapeRenderer) {
		this.childs.forEach { it.render(shapeRenderer) }
	}

	/**
	 * Adds a child. The order that this method is called matters (it affects the Z-index).
	 */
	fun <T> addChild(child: T): T {
		this.childs.add(child as Drawable)
		return child
	}

	/**
	 * Adds many childs.
	 */
	fun <T> addChilds(childs: List<T>): List<T> {
		return childs.map { this.addChild(it) }
	}

	/**
	 * Removes a child.
	 */
	fun removeChild(drawable: Drawable) {
		this.childs.remove(drawable)
	}

	/**
	 * Removes all the childs.
	 */
	fun removeAllChilds() {
		this.childs.clear()
	}
}
