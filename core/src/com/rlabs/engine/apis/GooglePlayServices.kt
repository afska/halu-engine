package com.rlabs.engine.apis

interface GooglePlayServices {
	fun signIn()
	fun signOut()
	fun rateGame()
	fun unlockAchievementByScore(score: Int)
	fun isSignedIn(): Boolean
}
