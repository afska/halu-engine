package com.rartworks.engine.android.services

import android.content.Intent

import com.badlogic.gdx.Gdx
import com.rartworks.engine.android.GdxAndroidApp
import com.rartworks.engine.android.services.util.IabHelper
import com.rartworks.engine.android.services.util.IabResult
import com.rartworks.engine.android.services.util.Inventory
import com.rartworks.engine.android.services.util.Purchase
import com.rartworks.engine.apis.InAppBillingServices

/**
 * Manages the integration with In App Billing.
 */
class InAppBilling(private val app: GdxAndroidApp) : InAppBillingServices {
	var iabHelper: IabHelper? = null

	companion object {
		private val RC_PURCHASE = 10001
	}

	/**
	 * Creates the In App Billing helper.
	 */
	fun initialize() {
		this.iabHelper = IabHelper(this.app, this.app.tokens.billingKey)

		this.iabHelper!!.startSetup { result ->
			if (!result.isSuccess)
				Gdx.app.log("IAB", "Problem setting up In-app Billing: " + result)
			else {
				Gdx.app.log("IAB", "In-app Billing is fully set up: " + result)

				checkPayment()
			}
		}
	}

	/**
	 * Starts the "remove ads" purchase.
	 */
	override fun startRemoveAdsPurchase() {
		if (this.app.isDebuggable) {
			this.app.removeAds()
			return
		}

		val purchaseFinishedListener = IabHelper.OnIabPurchaseFinishedListener { result, purchase ->
			if (purchase == null || iabHelper == null) return@OnIabPurchaseFinishedListener

			if (result.isFailure) {
				Gdx.app.log("IAB", "Error purchasing: " + result.message)
				app.showError("PurchaseFailureReason(" + result.message + ")")
				return@OnIabPurchaseFinishedListener
			}

			Gdx.app.log("IAB", "Purchase finished: " + purchase.sku)

			if (purchase.sku == app.tokens.removeAdsSku)
				app.removeAds()
		}

		this.iabHelper!!.flagEndAsync() // hack for: Can't start async operation (refresh inventory) because another async operation(launchPurchaseFlow) is in progress
		this.iabHelper!!.launchPurchaseFlow(
			this.app, app.tokens.removeAdsSku, RC_PURCHASE,
			purchaseFinishedListener, "HANDLE_PAYLOADS"
		)
	}

	/**
	 * Gets the inventory and removes the ads if the user purchased the game.
	 */
	private fun checkPayment() {
		this.iabHelper!!.queryInventoryAsync(false, IabHelper.QueryInventoryFinishedListener { result, inventory ->
			if (iabHelper == null) return@QueryInventoryFinishedListener

			if (result.isFailure) {
				Gdx.app.log("IAB", "Can't get the inventory: " + result.message)
				return@QueryInventoryFinishedListener
			}

			Gdx.app.log("IAB", "The inventory is here!")

			val removeAdPurchase = inventory.getPurchase(app.tokens.removeAdsSku)
			if (removeAdPurchase != null)
				app.removeAds()
		})
	}

	// -------
	// Events:
	// -------

	fun onDestroy() {
		try {
			if (this.iabHelper != null) this.iabHelper!!.dispose()
		} catch (e: Exception) {
			// yes, the IabHelper has bugs
		}

		this.iabHelper = null
	}

	fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
		if (this.iabHelper != null)
			this.iabHelper!!.handleActivityResult(requestCode, resultCode, data)
	}
}
