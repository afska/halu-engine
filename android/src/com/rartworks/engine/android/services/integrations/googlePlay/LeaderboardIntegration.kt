package com.rartworks.engine.android.services.integrations.googlePlay

import android.app.Activity
import com.google.android.gms.games.Games
import com.rartworks.engine.android.services.integrations.Integration
import com.rartworks.engine.apis.LeaderboardServices
import java.util.*

/**
 * Manages the integration with Google Play Leaderboards.
 */
class LeaderboardIntegration(app: Activity, private val googlePlayIntegration: GooglePlayIntegration, private val leaderboardIds: List<String>) : Integration(app), LeaderboardServices {
	companion object {
		private const val RC_SHOW_LEADERBOARD = 1
	}

	/**
	 * Shows the leaderboard.
	 */
	override fun showScores(leaderboardId: String?) {
		val id = this.getLeaderboardId(leaderboardId)

		if (this.googlePlayIntegration.isSignedIn())
			this.app.startActivityForResult(
				Games.Leaderboards.getLeaderboardIntent(
					this.googlePlayIntegration.gameHelper.apiClient,
					id
				),
				RC_SHOW_LEADERBOARD
			)
		else
			this.googlePlayIntegration.signIn()
	}

	/**
	 * Submits a [score] to the leaderboard.
	 */
	override fun submitScore(score: Long, leaderboardId: String?) {
		val id = this.getLeaderboardId(leaderboardId)

		if (this.googlePlayIntegration.isSignedIn())
			Games.Leaderboards.submitScore(
				this.googlePlayIntegration.gameHelper.apiClient,
				id,
				score
			)
	}

	/**
	 * Returns the same [leaderboardId] or the only one.
	 */
	private fun getLeaderboardId(leaderboardId: String?): String {
		return this.getOrThrow {
			if (leaderboardId != null)
				this.leaderboardIds.first { it == leaderboardId }
			else
				this.leaderboardIds.single()
		}
	}

	/**
	 * Executes the [action] to find a leaderboard or it throws an exception if it doesn't exist.
	 */
	private inline fun getOrThrow(action: () -> (String)): String {
		try {
			return action()
		} catch (e: NoSuchElementException) {
			throw RuntimeException("The leaderboard id was not found.")
		}
	}
}
