package com.rartworks.engine.apis

interface MobileServices {
	val inAppBilling: InAppBillingServices
	val googlePlay: GooglePlayServices
	val leaderboard: LeaderboardServices
}
