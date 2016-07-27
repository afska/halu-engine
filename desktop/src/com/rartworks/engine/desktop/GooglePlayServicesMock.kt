package com.rartworks.engine.desktop

import com.rartworks.engine.apis.GooglePlayServices

class GooglePlayServicesMock : GooglePlayServices {
	override fun signIn() {

	}

	override fun signOut() {

	}

	override fun rateGame() {

	}

	override fun submitScore(score: Long) {

	}

	override fun showScores() {

	}

	override fun unlockAchievementByScore(score: Int) {

	}

	override fun isSignedIn(): Boolean {
		return false
	}
}
