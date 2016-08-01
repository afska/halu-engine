package com.rartworks.engine.android.utils

import android.app.Activity
import android.content.pm.ActivityInfo
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.widget.LinearLayout
import android.widget.Toast

/**
 * Displays a toast with an error.
 */
fun Activity.showError(text: String) {
	Toast.makeText(this, text, Toast.LENGTH_LONG).show()
}

/**
 * Creates the layout params for the views.
 */
fun Activity.createLinearLayoutParams(): LinearLayout.LayoutParams {
	return LinearLayout.LayoutParams(
		LinearLayout.LayoutParams.WRAP_CONTENT,
		LinearLayout.LayoutParams.WRAP_CONTENT)
}

/**
 * Forces the landscape display mode.
 */
fun Activity.forceLandscape(config: Configuration) {
	if (config.orientation == Configuration.ORIENTATION_PORTRAIT)
		// dafaq did just happened? O_o
		this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
}

/**
 * Returns if the application is in debug mode.
 */
val Activity.isDebuggable: Boolean
	get() {
		val pm = this.packageManager

		try {
			val appInfo = pm.getApplicationInfo(this.packageName, 0)
			return appInfo.flags != 0  and ApplicationInfo.FLAG_DEBUGGABLE
		} catch (e: PackageManager.NameNotFoundException) {
			return false
		}
	}
