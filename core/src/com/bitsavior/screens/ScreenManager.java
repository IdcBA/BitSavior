package com.bitsavior.screens;

/**
 * This Class holds and manages the Screens
 * <p> TODO for new screens:
 * <li>Here: add them to constructor, showScreen and dispose
 * <li>Screens.java: add the enum to access it
 */
public class ScreenManager {
	
	//variables for testing
    /** when true prints create, show and dispose of screens */
    static boolean aScreenTestMode = false;
    /** when true: in titleScreen NUM1->winScreen and NUM2->loseScreen */
    static boolean aScreenEasySwitch = true;
	
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
	 * <p>initializes titleScreen and settingsScreen
	 */
	public ScreenManager(BitSavior game) {
		this.game = game;
		tScreen = new TitleScreen(game);
		settingsScreen = new SettingsScreen(game);
		//game is created with setGameLevel
		//winScreen  is created after a game in showScreen->case WIN
		//loseScreen is created after a game in showScreen->case lose
	}
	
	/**
	 * shows the requested screen
	 * @param screenName e.g.: "Screens.WIN" ; consider screen comments!
	 */
	public void showScreen(Screens screenName) {
		switch(screenName) {
			case TITLE : 				
				if(tScreen==null) System.out.println("TitleScreen is null");
				else game.setScreen(tScreen);
				//delete old win-/loseScreens
				if(winScreen!=null) {
					winScreen.dispose();
					winScreen = null;
				}
				if(loseScreen!=null) {
					loseScreen.dispose();
					loseScreen = null;
				}
				break;
			case GAME :
				if(!gameIsRunning()) System.out.println("GameScreen is null");
				else game.setScreen(gScreen);
				break;
			case WIN :
				if(winScreen==null) System.out.println("WinScreen is null");
				else {
					//deletes last GameScreen
					if(gameIsRunning()) {
						gScreen.dispose();
						gScreen = null;
						if(aScreenTestMode) System.out.println("gScreen disposed for winScreen");
					}
					game.setScreen(winScreen);
				}
				break;
			case LOSE :
				if(loseScreen==null) System.out.println("LoseScreen is null");
				else {
					//deletes last GameScreen
					if(gameIsRunning()) {
						gScreen.dispose();
						gScreen = null;
						if(aScreenTestMode) System.out.println("gScreen disposed for loseScreen");
					}
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
	/** returns current Level of the GameScreen; not used at the moment due to only one level */
	public int getGameLevel() {
		return gScreen.getGameLevel();
	}
	
	//methods to manage winScreen
	/**
	 * deletes old and creates new WinScreen
	 * @param time time left
	 * @param bugs bugs left
	 */
	public void setWinStats(int time, int bugs) {
		if(winScreen!=null) {
			winScreen.dispose();
			winScreen = null;
			System.out.println("unexpected winScreen disposed");
		}
		winScreen = new WinScreen(game, time, bugs);
	}
	
	//methods to manage loseScreen
	/**
	 * deletes old and creates new LoseScreen
	 */
	public void setLoseStats() {
		if(loseScreen!=null) {
			loseScreen.dispose();
			loseScreen = null;
			System.out.println("unexpected loseScreen disposed");
		}
		loseScreen = new LoseScreen(game);
	}

	/**
	 * disposes title-, game-, win-, lose-, settingsScreen
	 * <p> (if not already null/disposed)
	 */
	public void dispose() {
		if(aScreenTestMode) System.out.println("ScreenManager gets disposed and disposes the following:");
		
		if(tScreen != null) tScreen.dispose();
		if(gScreen != null) gScreen.dispose();
		if(winScreen != null) winScreen.dispose();
		if(loseScreen != null) loseScreen.dispose();
		if(settingsScreen != null) settingsScreen.dispose();
	}
}
