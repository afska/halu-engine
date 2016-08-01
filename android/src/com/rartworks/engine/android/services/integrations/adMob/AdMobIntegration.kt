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
class AdMobIntegration(app: Activity, private val settings: AdMobSettings) : Integration(app) {
	/**
	 * Removes the ads.
	 */
	fun removeAds() {
		Gdx.app.log("[!] AdMob", "Removing ads...")

		this.settings.disableAds()
		this.app.runOnUiThread {
			val layout = this.app.findViewById(this.settings.layoutId) as ViewGroup
			if (layout.childCount == 2)
				layout.removeViewAt(1)
		}
	}

	// -------
	// Events:
	// -------

	/**
	 * Creates the [AdView] in the [layout] if the ads are enabled.
	 */
	override fun onCreating(layout: ViewGroup) {
		if (this.settings.adsEnabled()) {
			Gdx.app.log("[!] AdMob", "Ads enabled: Adding ads...")

			// Create and setup the AdMob view
			val adView = AdView(this.app)
			adView.adUnitId = this.settings.adsId
			adView.adSize = AdSize.SMART_BANNER

			// Add the AdMob view
			layout.addView(adView, this.app.createLinearLayoutParams())

			// Load the ads
			adView.loadAd(AdRequest.Builder().build())
		}
	}
}

data class AdMobSettings(
	val layoutId: Int,
	val adsId: String,
	val adsEnabled: () -> (Boolean),
	val disableAds: () -> (Unit)
)
