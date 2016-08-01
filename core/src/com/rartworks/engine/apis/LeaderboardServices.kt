package com.rartworks.engine.apis

interface LeaderboardServices{
	fun submitScore(score: Long, leaderboardId: String? = null)
	fun showScores(leaderboardId: String? = null)
}
