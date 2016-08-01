package com.rartworks.engine.android.services

import android.app.Activity
import android.content.Intent
import com.badlogic.gdx.Gdx
import com.rartworks.engine.android.services.integrations.Integration
import com.rartworks.engine.android.services.integrations.inAppBilling.Purchase
import com.rartworks.engine.android.services.util.IabHelper
import com.rartworks.engine.android.utils.isDebuggable
import com.rartworks.engine.android.utils.showError
import com.rartworks.engine.apis.InAppBillingServices
import java.util.*

/**
 * Manages the integration with In App Billing.
 */
class InAppBillingIntegration(app: Activity, private val billingKey: String, private val availablePurchases: List<Purchase>) : Integration(app), InAppBillingServices {
	var iabHelper: IabHelper? = null

	companion object {
		private val RC_PURCHASE = 10001
	}

	/**
	 * Starts a [Purchase] by [sku].
	 */
	override fun startPurchase(sku: String) {
		val requestedPurchase = this.findAvailablePurchaseBySku(sku);

		if (requestedPurchase.acceptIfDebug && this.app.isDebuggable)
			return requestedPurchase.accept();

		val purchaseFinishedListener = IabHelper.OnIabPurchaseFinishedListener { result, purchase ->
			if (purchase == null || this.iabHelper == null) return@OnIabPurchaseFinishedListener

			if (result.isFailure) {
				Gdx.app.log("[!] InAppBilling", "Error purchasing: " + result.message)
				this.app.showError("PurchaseFailureReason(" + result.message + ")")
				return@OnIabPurchaseFinishedListener
			}

			Gdx.app.log("[!] InAppBilling", "Purchase finished: " + purchase.sku)

			if (purchase.sku == requestedPurchase.sku)
				requestedPurchase.accept()
		}

		this.iabHelper!!.flagEndAsync() // hack for: Can't start async operation (refresh inventory) because another async operation(launchPurchaseFlow) is in progress
		this.iabHelper!!.launchPurchaseFlow(
			this.app, requestedPurchase.sku, RC_PURCHASE,
			purchaseFinishedListener, "HANDLE_PAYLOADS"
		)
	}

	/**
	 * Gets the inventory and accept the proper [Purchase]s.
	 */
	private fun checkPayments() {
		this.iabHelper!!.queryInventoryAsync(false, IabHelper.QueryInventoryFinishedListener { result, inventory ->
			if (this.iabHelper == null) return@QueryInventoryFinishedListener

			if (result.isFailure) {
				Gdx.app.log("[!] InAppBilling", "Can't get the inventory: " + result.message)
				return@QueryInventoryFinishedListener
			}

			Gdx.app.log("[!] InAppBilling", "The inventory is here!")

			this.availablePurchases
				.filter { inventory.getPurchase(it.sku) != null }
				.forEach { it.accept() }
		})
	}

	/**
	 * Returns an available [Purchase] by its [sku].
	 */
	private fun findAvailablePurchaseBySku(sku: String): Purchase {
		try {
			return this.availablePurchases.single { it.sku == sku }
		} catch(e: NoSuchElementException) {
			throw RuntimeException("The sku ${sku} was not found.")
		}
	}

	// -------
	// Events:
	// -------

	/**
	 * Creates the In App Billing helper.
	 */
	override fun onCreated() {
		this.iabHelper = IabHelper(this.app, this.billingKey)

		this.iabHelper!!.startSetup { result ->
			if (!result.isSuccess)
				Gdx.app.log("[!] InAppBilling", "Problem setting up In-app Billing: " + result)
			else {
				Gdx.app.log("[!] InAppBilling", "In-app Billing is fully set up: " + result)

				this.checkPayments()
			}
		}
	}

	override fun onDestroy() {
		try {
			if (this.iabHelper != null) this.iabHelper!!.dispose()
		} catch (e: Exception) {
			// yes, the IabHelper has bugs
		}

		this.iabHelper = null
	}

	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
		if (this.iabHelper != null)
			this.iabHelper!!.handleActivityResult(requestCode, resultCode, data)
	}
}
