package com.rartworks.engine.apis

interface GooglePlayPreferences {
	fun hasAlreadyPlayed(): Boolean
	fun isSignedIn(): Boolean
}
