package com.bitsavior.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.bitsavior.game.WorldBounds;


/**
 * entry point from Desktop launcher
 * creates a screen manager and loads title screen
 */
public class BitSavior extends Game {
	/**
	 * screenmanager for managing the different screens of the application
	 */
	public ScreenManager manager;

	/**
	 * sets the titlescreen
	 */
	@Override
	public void create() {


		setScreen(new TitleScreen(this));
		
		//with ScreenManager
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
