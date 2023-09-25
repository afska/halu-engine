package com.rlabs.engine.apis

interface GooglePlayPreferences {
	fun hasAlreadyPlayed(): Boolean
	fun isSignedIn(): Boolean
}
