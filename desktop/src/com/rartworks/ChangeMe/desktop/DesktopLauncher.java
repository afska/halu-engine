package com.rartworks.ChangeMe.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.rartworks.ChangeMe.GameCore;
import com.rartworks.engine.apis.MobileServices;
import com.rartworks.engine.desktop.GooglePlayServicesMock;
import com.rartworks.engine.desktop.InAppBillingServicesMock;

/**
 * Launcher for desktop.
 */
public class DesktopLauncher {
	public static void main (String[] arg) {
		MobileServices mobileServicesMock = new MobileServices(new GooglePlayServicesMock(), new InAppBillingServicesMock());
		GameCore game = new GameCore(mobileServicesMock);
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "ChangeMe";
		config.width = (int) game.getDimensions().getWidth();
		config.height = (int) game.getDimensions().getHeight();
		new LwjglApplication(game, config);
	}
}
