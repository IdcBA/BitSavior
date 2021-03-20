package com.bitsavior.screens;

import com.badlogic.gdx.Game;
//import com.bitsavior.game.TitleScreen;
import com.badlogic.gdx.Gdx;

public class BitSavior extends Game {
	public ScreenManager manager;

	@Override
	public void create() {
		setScreen(new TitleScreen(this));
		
		//with ScreenManager
		manager = new ScreenManager(this);
		manager.showScreen(Screens.TITLE);
	}
	
	/*
	 * Disposes the manager(all screens) and exits the game
	 * <p> called when ExitButton(TitleScreen) is pressed
	 */
	@Override
	public void dispose() {
		manager.dispose();
	}

}
