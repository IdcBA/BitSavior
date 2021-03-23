package com.bitsavior.game;

/**
 * represents the actual state of the game
 * used for flow control of the application
 */
public enum GameState
{
    /**
     * new game is started, game is initialized
     */
    INITIALIZE,
    /**
     * game is initialized, world is created, start sequence played
     */
    START,
    /**
     * game is running
     */
    RUN,
    /**
     * game is won
     */
    WIN,
    /**
     * game is lost due timeout or caught
     */
    LOOSE,
    /**
     * game is shutting down
     */
    LOOSE_SHUTDOWN
}
