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
import com.bitsavior.entity.Enemy;
import com.bitsavior.entity.Movement;
import com.bitsavior.entity.PickUp;
import com.bitsavior.entity.Player;
import com.bitsavior.map.Tilemap;

import java.util.ArrayList;
import java.util.Random;

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
	 * contains all enemies
	 */
	private ArrayList<Enemy> Enemies;
	/**
	 * maximum Number of Enemies
	 */
	private final int MaxNumberOfEnemies;
	/**
	 * maximum Number of pickups
	 */
	private final int MaxNumberOfPickUps;

	private final float velocityPlayer;
	private final float velocityEnemy;
	private final float viewRange;

	private ArrayList<Float> levelData;



	/**
	 * contains all pickups
	 */
	private ArrayList<PickUp> pickUps;

	// public Members
	/**
	 * describes the bounds of the world
	 * visible trough the camera in worldunits
	 */
	public final Vector2 WORLDBOUNDS;
	
	
	
	// public Methods

	/**
	 * initialise required data for the creation of the world
	 */
	public World()
	{
		assetHolder = new AssetManager();
		batch = new SpriteBatch();
		camera = new OrthographicCamera();

		WORLDBOUNDS = new Vector2(1280.f, 960.f);

		// setup numbers
		MaxNumberOfEnemies = 2;
		MaxNumberOfPickUps = 10;
		velocityEnemy  = 100.f;
		velocityPlayer = 100.f;
		viewRange = 200.f;


		Enemies = new ArrayList<Enemy>();
		pickUps = new ArrayList<PickUp>();

	}

	/**
	 * setup the game session
	 */
	public void create() {
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
		player = new Player(assetHolder.get("pacman.png", Texture.class), velocityPlayer);
		player.setPosition(40.f, 40.f);
		
		// spawn pickups & enemies
		spawnEnemies();
		spawnPickUps();

	}
	/**
	 * update the game logic
	 * all update functions to be called
	 * between handlePlayerInput() and checkCollisions()
	 * @param Delta : elapsed time since last frame
	 */
	public void update(float Delta)
	{
		handlePlayerInput();
		player.update(Delta);

		// testing
		for(int i = 0; i < MaxNumberOfEnemies; i++)
		{
			Enemies.get(i).update(Delta, player, map);
		}
		updatePickUps();
		checkCollisions();
	}

	/**
	 * manages all render calls inside the world
	 */
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
		for(PickUp pickUp : pickUps)
			pickUp.draw(batch);
		batch.end();
	}
	
	/**
	 * free all assets
	 */
	public void dispose()
	{
		assetHolder.dispose();
		PickUp.pickUpCounter = 0;

		System.out.println("disposed");
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
		assetHolder.load("memory-leaks.jpg", Texture.class);

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
			player.direction = Movement.LEFT;
		}
		else if(Gdx.input.isKeyPressed(Keys.D)) {
			player.direction = Movement.RIGHT;
		}
		else if(Gdx.input.isKeyPressed(Keys.W)) {
			player.direction = Movement.UP;
		}
		else if(Gdx.input.isKeyPressed(Keys.S)) {
			player.direction = Movement.DOWN;
		}
		else
			player.direction = Movement.UNMOVED;
	}


	/**
	 * check all entities for collision
	 */
	private void checkCollisions()
	{
		// check collision with environment
		player.isCollided(map);

		// check enemy collision
		for(int i = 0; i < MaxNumberOfEnemies; i++)
		{
			Enemies.get(i).isCollided(map);
			if(player.isCollided(Enemies.get(i)))
				player.isAlive = false;
		}

		// check pickup collision
		for(int i = 0; i < PickUp.pickUpCounter; i++)
			if(player.isCollided(pickUps.get(i)))
				player.collect(pickUps.get(i));
	}

	/**
	 * spawn the maximum amount of enemies given by MaxNumberOfEnemies
	 */
	void spawnEnemies()
	{
		for(int i = 0; i < MaxNumberOfEnemies ; i++)
			Enemies.add(new Enemy(assetHolder.get("pacman.png", Texture.class), viewRange, velocityEnemy));


		for(Enemy enemy : Enemies)
			enemy.spawn(100, 100);
	}

	/**
	 * spawn the maximum amount of pickups given by MaxNumberOfPickUps
	 */

	private void spawnPickUps()
	{

		Random random = new Random();

		for(int i = 0; i < MaxNumberOfPickUps; i++)
		{
			pickUps.add(new PickUp(assetHolder.get("memory-leaks.jpg", Texture.class)));

			// if a pickup is colliding with the map, repeat with new coordinates
			do {
				pickUps.get(i).spawn(random.nextInt((int)WORLDBOUNDS.x), random.nextInt((int)WORLDBOUNDS.y));
			} while(map.isCollided(pickUps.get(i)));
		}
	}

	/**
	 * checks if pickups are collected and can be deleted
	 */

	private void updatePickUps()
	{
		for(int i = 0; i < PickUp.pickUpCounter; i++)
		{
			if(!pickUps.get(i).isAlive) {
				pickUps.remove(i);
				PickUp.pickUpCounter--;
			}
		}
	}
}
