package com.rartworks.engine.android.services.integrations.googlePlay

import android.app.Activity
import android.content.Intent
import android.net.Uri

import com.badlogic.gdx.Gdx
import com.google.android.gms.games.Games
import com.google.android.gms.games.GamesActivityResultCodes
import com.google.example.games.basegameutils.GameHelper
import com.rartworks.engine.android.services.integrations.Integration
import com.rartworks.engine.android.utils.showError
import com.rartworks.engine.apis.GooglePlayPreferences
import com.rartworks.engine.apis.GooglePlayServices

/**
 * Manages the integration with Google Play.
 */
class GooglePlayIntegration(app: Activity, val settings: GooglePlaySettings) : Integration(app), GooglePlayServices {
	lateinit var gameHelper: GameHelper

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
		val link = this.settings.playStoreLink
		this.app.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(link)))
	}

	/**
	 * Unlocks an achievement by a [score].
	 */
	override fun unlockAchievementByScore(score: Int) {
		val achievementId = this.settings.achievementsByScore[score]
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

	/**
	 * Creates a Game Helper, used to talk to the Google Play Services.
	 */
	override fun onCreated() {
		this.gameHelper = GameHelper(this.app, GameHelper.CLIENT_GAMES)
		this.gameHelper.enableDebugLog(false)
		this.gameHelper.setMaxAutoSignInAttempts(
			if (this.settings.preferences.hasAlreadyPlayed() && !this.settings.preferences.isSignedIn())
				0
			else
				1
		)

		this.gameHelper.setup(object : GameHelper.GameHelperListener {
			override fun onSignInFailed() {
				Gdx.app.log("[!] GooglePlayGames", "Sign in failed.")
				gameHelper.setMaxAutoSignInAttempts(0)

				val reason = gameHelper.signInError
				if (reason != null) app.showError(reason.toString())
			}

			override fun onSignInSucceeded() {
				Gdx.app.log("[!] GooglePlayGames", "Sign in success.")
				gameHelper.setMaxAutoSignInAttempts(0)
			}
		})
	}

	override fun onStart() {
		this.gameHelper.onStart(this.app)
	}

	override fun onStop() {
		this.gameHelper.onStop()
	}

	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
		this.gameHelper.onActivityResult(requestCode, resultCode, data)
		if (resultCode == GamesActivityResultCodes.RESULT_RECONNECT_REQUIRED)
			this.gameHelper.disconnect()
	}
}

data class GooglePlaySettings(
	val playStoreLink: String,
	val achievementsByScore: Map<Int, String>,
	val preferences: GooglePlayPreferences
)
