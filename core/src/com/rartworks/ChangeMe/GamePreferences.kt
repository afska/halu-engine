package com.rartworks.ChangeMe

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Preferences
import com.rartworks.engine.apis.*

/**
 * The stored preferences of the game.
 */
object GamePreferences : MobilePreferences {
	private val ALREADY_PLAYED = "alreadyPlayed"
	private val SIGNED_IN = "signedIn"
	private val FULL_VERSION = "fullVersion"

	val alreadyPlayed: Boolean get() = this.preferences.getBoolean(ALREADY_PLAYED)

	var signedIn: Boolean
		get() = this.preferences.getBoolean(SIGNED_IN)
		set(value) {
			this.change { it.putBoolean(SIGNED_IN, value) }
		}

	var fullVersion: Boolean
		get() = this.preferences.getBoolean(FULL_VERSION)
		set(value) {
			this.change { it.putBoolean(FULL_VERSION, value) }
		}

	private val preferences: Preferences get() = Gdx.app.getPreferences("com.rartworks.ChangeMe.preferences")

	/**
	 * Initializes the settings.
	 */
	fun initialize() {
		val prefs = this.preferences
		val alreadyPlayed = prefs.getBoolean(ALREADY_PLAYED)

		if (!alreadyPlayed) {
			prefs.putBoolean(ALREADY_PLAYED, true)
			prefs.putBoolean(SIGNED_IN, true)
			prefs.putBoolean(FULL_VERSION, false)
			prefs.flush()
		}
	}

	/**
	 * Executes the [changePreferences] function and flush the file.
	 */
	private inline fun change(changePreferences: (Preferences) -> (Unit)) {
		val prefs = this.preferences
		changePreferences(prefs)
		prefs.flush()
	}

	override fun hasAlreadyPlayed() = this.alreadyPlayed
	override fun isSignedIn() = this.signedIn
	override fun hasFullVersion() = this.fullVersion
	override fun setFullVersion() { this.fullVersion = true }
}
