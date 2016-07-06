package com.rartworks.engine.events

/**
 * An event that can be fired with parameters.
 */
open class EventWithInfo<TInfo> {
	private var handlers: MutableList<(TInfo) -> (Unit)> = arrayListOf()

	/**
	 * Subscribes to the event with a [handler].
	 */
	operator fun invoke(handler: (TInfo) -> (Unit)) {
		this.handlers.add(handler)
	}

	/**
	 * Fires the event.
	 */
	fun fire(info: TInfo) {
		this.handlers.forEach { it(info) }
	}
}
