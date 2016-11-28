package com.rartworks.ChangeMe.android.services

import android.app.Activity
import com.rartworks.ChangeMe.GamePreferences
import com.rartworks.ChangeMe.android.R
import com.rartworks.engine.apis.MobileServices
import com.rartworks.engine.android.services.InAppBillingIntegration
import com.rartworks.engine.android.services.integrations.Integration
import com.rartworks.engine.android.services.integrations.adMob.AdMobIntegration
import com.rartworks.engine.android.services.integrations.adMob.AdMobSettings
import com.rartworks.engine.android.services.integrations.googlePlay.GooglePlayIntegration
import com.rartworks.engine.android.services.integrations.googlePlay.GooglePlaySettings
import com.rartworks.engine.android.services.integrations.googlePlay.LeaderboardId
import com.rartworks.engine.android.services.integrations.googlePlay.LeaderboardIntegration
import com.rartworks.engine.android.services.integrations.inAppBilling.Purchase
import java.util.*

/**
 * Wraps the services as an interface between the core and the Android project.
 */
class AndroidMobileServices(app: Activity) : MobileServices {
	/**
	 * Converts all the integrations to a [List].
	 */
	fun toIntegrations() = listOf<Integration>(
		/*this.adMob,
		this.inAppBilling,
		this.googlePlay,
		this.leaderboard*/
	)

	companion object {
		private val achievements: MutableMap<Int, String>

		init {
			achievements = HashMap<Int, String>()
			achievements.put(100, "ChangeThisToken")
			achievements.put(200, "ChangeThisToken")
			achievements.put(300, "ChangeThisToken")
			achievements.put(400, "ChangeThisToken")
			achievements.put(500, "ChangeThisToken")
		}
	}

	val adMob = AdMobIntegration(app,
		AdMobSettings(
			layoutId = R.id.layout,
			adsId = app.getString(R.string.ads_id),
			adsEnabled = { !GamePreferences.withoutAds },
			disableAds = { GamePreferences.withoutAds = true }
		)
	)

	override val inAppBilling = InAppBillingIntegration(app,
		app.getString(R.string.billing_key),
		listOf(
			Purchase(
				sku = app.getString(R.string.sku_remove_ads),
				accept = { this.adMob.removeAds() }
			)
		)
	)

	override val googlePlay = GooglePlayIntegration(app,
		GooglePlaySettings(
			playStoreLink = app.getString(R.string.playstore_link),
			achievementsByScore = achievements,
			preferences = GamePreferences
		)
	)

	override val leaderboard = LeaderboardIntegration(app,
		this.googlePlay,
		listOf(
			LeaderboardId("high_scores", app.getString(R.string.leaderboard_high_scores))
		)
	)
}
