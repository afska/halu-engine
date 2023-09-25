package com.rlabs.engine.apis

interface LeaderboardServices {
	fun getScore(leaderboardId: String? = null, callback: (Int) -> (Unit))
	fun getScores(leaderboardId: String? = null, limit: Int = 5, callback: (List<PlayerScore>) -> (Unit))
	fun showScores(leaderboardId: String? = null)
	fun submitScore(score: Long, leaderboardId: String? = null)
}
