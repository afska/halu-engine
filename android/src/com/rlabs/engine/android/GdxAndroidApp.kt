package com.rlabs.engine.android

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.Gravity
import android.view.Window
import android.view.WindowManager
import android.widget.LinearLayout
import com.badlogic.gdx.ApplicationListener
import com.badlogic.gdx.backends.android.AndroidApplication
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration
import com.rlabs.engine.android.services.integrations.Integration
import com.rlabs.engine.android.utils.createLinearLayoutParams
import com.rlabs.engine.android.utils.forceLandscape

/**
 * An Android LibGDX app.
 */
abstract class GdxAndroidApp() : AndroidApplication() {
	private var layoutId = 0
	private lateinit var game: ApplicationListener
	private lateinit var integrations: List<Integration>

	/**
	 * Stores the needed data for the game.
	 */
	fun initialize(layoutId: Int, game: ApplicationListener, integrations: List<Integration>) {
		this.layoutId = layoutId
		this.game = game
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

		// Create and setup the libgdx game and view
		val config = AndroidApplicationConfiguration()
		val gameView = this.initializeForView(this.game, config)

		// Add the libgdx view
		val gameParams = this.createLinearLayoutParams()
		gameParams.weight = 1f
		layout.addView(gameView, gameParams)

		// Share the layout with the integrations
		this.integrations.forEach { it.onCreating(layout) }

		// Hook it all up
		this.setContentView(layout)

		// Initialize services
		this.integrations.forEach { it.onCreated() }
	}

	/**
	 * Does the stuff that this.initialize() would do for you.
	 */
	private fun initializeLibgdxGame() {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE)
		this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
		this.window.clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN)
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

	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
		super.onActivityResult(requestCode, resultCode, data)
		this.integrations.forEach { it.onActivityResult(requestCode, resultCode, data) }
	}

	override fun onConfigurationChanged(newConfig: Configuration) {
		super.onConfigurationChanged(newConfig)
		this.forceLandscape(newConfig)
	}
}
