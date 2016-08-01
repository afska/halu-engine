package com.rartworks.engine.android.services.integrations.inAppBilling

/**
 * An in-app purchase, with an [sku].
 */
data class Purchase(
	val sku: String,
	val accept: () -> (Unit),
	val acceptIfDebug: Boolean = false
)
