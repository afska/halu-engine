package com.rartworks.engine.android.services.integrations.googlePlay

import android.app.Activity
import com.google.android.gms.games.Games
import com.google.android.gms.games.leaderboard.LeaderboardVariant
import com.rartworks.engine.android.services.integrations.Integration
import com.rartworks.engine.apis.LeaderboardServices
import com.rartworks.engine.apis.PlayerScore
import java.util.*

/**
 * Manages the integration with Google Play Leaderboards.
 */
class LeaderboardIntegration(app: Activity, private val googlePlayIntegration: GooglePlayIntegration, private val leaderboardIds: List<LeaderboardId>) : Integration(app), LeaderboardServices {
	companion object {
		private const val RC_SHOW_LEADERBOARD = 1
	}

	override fun getScore(leaderboardId: String?, callback: (Int) -> (Unit)) {
		Games.Leaderboards.loadCurrentPlayerLeaderboardScore(
			this.googlePlayIntegration.gameHelper.apiClient,
			leaderboardId,
			LeaderboardVariant.TIME_SPAN_ALL_TIME,
			LeaderboardVariant.COLLECTION_PUBLIC
		).setResultCallback { loadScoreResult ->
			callback(loadScoreResult.score.rawScore.toInt())
		}
	}
	/**
	 * Retrieves the scores of the leaderboard.
	 */
	override fun getScores(leaderboardId: String?, limit: Int, callback: (List<PlayerScore>) -> (Unit)) {
		val id = this.getLeaderboardId(leaderboardId)

		Games.Leaderboards.loadPlayerCenteredScores(
			this.googlePlayIntegration.gameHelper.apiClient,
			id,
			LeaderboardVariant.TIME_SPAN_ALL_TIME,
			LeaderboardVariant.COLLECTION_PUBLIC,
			limit
		).setResultCallback { loadScoresResult ->
			val scores: MutableList<PlayerScore> = mutableListOf()
			val it = loadScoresResult.scores.iterator()
			while (it.hasNext()) {
				val score = it.next()
				scores.add(GooglePlayerScore(score.scoreHolderDisplayName, score.displayScore.toInt()))
			}
			callback(scores)
		}
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
				this.leaderboardIds.first { it.name == leaderboardId }.id
			else
				this.leaderboardIds.single().id
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
