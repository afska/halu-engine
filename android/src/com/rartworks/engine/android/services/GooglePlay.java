package com.rartworks.engine.android.services;

import android.content.Intent;
import android.net.Uri;

import com.badlogic.gdx.Gdx;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.GamesActivityResultCodes;
import com.google.example.games.basegameutils.GameHelper;
import com.rartworks.engine.android.GdxAndroidApp;
import com.rartworks.ChangeMe.android.R;
import com.rartworks.engine.apis.GooglePlayServices;

/**
 * Manages the integration with Google Play.
 */
public class GooglePlay implements GooglePlayServices {
	private GdxAndroidApp app;
	public GameHelper gameHelper;

	private final static int requestCode = 1;

	public GooglePlay(GdxAndroidApp app) {
		this.app = app;
	}

	/**
	 * Creates a Game Helper, used to talk to the Google Play Services.
	 */
	public void initialize() {
		this.gameHelper = new GameHelper(this.app, GameHelper.CLIENT_GAMES);
		this.gameHelper.enableDebugLog(false);
		this.gameHelper.setMaxAutoSignInAttempts(
			(this.app.preferences.hasAlreadyPlayed() && !this.app.preferences.isSignedIn())
				? 0
				: 1
		);

		this.gameHelper.setup(new GameHelper.GameHelperListener() {
			@Override
			public void onSignInFailed() {
				Gdx.app.log("GooglePlayGames", "Sign in failed.");
				gameHelper.setMaxAutoSignInAttempts(0);

				GameHelper.SignInFailureReason reason = gameHelper.getSignInError();
				if (reason != null) app.showError(reason.toString());
			}

			@Override
			public void onSignInSucceeded() {
				Gdx.app.log("GooglePlayGames", "Sign in success.");
				gameHelper.setMaxAutoSignInAttempts(0);
			}
		});
	}

	/**
	 * Starts the login with Google Play Services.
	 */
	@Override
	public void signIn() {
		this.app.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				gameHelper.beginUserInitiatedSignIn();
			}
		});
	}

	/**
	 * Starts the logout with Google Play Services.
	 */
	@Override
	public void signOut() {
		this.app.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				gameHelper.signOut();
			}
		});
	}

	/**
	 * Takes the user to the "rate my game" link.
	 */
	@Override
	public void rateGame() {
		String link = this.app.tokens.playStoreLink;
		this.app.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(link)));
	}

	/**
	 * Submits a score to the leaderboard.
	 */
	@Override
	public void submitScore(long score) {
		if (this.isSignedIn())
			Games.Leaderboards.submitScore(
				this.gameHelper.getApiClient(),
				this.app.getString(R.string.leaderboard_high_scores),
				score
			);
	}

	/**
	 * Shows the leaderboard.
	 */
	@Override
	public void showScores() {
		if (this.isSignedIn())
			this.app.startActivityForResult(
				Games.Leaderboards.getLeaderboardIntent(
					this.gameHelper.getApiClient(),
					this.app.getString(R.string.leaderboard_high_scores)
				),
				requestCode
			);
		else this.signIn();
	}

	/**
	 * Unlocks an achievement.
	 */
	@Override
	public void unlockAchievementByScore(int score) {
		String achievementId = this.app.tokens.achievementsByScore.get(score);
		if (achievementId != null && this.isSignedIn())
			Games.Achievements.unlock(this.gameHelper.getApiClient(), achievementId);
	}

	/**
	 * Returns if the user is logged in with Google Play Services.
	 */
	@Override
	public boolean isSignedIn() {
		return this.gameHelper.isSignedIn();
	}

	// -------
	// Events:
	// -------

	public void onStart() {
		this.gameHelper.onStart(this.app);
	}

	public void onStop() {
		this.gameHelper.onStop();
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		this.gameHelper.onActivityResult(requestCode, resultCode, data);
		if (resultCode == GamesActivityResultCodes.RESULT_RECONNECT_REQUIRED)
			this.gameHelper.disconnect();
	}
}
