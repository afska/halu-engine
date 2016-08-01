package com.rartworks.ChangeMe.android

import android.os.Bundle
import com.rartworks.ChangeMe.GamePreferences
import com.rartworks.engine.android.GdxAndroidApp
import com.rartworks.engine.android.services.InAppBillingIntegration
import com.rartworks.engine.android.services.integrations.Integration
import com.rartworks.engine.android.services.integrations.adMob.AdMobIds
import com.rartworks.engine.android.services.integrations.adMob.AdMobIntegration
import com.rartworks.engine.android.services.integrations.adMob.AdMobSettings
import com.rartworks.engine.android.services.integrations.inAppBilling.Purchase
import java.util.*

/**
 * Launcher for Android.
 */
class AndroidLauncher : GdxAndroidApp() {
	/*GdxAppTokens() // TODO: GooglePlay Integrations
		.withPlayStoreLink("ChangeThisToken")
		.withAchievementsByScore(AndroidLauncher.achievements)
	) {*/

	override fun onCreate(savedInstanceState: Bundle?) {
		val adMobIntegration = AdMobIntegration(this,
			AdMobIds(layoutId = R.id.layout, adsId = this.getString(R.string.ads_id)),
			AdMobSettings(adsEnabled = { !GamePreferences.withoutAds }, disableAds = { GamePreferences.withoutAds = true })
		)

		val inAppBillingIntegration = InAppBillingIntegration(this,
			this.getString(R.string.billing_key),
			listOf(
				Purchase(
					sku = this.getString(R.string.sku_remove_ads),
					accept = { adMobIntegration.removeAds() }
				)
			)
		)

		this.initialize(
			R.id.layout,
			listOf<Integration>(adMobIntegration, inAppBillingIntegration)
		)

		super.onCreate(savedInstanceState)
	}
    companion object {
        private val achievements: MutableMap<Int, String>

        init {
            achievements = HashMap<Int, String>()
            achievements.put(100, "ChangeThisToken")
            achievements.put(200, "ChangeThisToken")
        }
    }
}
