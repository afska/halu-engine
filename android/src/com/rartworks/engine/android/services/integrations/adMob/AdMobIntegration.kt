package com.rartworks.engine.android.services.integrations.adMob

import android.app.Activity
import android.view.ViewGroup
import com.badlogic.gdx.Gdx
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.rartworks.engine.android.services.integrations.Integration
import com.rartworks.engine.android.utils.createLinearLayoutParams

/**
 * Manages the integration with AdMob.
 */
class AdMobIntegration(app: Activity, private val ids: AdMobIds, private val settings: AdMobSettings) : Integration(app) {
	/**
	 * Removes the ads.
	 */
	fun removeAds() {
		Gdx.app.log("[!] AdMob", "Removing ads...")

		this.settings.disableAds()
		this.app.runOnUiThread {
			val layout = this.app.findViewById(this.ids.layoutId) as ViewGroup
			if (layout.childCount == 2)
				layout.removeViewAt(1)
		}
	}

	// -------
	// Events:
	// -------

	override fun onCreate(layout: ViewGroup) {
		// If the user doesn't have the full version
		if (this.settings.adsEnabled()) {
			Gdx.app.log("[!] AdMob", "Ads enabled: Adding ads...")

			// Create and setup the AdMob view
			val adView = AdView(this.app)
			adView.adUnitId = this.ids.adsId
			adView.adSize = AdSize.SMART_BANNER

			// Add the AdMob view
			layout.addView(adView, this.app.createLinearLayoutParams())

			// Load the ads
			adView.loadAd(AdRequest.Builder().build())
		}
	}
}

data class AdMobIds(
	val layoutId: Int,
	val adsId: String
)

data class AdMobSettings(
	val adsEnabled: () -> (Boolean),
	val disableAds: () -> (Unit)
)
