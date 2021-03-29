package com.bitsavior.screens;

/**
 * Enum for easier Screen switching
 */
public enum Screens {
	/**
	 * title screen
	 */
	TITLE,
	/**
	 * creates tutorial screen
	 */
	TUTORIAL,
	/**
	 * level has to be set before with manager.setGameLevel(int level)
	 */
	GAME,
	/**
	 * winScreen has to be initialized before with manager.setLoseStats
	 */
	WIN,
	/**
	 * loseScreen has to be initialized before with manager.setLoseStats
	 */
	LOSE,
	/**
	 * setting screen
	 */
	SETTINGS
}
