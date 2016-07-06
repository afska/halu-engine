package com.rartworks.engine.utils

/**
 * Executes the [action] if it exist.
 */
inline fun <T : Any> T?.doIfExists(action: (T) -> (Unit)): T? {
	if (this != null) action(this)
	return this
}

/**
 * Executes the [action] if it doesn't exist.
 */
inline fun <T : Any> T?.orElse(action: () -> (Unit)): T? {
	if (this == null) action()
	return this
}
