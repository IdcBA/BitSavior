package com.bitsavior.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.bitsavior.game.GameState;
import com.bitsavior.game.World;

public class GameScreen extends ScreenAdapter {

	//variables for testing
	/** ... */
	
	// private Members
	private BitSavior game;
	private World world;
	/** not needed at the moment because there is only one level */
	private int gameLevel = 0;

	// public methods
	/**
	 * Constructor:
	 * @param game game
	 * @param level current level
	 */
	public GameScreen(BitSavior game, int level)
	{
		if(ScreenManager.aScreenTestMode) System.out.println("GameScreen created");
		
		this.game = game;
		world = new World(GameState.INITIALIZE, level);    // creating the World
		world.create();
		gameLevel = level;
	}


	@Override
	public void show()
	{
		if(ScreenManager.aScreenTestMode) System.out.println("GameScreen is shown");
		
		Gdx.input.setInputProcessor(new InputAdapter() {
			@Override
			public boolean keyDown(int keyCode) {
				if (keyCode == Input.Keys.ESCAPE) {
					game.manager.showScreen(Screens.TITLE);
				}
				return true;
			}
		} );
	}


	@Override
	public void render(float Delta)
	{
		switch(world.update(Delta))
		{
			case LOOSE_SHUTDOWN:
				game.manager.setLoseStats();
				game.manager.showScreen(Screens.LOSE);
				break;
			case WIN:
				game.manager.setWinStats(-1, -1); //TODO insert parameters for time and bugs
				game.manager.showScreen(Screens.WIN);
				break;
			default:
		}
		world.render(Delta);
	}

	public int getGameLevel() { return gameLevel; }


	@Override
	public void dispose()
	{
		if(ScreenManager.aScreenTestMode) System.out.println("GameScreen is disposed");
		
		world.dispose();
		if(ScreenManager.aScreenTestMode) System.out.println("gScreen: world disposed");
	}
	
	@Override
	public void hide()
	{
		Gdx.input.setInputProcessor(null);
	}
}
