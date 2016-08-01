package com.rartworks.engine.android

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.Gravity
import android.view.Window
import android.view.WindowManager
import android.widget.LinearLayout
import com.badlogic.gdx.backends.android.AndroidApplication
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration
import com.rartworks.ChangeMe.GameCore
import com.rartworks.engine.android.services.InAppBillingIntegration
import com.rartworks.engine.android.services.integrations.Integration
import com.rartworks.engine.android.utils.createLinearLayoutParams
import com.rartworks.engine.android.utils.forceLandscape
import com.rartworks.engine.apis.MobileServices

/**
 * An Android LibGDX app.
 */
abstract class GdxAndroidApp() : AndroidApplication() {
	private var layoutId: Int = 0
	private lateinit var integrations: List<Integration>

	/**
	 * Stores the needed data of layout and [integrations].
	 */
	fun initialize(layoutId: Int, integrations: List<Integration>) {
		this.layoutId = layoutId
		this.integrations = integrations
	}

	/**
	 * Initializes the game and the integrations.
	 */
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		this.initializeLibgdxGame()

		// Create the layout
		val layout = LinearLayout(this)
		layout.setGravity(Gravity.CENTER_HORIZONTAL)
		layout.orientation = LinearLayout.VERTICAL
		layout.id = this.layoutId

		// Create services
		// TODO: QUIÉN LE PASA AL JUEGO LAS COSAS?
		this.googlePlay = GooglePlay(this)
		this.inAppBilling = InAppBillingIntegration(this)
		val services = MobileServices(this.googlePlay!!, this.inAppBilling!!)

		// Create and setup the libgdx game and view
		val config = AndroidApplicationConfiguration()
		val gameView = initializeForView(GameCore(services), config)

		// Add the libgdx view
		val gameParams = this.createLinearLayoutParams()
		gameParams.weight = 1f
		layout.addView(gameView, gameParams)

		this.integrations.forEach { it.onCreate(layout) }

		// Hook it all up
		setContentView(layout)

		// Initialize services
		this.integrations.forEach { it.onCreated() }
	}

	/**
	 * Does the stuff that this.initialize() would do for you.
	 */
	private fun initializeLibgdxGame() {
		requestWindowFeature(Window.FEATURE_NO_TITLE)
		window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
		window.clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN)
	}

	// -------
	// Events:
	// -------

	override fun onStart() {
		super.onStart()
		this.integrations.forEach { it.onStart() }
	}

	override fun onStop() {
		super.onStop()
		this.integrations.forEach { it.onStop() }
	}

	override fun onDestroy() {
		super.onDestroy()
		this.integrations.forEach { it.onDestroy() }
	}

	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
		super.onActivityResult(requestCode, resultCode, data)
		this.integrations.forEach { it.onActivityResult(requestCode, resultCode, data) }
	}

	override fun onConfigurationChanged(newConfig: Configuration) {
		super.onConfigurationChanged(newConfig)
		this.forceLandscape(newConfig)
	}
}
