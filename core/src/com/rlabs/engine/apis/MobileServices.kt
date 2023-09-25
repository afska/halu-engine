package com.rlabs.engine.apis

interface MobileServices {
	val inAppBilling: InAppBillingServices
	val googlePlay: GooglePlayServices
	val leaderboard: LeaderboardServices
}
