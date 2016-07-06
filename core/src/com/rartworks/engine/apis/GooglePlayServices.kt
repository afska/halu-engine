package com.rartworks.engine.apis

interface GooglePlayServices {
	fun signIn()
	fun signOut()
	fun rateGame()
	fun submitScore(score: Long)
	fun showScores()
	fun unlockAchievementByScore(meters: Int)
	fun isSignedIn(): Boolean
}
