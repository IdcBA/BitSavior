package com.bitsavior.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

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
	 * List of all enemies
	 */
	private ArrayList<Enemy> Enemies;
	/**
	 * maximum Number of Enemies
	 */
	private final int MaxNumberOfEnemies;
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

		// testing
		Enemies = new ArrayList<Enemy>();
		MaxNumberOfEnemies = 1;
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


		// distribute textures & create Entities
		map = new Tilemap(assetHolder.get("map_1.tmx",TiledMap.class), camera);
		player = new Player(assetHolder.get("pacman.png", Texture.class), 200.f);
		// testing, create Enemies
		for(int i = 0; i < MaxNumberOfEnemies ; i++)
			Enemies.add(new Enemy(assetHolder.get("pacman.png", Texture.class), 10.f, 200.f));

		// testing, spawn Enemies
		spawnEntities();




	}
	/**
	 * update the game logic
	 * all update functions to be called
	 * between handlePlayerInput() and checkCollisions()
	 */
	public void update()
	{
		handlePlayerInput();
		player.update();

		// testing
		for(int i = 0; i < MaxNumberOfEnemies; i++)
		{
			Enemies.get(i).update();
		}
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
		// testing/draw all enemies
		for(Enemy enemy : Enemies)
			enemy.draw(batch);
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


	/**
	 * manages the player input
	 */
	private void handlePlayerInput()
	{


		// if a specific key is pressed, move the player
		if(Gdx.input.isKeyPressed(Keys.A)) {
			//player.move(Player.Direction.LEFT, 1);
			player.direction = Player.Direction.LEFT;
		}
		else if(Gdx.input.isKeyPressed(Keys.D)) {
			//player.move(Player.Direction.RIGHT, 1);
			player.direction = Player.Direction.RIGHT;
		}
		else if(Gdx.input.isKeyPressed(Keys.W)) {
			//player.move(Player.Direction.UP, 1);
			player.direction = Player.Direction.UP;
		}
		else if(Gdx.input.isKeyPressed(Keys.S)) {
			player.direction = Player.Direction.DOWN;
			//player.move(Player.Direction.DOWN, 1);
		}

	}


	/**
	 * check alle Entities for collision
	 */
	private void checkCollisions()
	{
		// if collided with Environment, move back
		if(player.isCollided(map.getLayer(1)))
			player.move(-1);

		// testing, check enemy collision
		for(int i = 0; i < MaxNumberOfEnemies; i++)
		{
			if(player.isCollided(Enemies.get(i)))
			{
				player.move(-1);
			}
			if(Enemies.get(i).isCollided(map.getLayer(1)))
				Enemies.get(i).move(-1);


		}
		// reset current Direction for next update
		player.direction = Player.Direction.UNMOVED;




	}

	// testing
	void spawnEntities()
	{
		for(Enemy enemy : Enemies)
			enemy.spawn(100, 100);
	}

	
}
