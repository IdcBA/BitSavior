package com.bitsavior.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.bitsavior.game.GameState;
import com.bitsavior.game.World;

/**
 * Screen which displays the game itself
 * <p> connects other screens and the "world"
 */
public class GameScreen extends ScreenAdapter {

	//variables for testing
	/** ... */
	
	// private Members
	/** to access interact with other screens */
	private ScreenManager screenManager;
	/** @see World */
	private World world;
	/** level of current game*/
	private int gameLevel;

	// public methods
	/**
	 * Creates the game screen
	 * @param screenManager to access other screens
	 * @param level current level
	 */
	public GameScreen(ScreenManager screenManager, int level)
	{
		if(ScreenManager.aScreenTestMode) System.out.println("GameScreen created");
		
		this.screenManager = screenManager;
		this.gameLevel = level;
		world = new World(GameState.INITIALIZE, level);    // creating the World
		world.create();
	}

	/**
	 * adds an {@link InputProcessor} to pause with ESCAPE
	 */
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

	/**
	 * calls update of the world and if so sets lose/win
	 * <p> calls render of the world
	 */
	@Override
	public void render(float Delta)
	{
		switch(world.update(Delta))
		{
			case LOOSE_SHUTDOWN:
				screenManager.setLoseStats();
				screenManager.showScreen(Screens.LOSE);
				break;
			case WIN_SHUTDOWN:
				screenManager.setWinStats(world.getRemainingTime(), world.getRemainingBugs(), gameLevel);
				screenManager.showScreen(Screens.WIN);
				break;
			default:
		}
		world.render(Delta);
	}

	/** @return current gameLevel */
	public int getGameLevel() { return gameLevel; }

	/**
	 * disposes the world
	 */
	@Override
	public void dispose()
	{
		if(ScreenManager.aScreenTestMode) System.out.println("GameScreen is disposed");
		
		world.dispose();
		System.out.println("gScreen: world disposed");
		if(ScreenManager.aScreenTestMode) System.out.println("gScreen: world disposed");
	}
	
	/**
     * sets the InputProcessor to null to prevent bugs
     */
	@Override
	public void hide()
	{
		Gdx.input.setInputProcessor(null);
	}
}
