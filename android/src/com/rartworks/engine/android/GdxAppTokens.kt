package com.rartworks.engine.android

class GdxAppTokens {
	// Ads
	lateinit var adsId: String

	// Google Play
	lateinit var playStoreLink: String
	lateinit var achievementsByScore: Map<Int, String>

	// In App Billing
	lateinit var billingKey: String
	lateinit var removeAdsSku: String

	// --------
	// Setters:
	// --------

	fun withAdsId(value: String): GdxAppTokens {
		this.adsId = value
		return this
	}

	fun withPlayStoreLink(value: String): GdxAppTokens {
		this.playStoreLink = value
		return this
	}

	fun withAchievementsByScore(value: Map<Int, String>): GdxAppTokens {
		this.achievementsByScore = value
		return this
	}

	fun withBillingKey(value: String): GdxAppTokens {
		this.billingKey = value
		return this
	}

	fun withRemoveAdsSku(value: String): GdxAppTokens {
		this.removeAdsSku = value
		return this
	}
}
