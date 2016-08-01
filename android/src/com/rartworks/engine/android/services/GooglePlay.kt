/*

package com.rartworks.engine.android.services

import android.content.Intent
import android.net.Uri

import com.badlogic.gdx.Gdx
import com.google.android.gms.games.Games
import com.google.android.gms.games.GamesActivityResultCodes
import com.google.example.games.basegameutils.GameHelper
import com.rartworks.engine.android.GdxAndroidApp
import com.rartworks.ChangeMe.android.R
import com.rartworks.engine.apis.GooglePlayServices

/**
 * Manages the integration with Google Play.
 */
class GooglePlay(private val app: GdxAndroidApp) : GooglePlayServices {
	lateinit var gameHelper: GameHelper

	companion object {
		private const val RC_SHOW_LEADERBOARD = 1
	}

	/**
	 * Creates a Game Helper, used to talk to the Google Play Services.
	 */
	fun initialize() { // onCreated
		this.gameHelper = GameHelper(this.app, GameHelper.CLIENT_GAMES)
		this.gameHelper.enableDebugLog(false)
		this.gameHelper.setMaxAutoSignInAttempts(
			if (this.app.preferences.hasAlreadyPlayed() && !this.app.preferences.isSignedIn())
				0
			else
				1
		)

		this.gameHelper.setup(object : GameHelper.GameHelperListener {
			override fun onSignInFailed() {
				Gdx.app.log("GooglePlayGames", "Sign in failed.")
				gameHelper.setMaxAutoSignInAttempts(0)

				val reason = gameHelper.signInError
				if (reason != null) app.showError(reason.toString())
			}

			override fun onSignInSucceeded() {
				Gdx.app.log("GooglePlayGames", "Sign in success.")
				gameHelper.setMaxAutoSignInAttempts(0)
			}
		})
	}

	/**
	 * Starts the login with Google Play Services.
	 */
	override fun signIn() {
		this.app.runOnUiThread { gameHelper.beginUserInitiatedSignIn() }
	}

	/**
	 * Starts the logout with Google Play Services.
	 */
	override fun signOut() {
		this.app.runOnUiThread { gameHelper.signOut() }
	}

	/**
	 * Takes the user to the "rate my game" link.
	 */
	override fun rateGame() {
		val link = this.app.tokens.playStoreLink
		this.app.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(link)))
	}

	/**
	 * Submits a score to the leaderboard.
	 */
	override fun submitScore(score: Long) {
		if (this.isSignedIn())
			Games.Leaderboards.submitScore(
				this.gameHelper.apiClient,
				this.app.getString(R.string.leaderboard_high_scores),
				score
			)
	}

	/**
	 * Shows the leaderboard.
	 */
	override fun showScores() {
		if (this.isSignedIn())
			this.app.startActivityForResult(
				Games.Leaderboards.getLeaderboardIntent(
					this.gameHelper.apiClient,
					this.app.getString(R.string.leaderboard_high_scores)
				),
				RC_SHOW_LEADERBOARD
			)
		else
			this.signIn()
	}

	/**
	 * Unlocks an achievement.
	 */
	override fun unlockAchievementByScore(score: Int) {
		val achievementId = this.app.tokens.achievementsByScore[score]
		if (achievementId != null && this.isSignedIn())
			Games.Achievements.unlock(this.gameHelper.apiClient, achievementId)
	}

	/**
	 * Returns if the user is logged in with Google Play Services.
	 */
	override fun isSignedIn() = this.gameHelper.isSignedIn

	// -------
	// Events:
	// -------

	fun onStart() {
		this.gameHelper.onStart(this.app)
	}

	fun onStop() {
		this.gameHelper.onStop()
	}

	fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
		this.gameHelper.onActivityResult(requestCode, resultCode, data)
		if (resultCode == GamesActivityResultCodes.RESULT_RECONNECT_REQUIRED)
			this.gameHelper.disconnect()
	}
}

*/
