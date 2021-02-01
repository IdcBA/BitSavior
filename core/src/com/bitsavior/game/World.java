package com.bitsavior.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Vector2;
import com.bitsavior.asset.Assets;
import com.bitsavior.entity.*;
import com.bitsavior.map.Tilemap;

import java.util.ArrayList;
import java.util.Random;

/**
 * A class that represents the world of the game
 * <p><ul>
 *     <li>Creates the actual level</li>
 *     <li>Computes the game logic</li>
 *     <li>Render the scene</li>
 * </ul><p>
 * @author Valentin Zirngibl
 */
public class World
{
	// private Members
	/**
	 * holds user interface elements as
	 * fonts and buttons
	 */
	private UserInterface userInterface;
	/**
	 * hold timer data for the time limit
	 */
	Watch timer;
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
	 * friendly bug with a lightsource attached
	 */
	private AntiBug debugger;
	/**
	 * contains all enemies
	 */
	private ArrayList<Bug> Enemies;
	/**
	 * maximum Number of Enemies
	 */
	private final int MaxNumberOfEnemies;
	/**
	 * maximum Number of pickups
	 */
	private final int MaxNumberOfPickUps;
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
	/**
	 * handles the blending of the scene
	 */
	private FrameBuffer lightBuffer;
	private TextureRegion lightBufferRegion;


	// public Methods

	/**
	 * initialise required data for the creation of the world
	 */
	public World()
	{
		timer = new Watch(100);
		assets = new Assets();
		batch = new SpriteBatch();
		camera = new OrthographicCamera();

		WORLDBOUNDS = new Vector2(1280.f, 960.f);

		// setup numbers
		MaxNumberOfEnemies = 2;
		MaxNumberOfPickUps = 10;


		Enemies = new ArrayList<Bug>();
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

		userInterface = new UserInterface(assets.holder, timer);


		debugger = new AntiBug(assets.holder);
		debugger.spawn(40, 40);


		// distribute textures & create Entities
		map = new Tilemap(assets.holder.get(Assets.map1), camera);
		player = new Player(assets.holder);
		player.setPosition(40.f, 40.f);
		
		// spawn pickups & enemies
		spawnEnemies();
		spawnPickUps();

		// if there is already a lightBuffer, reset
		if(lightBuffer != null)
			lightBuffer.dispose();
		// create FrameBuffer with width/height of the screen
		lightBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, (int)WORLDBOUNDS.x, (int)WORLDBOUNDS.y, false);
		lightBuffer.getColorBufferTexture().setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

		lightBufferRegion = new TextureRegion(lightBuffer.getColorBufferTexture(), 0, 0, 1280, 960);
		lightBufferRegion.flip(false, true);

		timer.startWatch();



	}
	/**
	 * update the game logic
	 * all update functions to be called
	 * between handlePlayerInput() and checkCollisions()
	 * @param Delta : elapsed time since last frame
	 */
	public boolean update(float Delta)
	{
		handlePlayerInput();
		player.update(Delta);
		debugger.update(Delta);

		// testing
		for(int i = 0; i < MaxNumberOfEnemies; i++)
		{
			Enemies.get(i).update(Delta, player);
		}
		updatePickUps();
		userInterface.update(player.getPickUpCounter());

		checkCollisions();

		return (timer.update() && player.isAlive);
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
		// draw objects
		batch.begin();

		for(PickUp pickUp : pickUps)
			pickUp.draw(batch, Delta);
		for(Bug bug : Enemies)
			bug.draw(batch, Delta);

		batch.end();


		// render blending and lightcone into the second FrameBuffer
		lightBuffer.begin();

		// set blending functions
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
		Gdx.gl.glEnable(GL20.GL_BLEND);

		// clear the second bufferscreen
		Gdx.gl.glClearColor(0.f, 0.f, 0.f, 1.f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// draw the lightsources
		batch.begin();
		player.drawFlashlight(batch, Delta);
		debugger.drawFlashlight(batch,Delta);
		batch.end();

		lightBuffer.end();

		// draw second buffer and player on top
		Gdx.gl.glBlendFunc(GL20.GL_DST_COLOR, GL20.GL_ZERO);
		batch.begin();
		batch.draw(lightBufferRegion, 0, 0, 1280, 960);


		player.draw(batch, Delta);
		debugger.draw(batch, Delta);
		userInterface.draw(batch);




		batch.end();


	}
	
	/**
	 * free all assets
	 */
	public void dispose()
	{
		assets.dispose();
		userInterface.dispose();
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

		// check debugger collision
		debugger.isCollided(map);

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
			Enemies.add(new Bug(assets.holder.get(Assets.enemy)));
		

		for(Bug bug : Enemies)
			bug.spawn(100, 100);
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
