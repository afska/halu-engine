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
	/*GdxAppTokens() // TODO: GooglePlay Integrations
		.withPlayStoreLink("ChangeThisToken")
		.withAchievementsByScore(AndroidLauncher.achievements)
	) {*/

	override fun onCreate(savedInstanceState: Bundle?) {
		val mobileServices = AndroidMobileServices(this)

		this.initialize(
			R.id.layout,
			GameCore(mobileServices as MobileServices),
			mobileServices.toIntegrations()
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
