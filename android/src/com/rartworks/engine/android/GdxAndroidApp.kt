package com.rartworks.engine.android

import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.os.Bundle
import android.view.Gravity
import android.view.Window
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.Toast

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.backends.android.AndroidApplication
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.rartworks.engine.android.services.*
import com.rartworks.ChangeMe.*
import com.rartworks.ChangeMe.android.R
import com.rartworks.engine.apis.*

/**
 * An Android LibGDX app.
 */
abstract class GdxAndroidApp(var tokens: GdxAppTokens) : AndroidApplication() {
	var preferences: MobilePreferences = GamePreferences

	private var googlePlay: GooglePlay? = null
	private var inAppBilling: InAppBilling? = null

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		this.initializeLibgdxGame()

		// Create the layout
		val layout = LinearLayout(this)
		layout.setGravity(Gravity.CENTER_HORIZONTAL)
		layout.orientation = LinearLayout.VERTICAL
		layout.id = R.id.layout

		// Create services
		this.googlePlay = GooglePlay(this)
		this.inAppBilling = InAppBilling(this)
		val services = MobileServices(this.googlePlay!!, this.inAppBilling!!)

		// Create and setup the libgdx game and view
		val config = AndroidApplicationConfiguration()
		val gameView = initializeForView(GameCore(services), config)

		// Add the libgdx view
		val gameParams = this.createLinearLayoutParams()
		gameParams.weight = 1f
		layout.addView(gameView, gameParams)

		// If the user doesn't have the full version
		if (!this.preferences.hasFullVersion()) {
			// Create and setup the AdMob view
			val adView = AdView(this)
			adView.adUnitId = this.tokens.adsId
			adView.adSize = AdSize.SMART_BANNER

			// Add the AdMob view
			layout.addView(adView, this.createLinearLayoutParams())

			// Load the ads
			adView.loadAd(AdRequest.Builder().build())
		}

		// Hook it all up
		setContentView(layout)

		// Initialize services
		this.googlePlay!!.initialize()
		this.inAppBilling!!.initialize()
	}

	/**
	 * Removes the ads.
	 */
	fun removeAds() {
		Gdx.app.log("IAB", "Removing ads...")
		this.preferences.setFullVersion()

		runOnUiThread {
			val layout = findViewById(R.id.layout) as LinearLayout
			if (layout.childCount == 2)
				layout.removeViewAt(1)
		}
	}

	/**
	 * Displays a toast with an error.
	 */
	fun showError(text: String) {
		Toast.makeText(context, text, Toast.LENGTH_LONG).show()
	}

	/**
	 * Returns if the application is in debug mode.
	 */
	val isDebuggable: Boolean
		get() {
			val pm = this.packageManager

			try {
				val appInfo = pm.getApplicationInfo(this.packageName, 0)
				return appInfo.flags != 0  and ApplicationInfo.FLAG_DEBUGGABLE
			} catch (e: PackageManager.NameNotFoundException) {
				return false
			}
		}

	/**
	 * Does the stuff that this.initialize() would do for you.
	 */
	private fun initializeLibgdxGame() {
		requestWindowFeature(Window.FEATURE_NO_TITLE)
		window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
		window.clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN)
	}

	/**
	 * Creates the layout params for the views.
	 */
	private fun createLinearLayoutParams(): LinearLayout.LayoutParams {
		return LinearLayout.LayoutParams(
			LinearLayout.LayoutParams.WRAP_CONTENT,
			LinearLayout.LayoutParams.WRAP_CONTENT)
	}

	// -------
	// Events:
	// -------

	override fun onStart() {
		super.onStart()
		this.googlePlay!!.onStart()
	}

	override fun onStop() {
		super.onStop()
		this.googlePlay!!.onStop()
	}

	override fun onDestroy() {
		super.onDestroy()
		this.inAppBilling!!.onDestroy()
	}

	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
		super.onActivityResult(requestCode, resultCode, data)
		this.googlePlay!!.onActivityResult(requestCode, resultCode, data)
		this.inAppBilling!!.onActivityResult(requestCode, resultCode, data)
	}

	override fun onConfigurationChanged(newConfig: Configuration) {
		super.onConfigurationChanged(newConfig)

		if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT)
			// dafaq did just happened? O_o
			requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
	}
}
