package com.bitsavior.screens;

import com.badlogic.gdx.Game;
//import com.bitsavior.game.TitleScreen;

public class BitSavior extends Game {
	public ScreenManager manager;

	@Override
	public void create() {
		setScreen(new TitleScreen(this));
		
		//with ScreenManager
		manager = new ScreenManager(this);
		manager.showScreen(Screens.TITLE);
	}

	@Override
	public void dispose() {
		manager.dispose();
	}

}
