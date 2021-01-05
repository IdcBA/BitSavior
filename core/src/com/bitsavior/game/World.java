package com.bitsavior.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class World {
	
	// public Methods
	
	public World()
	{
		assetHolder = new AssetManager();
		batch = new SpriteBatch();
		camera = new OrthographicCamera();

		WORLDBOUNDS = new Vector2(50.f, 50.f);
		
	}
	
	public void create()
	{
		// sets the camera to the world bounds
		camera.viewportWidth  = WORLDBOUNDS.x;
		camera.viewportHeight = WORLDBOUNDS.y;

		// centers camera at 0,0
		camera.position.set(WORLDBOUNDS.x / 2.f, WORLDBOUNDS.y / 2.f, 0);
		
		// loading all assets regarding the game world
		assetHolder.load("badlogic.jpg", Texture.class);
		
		
		// wait until everything is loaded
		assetHolder.finishLoading();
		
		//img = assetHolder.get("badlogic.jpg", Texture.class);
		player = new Player(assetHolder.get("badlogic.jpg", Texture.class));

		
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
		checkCollisions();
		
	}
	
	public void render()
	{
		// Clear the Screen
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// update camera
		camera.update();
		// set the projection matrix of the ScreenBatch to camera's size
		batch.setProjectionMatrix(camera.combined);
		
		batch.begin();
		player.draw(batch);
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
		// multiplied with DeltaTime to ensure same movement with different framerates
		if(Gdx.input.isKeyPressed(Keys.A))
			player.setPosition(-player.velocity * Gdx.graphics.getDeltaTime(), 0);
		if(Gdx.input.isKeyPressed(Keys.D))
			player.setPosition(player.velocity * Gdx.graphics.getDeltaTime(), 0);
		if(Gdx.input.isKeyPressed(Keys.W))
			player.setPosition(0,player.velocity * Gdx.graphics.getDeltaTime());
		if(Gdx.input.isKeyPressed(Keys.S))
			player.setPosition(0,-player.velocity * Gdx.graphics.getDeltaTime());
	}

	/**
	 * - JUST FOR TESTING PURPOSES - !!!!!!!!!!!!!
	 * checkCollisions()
	 * - check if player reaches the end of the camera
	 */
	private void checkCollisions()
	{
		if(player.getPosition().x <  0.f)
			player.setPosition(player.velocity * Gdx.graphics.getDeltaTime(), 0.f);
		if(player.getPosition().x + player.getSize().x >  WORLDBOUNDS.x)
			player.setPosition(-player.velocity * Gdx.graphics.getDeltaTime(), 0.f);
		if(player.getPosition().y <  0.f)
			player.setPosition(0.f, player.velocity * Gdx.graphics.getDeltaTime());
		if(player.getPosition().y + + player.getSize().y  >  WORLDBOUNDS.y)
			player.setPosition(0.f, -player.velocity * Gdx.graphics.getDeltaTime());
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
	/**
	 * collecting drawable objects
	 */
	private SpriteBatch batch;
	/**
	 * Player class
	 */
	private Player player;
	/**
	 * Worldbounds
	 */
	final Vector2 WORLDBOUNDS;


	
}
