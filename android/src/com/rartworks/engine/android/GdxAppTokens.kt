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

	fun setAdsId(value: String): GdxAppTokens {
		this.adsId = value
		return this
	}

	fun setPlayStoreLink(value: String): GdxAppTokens {
		this.playStoreLink = value
		return this
	}

	fun setAchievementsByScore(value: Map<Int, String>): GdxAppTokens {
		this.achievementsByScore = value
		return this
	}

	fun setBillingKey(value: String): GdxAppTokens {
		this.billingKey = value
		return this
	}

	fun setRemoveAdsSku(value: String): GdxAppTokens {
		this.removeAdsSku = value
		return this
	}
}
