package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class World {
	
	// public Methods
	
	public World()
	{
		assetHolder = new AssetManager();
		batch = new SpriteBatch();
		camera = new OrthographicCamera(50,50);
		
	}
	
	public void create()
	{
		
		// loading all assets regarding the game world
		assetHolder.load("badlogic.jpg", Texture.class);
		
		
		// wait until everything is loaded
		assetHolder.finishLoading();
		
		img = assetHolder.get("badlogic.jpg", Texture.class);
		
	}
	
	/**
	 * update():
	 * updates the world logic including:
	 * - player input
	 * - position of enemies
	 * - collisions
	 * ...
	 */
	public void update()
	{
		handlePlayerInput();
		
	}
	
	public void render()
	{
		// Clear the Screen
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		// set the projection matrix of the ScreenBatch to camera's size
		batch.setProjectionMatrix(camera.combined);
		
		batch.begin();
		batch.draw(img, x, y, 25, 25);
		batch.end();
	}
	
	/**
	 * free all assets
	 */
	public void dispose()
	{
		assetHolder.unload("badlogic.jpg");
		
	}
	
	// private methods
	private void handlePlayerInput()
	{
		// just an example
		// multiplied with DeltaTime to ensure same movement with different Framerates
		if(Gdx.input.isKeyPressed(Keys.A))
			x -= 50 * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Keys.D))
			x += 50 * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Keys.W))
			y += 50 * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Keys.S))
			y -= 50 * Gdx.graphics.getDeltaTime();
	}
	
	// private Members
	/**
	 * Manage all game-assets
	 */
	private AssetManager assetHolder;
	/**
	 * Orthographic Camera:
	 * Shows what we see in the world.
	 * Works with WorldUnits instead of Pixels, 
	 * makes it easier to handle different screensizes
	 * and use Box2D
	 */
	private OrthographicCamera camera;
	
	
	// just an example
	private SpriteBatch batch;
	private Texture img;
	private float x = 0.f;
	private float y = 0.f;
	
	
}
