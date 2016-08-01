package com.rartworks.engine.android.services.integrations.inAppBilling

data class Purchase(
	val sku: String,
	val accept: () -> (Unit),
	val acceptIfDebug: Boolean = false
)
