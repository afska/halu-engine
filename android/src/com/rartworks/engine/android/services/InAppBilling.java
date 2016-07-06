package com.rartworks.engine.android.services;

import android.content.Intent;

import com.badlogic.gdx.Gdx;
import com.rartworks.engine.android.GdxAndroidApp;
import com.rartworks.engine.android.services.util.IabHelper;
import com.rartworks.engine.android.services.util.IabResult;
import com.rartworks.engine.android.services.util.Inventory;
import com.rartworks.engine.android.services.util.Purchase;
import com.rartworks.engine.apis.InAppBillingServices;

/**
 * Manages the integration with In App Billing.
 */
public class InAppBilling implements InAppBillingServices {
	private GdxAndroidApp app;
	public IabHelper iabHelper;

	private final static int REQUEST_CODE_FOR_PURCHASES = 10001;

	public InAppBilling(GdxAndroidApp app) {
		this.app = app;
	}

	/**
	 * Creates the In App Billing helper.
	 */
	public void initialize() {
		this.iabHelper = new IabHelper(this.app, this.app.tokens.billingKey);

		this.iabHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
			public void onIabSetupFinished(IabResult result) {
				if (!result.isSuccess())
					Gdx.app.log("IAB", "Problem setting up In-app Billing: " + result);
				else {
					Gdx.app.log("IAB", "In-app Billing is fully set up: " + result);

					checkPayment();
				}
			}
		});
	}

	/**
	 * Starts the "remove ads" purchase.
	 */
	@Override
	public void startRemoveAdsPurchase() {
		if (this.app.isDebuggable()) {
			this.app.removeAds();
			return;
		}

		IabHelper.OnIabPurchaseFinishedListener purchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
			public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
				if (purchase == null || iabHelper == null) return;

				if (result.isFailure()) {
					Gdx.app.log("IAB", "Error purchasing: " + result.getMessage());
					app.showError("PurchaseFailureReason(" + result.getMessage() + ")");
					return;
				}

				Gdx.app.log("IAB", "Purchase finished: " + purchase.getSku());

				if (purchase.getSku().equals(app.tokens.removeAdsSku))
					app.removeAds();
			}
		};

		this.iabHelper.flagEndAsync(); // hack for: Can't start async operation (refresh inventory) because another async operation(launchPurchaseFlow) is in progress
		this.iabHelper.launchPurchaseFlow(
			this.app, app.tokens.removeAdsSku, REQUEST_CODE_FOR_PURCHASES,
			purchaseFinishedListener, "HANDLE_PAYLOADS"
		);
	}

	/**
	 * Gets the inventory and removes the ads if the user purchased the game.
	 */
	private void checkPayment() {
		this.iabHelper.queryInventoryAsync(false, new IabHelper.QueryInventoryFinishedListener() {
			public void onQueryInventoryFinished(IabResult result, Inventory inventory) {
				if (iabHelper == null) return;

				if (result.isFailure()) {
					Gdx.app.log("IAB", "Can't get the inventory: " + result.getMessage());
					return;
				}

				Gdx.app.log("IAB", "The inventory is here!");

				Purchase removeAdPurchase = inventory.getPurchase(app.tokens.removeAdsSku);
				if (removeAdPurchase != null)
					app.removeAds();
			}
		});
	}

	// -------
	// Events:
	// -------

	public void onDestroy() {
		try {
			if (this.iabHelper != null) this.iabHelper.dispose();
		} catch (Exception e) {
			// yes, the IabHelper has bugs
		}
		this.iabHelper = null;
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (this.iabHelper != null)
			this.iabHelper.handleActivityResult(requestCode, resultCode, data);
	}
}
