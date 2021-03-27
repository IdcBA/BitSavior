package com.bitsavior.screens;

/**
 * for easier Screen switching
 * <p> WIN and LOSE delete the last gameScreen
 */
public enum Screens {
	TITLE,
	/** Level has to be set before with manager.setGameLevel(int level) */
	GAME,
	/** WinScreen has to be initialized before with manager.setLoseStats */
	WIN,
	/** Losecreen has to be initialized before with manager.setLoseStats */
	LOSE,
	SETTINGS
}
