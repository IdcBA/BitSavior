package com.bitsavior.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

public class BitSavior extends ApplicationAdapter
{

	Game game;
	ScreenMainMenu MainMenu;
	int currentScreen = 0;

	@Override
	public void create () {
		MainMenu = new ScreenMainMenu();
		MainMenu.create();
		game = new Game();
		game.create();
	}

	@Override
	public void render () {
		handlePlayerInput();
		if (currentScreen==0) {
			MainMenu.render();
		}
		if (currentScreen==1) {
			game.update();
			game.render();
		}
		
	}
	
	@Override
	public void dispose () {
		MainMenu.dispose();
		game.dispose();
	}
	
	/**
	 * manages the player input;
	 * sets currentScreen
	 */
	private void handlePlayerInput() {
		//MainMenu
		if (Gdx.input.isKeyPressed(Keys.P)) {
			currentScreen = 0;
		}
		//Level 1
		if (Gdx.input.isKeyPressed(Keys.NUM_1)) {
			currentScreen = 1;
		}
		
	}
}
