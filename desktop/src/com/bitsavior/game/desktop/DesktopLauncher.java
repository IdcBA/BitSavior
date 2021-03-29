package com.bitsavior.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.bitsavior.screens.BitSavior;

/**
 * entry point for the application
 */
public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1280;
		config.height = 960;
		config.foregroundFPS = 60;
		config.fullscreen = true;
		config.resizable = false;

		new LwjglApplication(new BitSavior(), config);
	}
}