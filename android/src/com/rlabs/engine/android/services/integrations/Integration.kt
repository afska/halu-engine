package com.rlabs.engine.android.services.integrations

import android.app.Activity
import android.content.Intent
import android.view.ViewGroup

/**
 * A generic integration with the [app].
 */
abstract class Integration(protected val app : Activity) {
	open fun onCreating(layout: ViewGroup) { }
	open fun onCreated() { }
	open fun onStart() { }
	open fun onStop() { }
	open fun onDestroy() { }
	open fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) { }
}
