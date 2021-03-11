package com.bitsavior.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;

public class GameScreen extends ScreenAdapter {

	// private Members
	private BitSavior game;
	private World world;

	
	/**
     * 0: no messages; 1: send messages
     */
    int aTestMode=0;

	// public methods
	/**
	 * Constructor:
	 * @param game game
	 * @param tScreen to "jump back"
	 */
	public GameScreen(BitSavior game, int level)
	{
		this.game = game;
		//if(tScreen==null) System.out.println("tScreen beim erstellen von GameScreen leer");
		//this.tScreen = tScreen;
		world = new World();    // creating the World
		world.create();
		if(aTestMode==1) System.out.println("creates new world");
	}


	@Override
	public void show()
	{
		Gdx.input.setInputProcessor(new InputAdapter() {
			@Override
			public boolean keyDown(int keyCode) {
				if (keyCode == Input.Keys.ESCAPE) {
					
					game.manager.showScreen(Screens.TITLE);
					
				}
				return true;
			}
		});
	}


	@Override
	public void render(float Delta)
	{
		if(!world.update(Delta))
			game.manager.showScreen(Screens.TITLE);
		world.render(Delta);
	}

	@Override
	public void dispose()
	{
		world.dispose();
	}
	
	@Override
	public void hide()
	{
		Gdx.input.setInputProcessor(null);
	}
	
	// private methods
	private void processEvents() {}
}
