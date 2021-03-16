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
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.bitsavior.asset.Assets;
import com.bitsavior.entity.*;
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
	 * holds the actual state of the game
	 * to manage different behaviours
	 */
	private GameState gameState;
	/**
	 * holds user interface elements as
	 * fonts and buttons
	 */
	private UserInterface userInterface;
	/**
	 * hold timer data for the time limit
	 */
	private Watch timer;
	/**
	 * holds the time required for starting a game and animate the entrysequence
	 */
	private Watch startTimer;
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
	 * handles the blending of the scene
	 */
	private FrameBuffer lightBuffer;
	private TextureRegion lightBufferRegion;
	/**
	 * holds the backgroundmusic of the current level
	 */
	private BackgroundMusic music;
	/**
	 * class contains the Tiled Map, additional renderer and related data
	 */
	private Tilemap map;
	/**
	 * Player class
	 */
	private Player player;

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
	/**
	 * describes the bounds of the world
	 * visible trough the camera in worldunits
	 */
	public final Vector2 WORLDBOUNDS;
	/**
	 * shape renderer for the fading effects
	 */
	private ShapeRenderer shapeRenderer;
	/**
	 * alpha value used for the fading effect
	 */
	private float fadeAlpha;
	/**
	 * value for coordinating the speed of the fading effect
	 */
	private long fadeTimer;
	/**
	 * initialise required data for the creation of the world
	 * @param gameState : initialises the gamestate once, to ensure the correct behaviour
	 */
	public World(GameState gameState)
	{
		timer = new Watch(100);
		// time limit for the entry animation
		startTimer = new Watch(10);
		assets = new Assets();
		batch = new SpriteBatch();
		camera = new OrthographicCamera();

		shapeRenderer = new ShapeRenderer();

		WORLDBOUNDS = new Vector2(1280.f, 960.f);

		// setup numbers
		MaxNumberOfEnemies = 10;
		MaxNumberOfPickUps = 10;
		fadeAlpha = 1.0f;
		fadeTimer = 0l;

		this.gameState = gameState;

		Enemies = new ArrayList<Bug>();
		pickUps = new ArrayList<PickUp>();
	}
	/**
	 * config camera, map, player and load assets
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

		// distribute textures & create Entities
		map = new Tilemap(assets.holder.get(Assets.currentMap), camera);
		player = new Player(assets.holder);
		// set position to the center of the map
		player.setPosition(625.f, 465.f);

		debugger = new AntiBug(assets.holder);

		music = new BackgroundMusic(assets.holder.get(Assets.background));

		// if there is already a lightBuffer, reset
		if(lightBuffer != null)
			lightBuffer.dispose();
		// create FrameBuffer with width/height of the screen
		lightBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, (int)WORLDBOUNDS.x, (int)WORLDBOUNDS.y, false);
		lightBuffer.getColorBufferTexture().setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

		lightBufferRegion = new TextureRegion(lightBuffer.getColorBufferTexture(), 0, 0, 1280, 960);
		lightBufferRegion.flip(false, true);

		gameState = GameState.START;
		// start timer for the entry animation
		startTimer.startWatch();
		fadeTimer = startTimer.getRemainingMilliSeconds();
	}
	/**
	 * prepare the game session
	 * spawns pickups and enemies, play background music and set the player alive
	 */
	private void startGameSession()
	{
		player.isAlive = true;
		debugger.spawn(40, 40);

		spawnEnemies();
		spawnPickUps();

		music.setloop(true);
		music.setVolume(0.5f);
		music.play();music.setloop(true);

		timer.startWatch();
	}
	/**
	 * updates and handles the game logic in dependency of the current
	 * gamestate
	 * @param Delta : elapsed time since last frame
	 * @return : the current gamestate
	 */
	public GameState update(float Delta)
	{
		switch(gameState)
		{
			case START:
				startUpdate();
				break;
			case RUN:
				runUpdate(Delta);
				break;
			default:
		}

		return gameState;
	}

	/**
	 * handles the entry animations including fading in effects etc.
	 */
	private void startUpdate()
	{
		startTimer.update();

		// change the alphablending over the first 5 seconds
		if(startTimer.isActive && startTimer.getRemainingSeconds() > 5 && fadeAlpha > 0.f)
		{
			if(fadeTimer >= (startTimer.getRemainingMilliSeconds() + 50l))
			{
				fadeTimer = startTimer.getRemainingMilliSeconds();
				fadeAlpha -= 0.01;
			}
		}
		else if(startTimer.isActive && startTimer.getRemainingSeconds() <= 5)
			player.isAlive = true;

		if(startTimer.isActive && startTimer.getRemainingSeconds() <= 3)
			fadeAlpha = 1.f;

		// if time is over start the game session and allow user manipulation
		if(!startTimer.isActive) {
			startGameSession();
			gameState = GameState.RUN;
		}
	}
	/**
	 * updates the game logic
	 * all update functions to be called
	 * between handlePlayerInput() and checkCollisions()
	 * @param Delta : elapsed time since last frame
	 */
	private void runUpdate(float Delta)
	{
		handlePlayerInput();
		player.update(Delta);
		debugger.update(Delta);


		for(int i = 0; i < MaxNumberOfEnemies; i++)
		{
			Enemies.get(i).update(Delta, player);
		}
		updatePickUps();
		userInterface.update(player.getPickUpCounter());

		checkCollisions();

		timer.update();
		if(!timer.isActive)
			gameState = GameState.LOOSE_TIMEOUT;
		if(!player.isAlive)
			gameState = GameState.LOOSE_CAUGHT;
	}
	/**
	 * manages all render calls inside the world
	 */
	public void render(float Delta)
	{

		switch(gameState)
		{
			case START:
				startRender(Delta);
				break;
			case RUN:
				runRender(Delta);
				break;
			default:
		}
	}
	/**
	 * handles the drawing of the entry animation
	 * @param Delta : elapsed time since last frame
	 */
	private void startRender(float Delta)
	{
		// Clear the Screen
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// update camera
		camera.update();
		// render the map
		map.render();

		// set the projection matrix of the ScreenBatch to camera's size
		batch.setProjectionMatrix(camera.combined);

		Gdx.gl.glEnable(Gdx.gl.GL_BLEND);
		Gdx.gl.glBlendFunc(Gdx.gl.GL_SRC_ALPHA, Gdx.gl.GL_ONE_MINUS_SRC_ALPHA);
		shapeRenderer.setColor(0, 0, 0, fadeAlpha);
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		shapeRenderer.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		shapeRenderer.end();
		Gdx.gl.glDisable(Gdx.gl.GL_BLEND);

		batch.begin();
		player.draw(batch, Delta);
		batch.end();

	}
	private void runRender(float Delta)
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


		// render blending into the second FrameBuffer
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
	 * free all resources
	 */
	public void dispose()
	{
		assets.dispose();
		userInterface.dispose();
		lightBuffer.dispose();
		shapeRenderer.dispose();
		batch.dispose();
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

			// !!! buggy !!!
			if(player.isCollided(Enemies.get(i)) && !player.isSaved()) {
				player.isAlive = false;
				player.stopMusic(music);
				player.playSound(assets.holder.get(Assets.lose), false);
			}
		}

		// check debugger collision
		debugger.isCollided(map);

		// check pickup collision
		for(int i = 0; i < PickUp.pickUpCounter; i++)
			if(player.isCollided(pickUps.get(i))) {
				player.collect(pickUps.get(i));
				player.playSound(assets.holder.get(Assets.blop), false);
			}
		
		// check debugger collision 
		if (player.isCollided(debugger)) {
			player.Save();
			player.playSound(assets.holder.get(Assets.save), true);
		}
	}
	/**
	 * spawn the maximum amount of enemies given by MaxNumberOfEnemies
	 */
	void spawnEnemies()
	{

		Random random = new Random();

		for(int i = 0; i < MaxNumberOfEnemies ; i++) {
			Enemies.add(new Bug(assets.holder.get(Assets.enemy)));

			do {
				if(i < MaxNumberOfEnemies / 2)
					Enemies.get(i).spawn(random.nextInt((int)(WORLDBOUNDS.x / 3)), random.nextInt((int)WORLDBOUNDS.y) );
				else
					Enemies.get(i).spawn((int)(WORLDBOUNDS.x / 3 * 2) + random.nextInt((int)(WORLDBOUNDS.x / 3)), random.nextInt((int)WORLDBOUNDS.y) );
			} while(map.isCollided(Enemies.get(i)));
		}
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
