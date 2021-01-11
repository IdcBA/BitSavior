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
	// array of enemies
	// array of pickups

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

		WORLDBOUNDS = new Vector2(1280.f, 960.f);
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
		map = new Tilemap(assetHolder.get("map_1.tmx",TiledMap.class), camera);
		player = new Player(assetHolder.get("pacman.png", Texture.class), 200.f);

		
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
		assetHolder.load("map_1.tmx", TiledMap.class);
		assetHolder.load("pacman.png", Texture.class);

		// wait until everything is loaded
		assetHolder.finishLoading();

	}

	private void handlePlayerInput()
	{

		if(Gdx.input.isKeyPressed(Keys.A)) {
			player.move(Player.Direction.LEFT, 1);
		}
		else if(Gdx.input.isKeyPressed(Keys.D)) {
			player.move(Player.Direction.RIGHT, 1);
		}
		else if(Gdx.input.isKeyPressed(Keys.W)) {
			player.move(Player.Direction.UP, 1);
		}
		else if(Gdx.input.isKeyPressed(Keys.S)) {
			player.move(Player.Direction.DOWN, 1);
		}
	}


	private void checkCollisions()
	{
		if(player.isCollided(map.getLayer(1)))
			player.move(player.direction, -1);
	}

	
}
