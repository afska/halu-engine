package com.rartworks.ChangeMe.android;

import com.rartworks.engine.android.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Launcher for Android.
 */
public class AndroidLauncher extends GdxAndroidApp {
	private static final Map<Integer, String> achievements; static {
		achievements = new HashMap<>();
		achievements.put(100, "ChangeThisToken");
		achievements.put(200, "ChangeThisToken");
	}

	public AndroidLauncher() {
		super(
			new GdxAppTokens()
				.withAdsId("ChangeThisToken")
				.withPlayStoreLink("ChangeThisToken")
				.withAchievementsByScore(achievements)
				.withBillingKey("ChangeThisToken")
				.withRemoveAdsSku("remove_ads")
		);
	}
}
