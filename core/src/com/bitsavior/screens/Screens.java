package com.bitsavior.screens;

/**
 * for easier Screen switching
 * <p> WIN and LOSE delete the last gameScreen
 */
public enum Screens {
	/**
	 * title screen
	 */
	TITLE,
	/**
	 * level has to be set before with manager.setGameLevel(int level)
	 */
	GAME,
	/**
	 * winScreen has to be initialized before with manager.setLoseStats
	 */
	WIN,
	/**
	 * losecreen has to be initialized before with manager.setLoseStats
	 */
	LOSE,
	/**
	 * setting screen
	 */
	SETTINGS
}
