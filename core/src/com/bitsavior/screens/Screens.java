package com.bitsavior.screens;

/**
 * for easier Screen switching
 */
public enum Screens {
	TITLE,
	/** Level has to be set before with manager.setGameLevel(int level) */
	GAME,
	/** deletes last gameScreen <p> WinScreen has to be initialized before with manager.setWinStats */
	WIN,
	/** deletes last gameScreen */
	LOSE,
	SETTINGS
}
