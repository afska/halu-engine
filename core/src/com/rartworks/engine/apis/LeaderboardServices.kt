package com.rartworks.engine.apis

interface LeaderboardServices{
	fun submitScore(score: Long)
	fun showScores()
}
