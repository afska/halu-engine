package com.rartworks.engine.desktop;

import com.rartworks.engine.apis.GooglePlayServices;

public class GooglePlayServicesMock implements GooglePlayServices {
	@Override
	public void signIn() {

	}

	@Override
	public void signOut() {

	}

	@Override
	public void rateGame() {

	}

	@Override
	public void submitScore(long score) {

	}

	@Override
	public void showScores() {

	}

	@Override
	public void unlockAchievementByScore(int score) {

	}

	@Override
	public boolean isSignedIn() {
		return false;
	}
}
