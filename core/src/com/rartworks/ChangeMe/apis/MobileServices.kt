package com.rartworks.ChangeMe.apis

import com.rartworks.engine.apis.GooglePlayServices
import com.rartworks.engine.apis.InAppBillingServices
import com.rartworks.engine.apis.LeaderboardServices

interface MobileServices {
	val inAppBilling: InAppBillingServices
	val googlePlay: GooglePlayServices
	val leaderboard: LeaderboardServices
}
