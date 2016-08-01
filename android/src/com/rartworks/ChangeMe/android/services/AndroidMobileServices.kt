package com.rartworks.ChangeMe.android.services

import android.app.Activity
import com.rartworks.ChangeMe.GamePreferences
import com.rartworks.ChangeMe.android.R
import com.rartworks.ChangeMe.apis.MobileServices
import com.rartworks.engine.android.services.InAppBillingIntegration
import com.rartworks.engine.android.services.integrations.Integration
import com.rartworks.engine.android.services.integrations.adMob.AdMobIds
import com.rartworks.engine.android.services.integrations.adMob.AdMobIntegration
import com.rartworks.engine.android.services.integrations.adMob.AdMobSettings
import com.rartworks.engine.android.services.integrations.inAppBilling.Purchase

/**
 * Wraps the services as an interface between the core and the Android project.
 */
class AndroidMobileServices(app: Activity) : MobileServices {
	val adMob = AdMobIntegration(app,
		AdMobIds(layoutId = R.id.layout, adsId = app.getString(R.string.ads_id)),
		AdMobSettings(adsEnabled = { !GamePreferences.withoutAds }, disableAds = { GamePreferences.withoutAds = true })
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

	/**
	 * Converts all the integrations to a [List].
	 */
	fun toIntegrations() = listOf<Integration>(
		this.adMob,
		this.inAppBilling
	)
}
