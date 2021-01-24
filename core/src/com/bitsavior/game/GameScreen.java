package com.bitsavior.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;

public class GameScreen extends ScreenAdapter {

	// private Members
	BitSavior game;
	private World world;

	// public methods
	/**
	 * Constructor:
	 * 
	 */
	public GameScreen(BitSavior game)
	{
		this.game = game;
		world = new World();    // creating the World
		world.create();
		System.out.println("create");
	}


	@Override
	public void show(){
		Gdx.input.setInputProcessor(new InputAdapter() {
			@Override
			public boolean keyDown(int keyCode) {
				if (keyCode == Input.Keys.ESCAPE) {
					dispose();
					game.setScreen(new TitleScreen(game));
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
