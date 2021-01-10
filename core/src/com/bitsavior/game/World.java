package com.bitsavior.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;

/**
 * represents the world of the game
 */
public class World
{
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
	 * class contains the Tiled Map, additional renderer and related data
	 */
	private Tilemap map;
	/**
	 * Player class
	 */
	private Player player;
	/**
	 * relation worldunits / pixels
	 */
	// array of enemies
	// array of pickups
	private final float UNIT_SCALE;

	// public Members
	/**
	 * Worldbounds in worldunits
	 */
	public final Vector2 WORLDBOUNDS;
	
	// public Methods
	public World()
	{
		assetHolder = new AssetManager();
		batch = new SpriteBatch();
		camera = new OrthographicCamera();

		WORLDBOUNDS = new Vector2(100.f, 100.f);
		UNIT_SCALE = 1.f / 4.f;
		
	}

	/**
	 * setup the game session
	 */
	public void create()
	{

		// sets the camera to the world bounds
		camera.viewportWidth  = WORLDBOUNDS.x;
		camera.viewportHeight = WORLDBOUNDS.y;

		// centers camera at 0,0
		camera.position.set(WORLDBOUNDS.x / 2.f, WORLDBOUNDS.y / 2.f, 0);
		camera.update();

		// load assets
		loadAssets();


		// distribute textures
		map = new Tilemap(assetHolder.get("level.tmx",TiledMap.class), UNIT_SCALE, camera);
		player = new Player(assetHolder.get("pacman.png", Texture.class), 50.f);

		
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
		// render the map
		map.render();

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
		assetHolder.dispose();
	}
	
	// private methods

	/**
	 * loadAssets()
	 * - loads all assets
	 */
	private void loadAssets()
	{
		// set the loader for tmx maps
		assetHolder.setLoader(TiledMap.class, new TmxMapLoader());

		// loading all assets regarding the game world
		assetHolder.load("level.tmx", TiledMap.class);
		assetHolder.load("pacman.png", Texture.class);

		// wait until everything is loaded
		assetHolder.finishLoading();

	}

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

	
}
