package com.bitsavior.game;

public class Game {
	
	// public methods
	
	/**
	 * Constructor:
	 * 
	 */
	public Game()
	{	
		world = new World();	// creating the World
	}
	
	public void create()
	{
		world.create();
	}
	
	public void update()
	{
		world.update();
	}
	public void render()
	{
		world.render();
	}

	public void dispose()
	{
		world.dispose();
	}
	// private methods
	private void processEvents() {}
	
	
	
	// private methods
	
	// Variables
	private World world;
}
