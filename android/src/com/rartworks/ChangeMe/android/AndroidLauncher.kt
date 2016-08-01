package com.rartworks.ChangeMe.android

import android.os.Bundle
import com.rartworks.ChangeMe.GameCore
import com.rartworks.ChangeMe.android.services.AndroidMobileServices
import com.rartworks.ChangeMe.apis.MobileServices
import com.rartworks.engine.android.GdxAndroidApp
import java.util.*

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
