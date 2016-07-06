package com.rartworks.engine.android;

import java.util.Map;

public class GdxAppTokens {
	// Ads
	public String adsId;

	// Google Play
	public String playStoreLink;
	public Map<Integer, String> achievementsByScore;

	// In App Billing
	public String billingKey;
	public String removeAdsSku;

	// --------
	// Setters:
	// --------

	public GdxAppTokens setAdsId(String value) {
		this.adsId = value;
		return this;
	}

	public GdxAppTokens setPlayStoreLink(String value) {
		this.playStoreLink = value;
		return this;
	}

	public GdxAppTokens setAchievementsByScore(Map<Integer, String> value) {
		this.achievementsByScore = value;
		return this;
	}

	public GdxAppTokens setBillingKey(String value) {
		this.billingKey = value;
		return this;
	}

	public GdxAppTokens setRemoveAdsSku(String value) {
		this.removeAdsSku = value;
		return this;
	}
}
