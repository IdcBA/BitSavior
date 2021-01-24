package com.bitsavior.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;

public class GameScreen extends ScreenAdapter {

	// private Members
	BitSavior game;
	TitleScreen tScreen;
	private World world;
	
	/**
     * 0: no messages; 1: send messages
     */
    int aTestMode=1;

	// public methods
	/**
	 * Constructor:
	 * @param BitSavior game
	 * @param TitelScreen to "jump back"
	 */
	public GameScreen(BitSavior game, TitleScreen tScreen)
	{
		this.game = game;
		if(tScreen==null) System.out.println("tScreen beim erstellen von GameScreen leer");
		this.tScreen = tScreen;
		world = new World();    // creating the World
		world.create();
		if(aTestMode==1) System.out.println("creates new world");
	}


	@Override
	public void show(){
		Gdx.input.setInputProcessor(new InputAdapter() {
			@Override
			public boolean keyDown(int keyCode) {
				if (keyCode == Input.Keys.ESCAPE) {
					
					if(tScreen==null) if(aTestMode==1) System.out.println("tScreen leer");
					game.setScreen(tScreen);
					
				}
				return true;
			}
		});


	}


	@Override
	public void render(float Delta)
	{
		world.update(Delta);
		world.render();
	}

	@Override
	public void dispose()
	{
		world.dispose();
	}
	// private methods
	private void processEvents() {}
}
