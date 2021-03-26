package com.bitsavior.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.bitsavior.game.GameState;
import com.bitsavior.game.World;

/**
 * screen which displays the game itself
 */
public class GameScreen extends ScreenAdapter {

	//variables for testing
	/** ... */
	
	// private Members
	private ScreenManager screenManager;
	private World world;
	/** not needed at the moment because there is only one level */
	private int gameLevel = 0;

	// public methods
	/**
	 * Constructor:
	 * @param screenManager to access other screens
	 * @param level current level
	 */
	public GameScreen(ScreenManager screenManager, int level)
	{
		if(ScreenManager.aScreenTestMode) System.out.println("GameScreen created");
		
		this.screenManager = screenManager;
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
					screenManager.showScreen(Screens.TITLE);
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
				screenManager.setLoseStats();
				screenManager.showScreen(Screens.LOSE);
				break;
			case WIN:
				screenManager.setWinStats(-1, -1); //TODO insert parameters for time and bugs
				screenManager.showScreen(Screens.WIN);
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
