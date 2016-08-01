package com.rartworks.engine.apis

interface GooglePlayServices {
	fun signIn()
	fun signOut()
	fun rateGame()
	fun unlockAchievementByScore(meters: Int)
	fun isSignedIn(): Boolean
}
