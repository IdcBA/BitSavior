package com.bitsavior.game;

import com.badlogic.gdx.ApplicationListener;

/**
 * This Class holds the Screens: TitleScreen, GameScreen, (Lose/Win)
 * <p> TODO: edit " initialize, show, dispose" of win-/lose-screens
 * <p> TODO: for other Screens: add them extra in Screens.java
 */
public class ScreenManager {
	
	/** the game BitSavior */
	private BitSavior game;
	
	//the Screens
	/** The TitleScreen/Mainmenu */
	private TitleScreen tScreen;
	/** The GameScreen */
	private GameScreen gScreen;
	/** The WinScreen after winning the game */
	//TODO ... win
	/** The LoseScreen after losing the game */
	//TODO ... lose
	
	/** initializes all Screens except the GameScreen */
	public ScreenManager(BitSavior game) {
		this.game = game;
		tScreen = new TitleScreen(game);
		//TODO  ... win/lose
	}
	
	/** @param screenName one of these: TITLE,GAME,(WIN,LOSE) */
	public void showScreen(Screens screenName) {
		switch(screenName) {
			case TITLE : 
				if(tScreen==null) System.out.println("tScreen is null");
				else game.setScreen(tScreen);
				break;
			case GAME :
				if(gameIsRunning()) {
					game.setScreen(gScreen);
				}
				else System.out.println("gScreen is null");
				break;
			case WIN :
				System.out.println("WinScreen not in ScreenManager implemented yet.");
				//TODO ... win
				break;
			case LOSE :
				System.out.println("LoseScreen not in ScreenManager implemented yet.");
				//TODO ... lose
				break;
			default : System.out.println("try \"TITLE, GAME, WIN, LOSE\"");
		}
	}
	
	/** true if the GameScreen is not null*/
	public boolean gameIsRunning() {
		if(gScreen==null) return false;
		else return true;
	}
	
	/** sets the GameScreen or replaces the existing one 
	 * @param level currently only 1
	 */
	public void setGameLevel(int level) {
		if(!gameIsRunning()) {
			gScreen = new GameScreen(game, level);
		}
		else {
			gScreen.dispose();
			gScreen = new GameScreen(game, level);
		}
	}

	public void dispose() {
		tScreen.dispose();
		gScreen.dispose();
		//TODO ... win/lose
	}
}
