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
	 * contains all pickups
	 */
	private ArrayList<PickUp> pickUps;
	/**
	 * maximum Number of pickups
	 */
	private final int MaxNumberOfPickUps;

	// public Members
	/**
	 * describes the bounds of the world
	 * visible trough the camera in worldunits
	 */
	public final Vector2 WORLDBOUNDS;
	
	// public Methods
	public World()
	{
		assetHolder = new AssetManager();
		batch = new SpriteBatch();
		camera = new OrthographicCamera();

		WORLDBOUNDS = new Vector2(1280.f, 960.f);


		Enemies = new ArrayList<Enemy>();
		MaxNumberOfEnemies = 1;

		pickUps = new ArrayList<PickUp>();
		MaxNumberOfPickUps = 10;
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
		player = new Player(assetHolder.get("pacman.png", Texture.class), 400.f);
		player.setPosition(50.f, 50.f);

		// spawn pickups & enemies
		spawnEnemies();
		spawnPickUps();


	}
	/**
	 * update the game logic
	 * all update functions to be called
	 * between handlePlayerInput() and checkCollisions()
	 */
	public void update(float Delta)
	{
		handlePlayerInput();
		player.update(Delta);
		// testing

		for(int i = 0; i < MaxNumberOfEnemies; i++)
		{
			Enemies.get(i).setCurrentMovement(Movement.CONTINUE);
			Enemies.get(i).update(Delta);
		}


		updatePickUps();
		checkCollisions();
		player.update(Delta);

		// testing
		for(int i = 0; i < MaxNumberOfEnemies; i++)
		{
			Enemies.get(i).update(Delta );
		}


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
			player.setCurrentMovement(Movement.LEFT);
		}
		else if(Gdx.input.isKeyPressed(Keys.D)) {
			player.setCurrentMovement(Movement.RIGHT);
		}
		else if(Gdx.input.isKeyPressed(Keys.W)) {
			player.setCurrentMovement(Movement.UP);
		}
		else if(Gdx.input.isKeyPressed(Keys.S)) {
			player.setCurrentMovement(Movement.DOWN);
		}
		else
			player.setCurrentMovement(Movement.UNMOVED);
	}


	/**
	 * check all Entities for collision
	 */
	private void checkCollisions()
	{
		// if collided with Environment, move back
		if(map.isCollided(player))
			player.setCurrentMovement(Movement.BACK);

		// testing, check enemy collision
		for(int i = 0; i < MaxNumberOfEnemies; i++)
		{
			if(player.isCollided(Enemies.get(i)))
			{
				player.setCurrentMovement(Movement.BACK);
			}
			if(map.isCollided(Enemies.get(i))) {
				Enemies.get(i).setCurrentMovement(Movement.BACK);
				Enemies.get(i).collision = true;

			}
			else
			{
				Enemies.get(i).setCurrentMovement(Movement.UNMOVED);
			}
		}


		// check pickup collision
		for(int i = 0; i < PickUp.pickUpCounter; i++)
		{
			if(player.isCollided(pickUps.get(i)))
				player.collect(pickUps.get(i));
		}

		// reset current Direction for next update
		//player.setCurrentMovement(new MoveMessage(Direction.UNMOVED));
		//player.direction = Direction.UNMOVED;

	}

	/**
	 * spawn the maximum amount of enemies given by MaxNumberOfEnemies
	 */
	void spawnEnemies()
	{
		for(int i = 0; i < MaxNumberOfEnemies ; i++)
			Enemies.add(new Enemy(assetHolder.get("pacman.png", Texture.class), 10.f, 400.f, map.getLayer(1)));


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
			pickUps.add(new PickUp(assetHolder.get("pacman.png", Texture.class)));

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
