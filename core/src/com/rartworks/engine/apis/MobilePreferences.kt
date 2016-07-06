package com.rartworks.engine.apis

interface MobilePreferences {
	fun hasAlreadyPlayed(): Boolean
	fun isSignedIn(): Boolean

	fun hasFullVersion(): Boolean
	fun setFullVersion()
}
