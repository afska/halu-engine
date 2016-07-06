package com.rartworks.engine.android;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.rartworks.engine.android.services.GooglePlay;
import com.rartworks.engine.android.services.InAppBilling;
import com.rartworks.ChangeMe.GamePreferences;
import com.rartworks.ChangeMe.GameCore;
import com.rartworks.ChangeMe.android.R;
import com.rartworks.engine.apis.MobilePreferences;
import com.rartworks.engine.apis.MobileServices;

/**
 * An Android LibGDX app.
 */
public abstract class GdxAndroidApp extends AndroidApplication {
	public MobilePreferences preferences = GamePreferences.INSTANCE;
	public GdxAppTokens tokens;

	private GooglePlay googlePlay;
	private InAppBilling inAppBilling;

	public GdxAndroidApp(GdxAppTokens tokens) {
		super();
		this.tokens = tokens;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.initializeLibgdxGame();

		// Create the layout
		LinearLayout layout = new LinearLayout(this);
		layout.setGravity(Gravity.CENTER_HORIZONTAL);
		layout.setOrientation(LinearLayout.VERTICAL);
		layout.setId(R.id.layout);

		// Create services
		this.googlePlay = new GooglePlay(this);
		this.inAppBilling = new InAppBilling(this);
		MobileServices services = new MobileServices(this.googlePlay, this.inAppBilling);

		// Create and setup the libgdx game and view
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		View gameView = initializeForView(new GameCore(services), config);

		// Add the libgdx view
		LinearLayout.LayoutParams gameParams = this.createLinearLayoutParams();
		gameParams.weight = 1;
		layout.addView(gameView, gameParams);

		// If the user doesn't have the full version
		if (!this.preferences.hasFullVersion()) {
			// Create and setup the AdMob view
			AdView adView = new AdView(this);
			adView.setAdUnitId(this.tokens.adsId);
			adView.setAdSize(AdSize.SMART_BANNER);

			// Add the AdMob view
			layout.addView(adView, this.createLinearLayoutParams());

			// Load the ads
			adView.loadAd(new AdRequest.Builder().build());
		}

		// Hook it all up
		setContentView(layout);

		// Initialize services
		this.googlePlay.initialize();
		this.inAppBilling.initialize();
	}

	/**
	 * Removes the ads.
	 */
	public void removeAds() {
		Gdx.app.log("IAB", "Removing ads...");
		this.preferences.setFullVersion();

		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				LinearLayout layout = (LinearLayout) findViewById(R.id.layout);
				if (layout.getChildCount() == 2)
					layout.removeViewAt(1);
			}
		});
	}

	/**
	 * Displays a toast with an error.
	 */
	public void showError(String text) {
		Toast
			.makeText(getContext(), text, Toast.LENGTH_LONG)
			.show();
	}

	/**
	 * Returns if the application is in debug mode.
	 */
	public boolean isDebuggable() {
		boolean debuggable = false;

		PackageManager pm = this.getPackageManager();
		try {
			ApplicationInfo appInfo = pm.getApplicationInfo(this.getPackageName(), 0);
			debuggable = (0 != (appInfo.flags & ApplicationInfo.FLAG_DEBUGGABLE));
		} catch (PackageManager.NameNotFoundException e) {
			// debuggable variable will remain false
		}

		return debuggable;
	}

	/**
	 * Does the stuff that this.initialize() would do for you.
	 */
	private void initializeLibgdxGame() {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
	}

	/**
	 * Creates the layout params for the views.
	 */
	private LinearLayout.LayoutParams createLinearLayoutParams() {
		return new LinearLayout.LayoutParams(
			LinearLayout.LayoutParams.WRAP_CONTENT,
			LinearLayout.LayoutParams.WRAP_CONTENT
		);
	}

	// -------
	// Events:
	// -------

	@Override
	protected void onStart() {
		super.onStart();
		this.googlePlay.onStart();
	}

	@Override
	protected void onStop() {
		super.onStop();
		this.googlePlay.onStop();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		this.inAppBilling.onDestroy();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		this.googlePlay.onActivityResult(requestCode, resultCode, data);
		this.inAppBilling.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);

		if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT)
			// dafaq did just happened? O_o
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
	}
}
