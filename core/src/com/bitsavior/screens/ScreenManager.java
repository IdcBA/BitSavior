package com.bitsavior.screens;

/**
 * This Class holds the Screens: TitleScreen, GameScreen, (Lose/Win)
 * <p> TODO: edit "initialize, show, dispose" of win-/lose-com.bitsavior.screens
 * <p> TODO: for other Screens: add them extra in Screens.java
 */
public class ScreenManager {
	
	/** the game BitSavior */
	private BitSavior game;
	
	//the Screens
	/** The main menu */
	private TitleScreen tScreen;
	/** The GameScreen */
	private GameScreen gScreen;
	/** The WinScreen after winning the game */
	private WinScreen winScreen;
	/** The LoseScreen after losing the game */
	private LoseScreen loseScreen;
	/** The Settings for the game */
	private SettingsScreen settingsScreen;
	
	/** 
	 * Constructor
	 * <p>initializes all Screens except the GameScreen
	 */
	public ScreenManager(BitSavior game) {
		this.game = game;
		tScreen = new TitleScreen(game);
		winScreen = new WinScreen(game);
		loseScreen = new LoseScreen(game);
		settingsScreen = new SettingsScreen(game);
	}
	
	/**
	 * @param screenName Screens.[one of the following]
	 * <li>TITLE
	 * <li>GAME
	 * <li>WIN
	 * <li>LOSE
	 * <li>SETTINGS
	 */
	public void showScreen(Screens screenName) {
		switch(screenName) {
			case TITLE : 
				if(tScreen==null) System.out.println("TitleScreen is null");
				else game.setScreen(tScreen);
				break;
			case GAME :
				if(!gameIsRunning()) System.out.println("GameScreen is null");
				else game.setScreen(gScreen);
				break;
			case WIN :
				if(winScreen==null) System.out.println("WinScreen is null");
				else {
					//deletes last GameScreen
					gScreen.dispose();
					gScreen = null;
					game.setScreen(winScreen);
				}
				break;
			case LOSE :
				if(loseScreen==null) System.out.println("LoseScreen is null");
				else {
					//deletes last GameScreen
					gScreen.dispose();
					gScreen = null;
					game.setScreen(loseScreen);
				}
				break;
			case SETTINGS :
				if(settingsScreen==null) System.out.println("settingsScreen is null");
				else game.setScreen(settingsScreen);
				break;
			default : System.out.println("try \"TITLE, GAME, WIN, LOSE or SETTINGS\"");
		}
	}
	
	//methods to manage GameScreen
	/** true if the GameScreen is not null*/
	public boolean gameIsRunning() {
		if(gScreen==null) return false;
		else return true;
	}
	/** sets the GameScreen or replaces the existing one
	 * <p> has to be set before the GameScreen can be shown
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
	/** returns current Level of the GameScreen */
	public int getGameLevel() {
		return gScreen.getGameLevel();
	}

	/**
	 * disposes title-, game-, win-, lose-, settingsScreen
	 * <p> (if not already null/disposed)
	 */
	public void dispose() {
		if(tScreen != null) tScreen.dispose();
		if(gScreen != null) gScreen.dispose();
		if(winScreen != null) winScreen.dispose();
		if(loseScreen != null) loseScreen.dispose();
		if(settingsScreen != null) settingsScreen.dispose();
	}
}
