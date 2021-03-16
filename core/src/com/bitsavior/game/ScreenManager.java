package com.bitsavior.game;

/**
 * This Class holds the Screens: TitleScreen, GameScreen, (Lose/Win)
 * <p> TODO: edit "initialize, show, dispose" of win-/lose-screens
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
	private WinScreen winScreen;
	/** The LoseScreen after losing the game */
	private LoseScreen loseScreen;
	
	/** 
	 * Constructor
	 * <p>initializes (only?) title Screen
	 */
	public ScreenManager(BitSavior game) {
		this.game = game;
		tScreen = new TitleScreen(game);
		winScreen = new WinScreen(game);
		loseScreen = new LoseScreen(game);
		//TODO  ... win/lose
	}
	
	/**
	 * Level has to be set before: 
	 * @see {@link setGameLevel}
	 * @param screenName one of these: TITLE,GAME,WIN,LOSE
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
			default : System.out.println("try \"TITLE, GAME, WIN, LOSE\"");
		}
	}
	
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

	public void dispose() {
		if(tScreen != null) tScreen.dispose();
		if(gScreen != null) gScreen.dispose();
		if(winScreen != null) winScreen.dispose();
		if(loseScreen != null) loseScreen.dispose();
	}
}
