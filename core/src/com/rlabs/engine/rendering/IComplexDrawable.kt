package com.rlabs.engine.rendering

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.rlabs.engine.utils.fastForEach
import com.rlabs.engine.utils.joinWith

/**
 * A [Drawable] that contains many [Drawable]s.
 */
interface IComplexDrawable<T : Drawable> : Drawable {
	val childs: MutableList<T>

	override fun update(delta: Float) {
		this.childs.fastForEach {
			it.update(delta)

			it.joinWith(this) {
				it.updateAbs(delta)
			}
		}
	}

	override fun draw(spriteBatch: SpriteBatch) {
		this.childs.fastForEach { it.render(spriteBatch) }
	}

	override fun draw(shapeRenderer: ShapeRenderer) {
		this.childs.fastForEach { it.render(shapeRenderer) }
	}

	/**
	 * Adds a child. The order that this method is called matters (it affects the Z-index).
	 */
	fun <TChild : T> addChild(child: TChild): TChild {
		this.childs.add(child)
		return child
	}

	/**
	 * Adds many childs.
	 */
	fun <TChild : T> addChilds(childs: List<TChild>): List<TChild> {
		return childs.map { this.addChild(it) }
	}

	/**
	 * Removes a child.
	 */
	fun removeChild(child: T) {
		this.childs.remove(child)
	}

	/**
	 * Removes all the childs.
	 */
	fun removeAllChilds() {
		this.childs.clear()
	}
}
