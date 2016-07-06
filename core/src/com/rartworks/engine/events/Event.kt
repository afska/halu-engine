package com.rartworks.engine.events

/**
 * An event that can be fired.
 */
class Event {
	private var handlers: MutableList<() -> (Unit)> = arrayListOf()

	/**
	 * Subscribes to the event with a [handler].
	 */
	operator fun invoke(handler: () -> (Unit)) {
		this.handlers.add(handler)
	}

	/**
	 * Fires the event.
	 */
	fun fire() {
		this.handlers.forEach { it() }
	}
}
