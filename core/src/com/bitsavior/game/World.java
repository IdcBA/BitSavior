package com.bitsavior.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.bitsavior.asset.Assets;
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
	 * manages all game-assets
	 */
	private Assets assets;
	/**
	 * Orthographic Camera:
	 * Shows what we see in the world.
	 * Works with WorldUnits instead of Pixels,
	 * makes it easier to handle different screensizes
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
		assets = new Assets();
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
		assets.load();

		// distribute textures & create Entities
		map = new Tilemap(assets.holder.get(Assets.currentMap), camera);
		player = new Player(assets.holder.get(Assets.player), velocityPlayer);
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
			Enemies.get(i).update(Delta, player);
		}
		updatePickUps();
		checkCollisions();
	}

	/**
	 * manages all render calls inside the world
	 */
	public void render(float Delta)
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
		player.draw(batch, Delta);
		// testing/draw all enemies
		for(Enemy enemy : Enemies)
			enemy.draw(batch, Delta);
		for(PickUp pickUp : pickUps)
			pickUp.draw(batch, Delta);
		batch.end();
	}
	
	/**
	 * free all assets
	 */
	public void dispose()
	{
		assets.dispose();
		PickUp.pickUpCounter = 0;

		System.out.println("disposed");
	}
	
	// private methods
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
			Enemies.add(new Enemy(assets.holder.get(Assets.enemy), viewRange, velocityEnemy));
		

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
			pickUps.add(new PickUp(assets.holder.get(Assets.pickUp)));

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
