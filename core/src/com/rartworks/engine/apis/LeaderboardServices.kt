package com.rartworks.engine.apis

interface LeaderboardServices {
	fun getScores(leaderboardId: String?, limit: Int = 5, callback: (List<PlayerScore>) -> (Unit))
	fun showScores(leaderboardId: String? = null)
	fun submitScore(score: Long, leaderboardId: String? = null)
}
