package com.rlabs.ChangeMe.android

import android.os.Bundle
import com.rlabs.ChangeMe.GameCore
import com.rlabs.ChangeMe.android.services.AndroidMobileServices
import com.rlabs.engine.android.GdxAndroidApp

/**
 * Launcher for Android.
 */
class AndroidLauncher : GdxAndroidApp() {
	override fun onCreate(savedInstanceState: Bundle?) {
		val mobileServices = AndroidMobileServices(this)

		this.initialize(
			R.id.layout,
			GameCore(mobileServices),
			mobileServices.toIntegrations()
		)

		super.onCreate(savedInstanceState)
	}

}
