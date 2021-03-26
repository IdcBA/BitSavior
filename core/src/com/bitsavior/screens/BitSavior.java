package com.bitsavior.screens;

import com.badlogic.gdx.Game;

/**
 * entry point from Desktop launcher
 * creates a screen manager and loads title screen
 */
public class BitSavior extends Game {
	/**
	 * ScreenManager for managing the different screens of the application
	 */
	public ScreenManager manager;

	/**
	 * creates the ScreenManager
	 */
	@Override
	public void create() {
		
		manager = new ScreenManager(this);
		manager.showScreen(Screens.TITLE);
	}
	
	/**
	 * Disposes the manager(all screens) and exits the game
	 * <p> called when ExitButton(TitleScreen) is pressed
	 */
	@Override
	public void dispose() {
		manager.dispose();
	}

}
