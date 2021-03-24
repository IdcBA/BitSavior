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
import com.bitsavior.asset.Assets;
import com.bitsavior.entity.*;
import com.bitsavior.map.Environment;
import com.bitsavior.map.Tilemap;
import java.util.ArrayList;
import java.util.Random;

/**
 * represents the world of the game
 */
public class World
{
	/**
	 * if true: sends dispose message
	 */
	private boolean aWorldTest = false;
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
	private final Watch timer;
	/**
	 * holds the time required for starting a game and animate the entrysequence
	 */
	private final Watch gameStateTimer;
	/**
	 * manages all game-assets
	 */
	private final Assets assets;
	/**
	 * Orthographic Camera:
	 * Shows what we see in the world.
	 * Works with WorldUnits instead of Pixels,
	 * makes it easier to handle different screensizes
	 */
	private final OrthographicCamera camera;
	/**
	 * collecting drawable objects
	 */
	private final SpriteBatch batch;
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
	 * holds the sound effect currently needed
	 */
	private Soundeffect sound;
	/**
	 * class contains the Tiled Map, additional renderer and related data
	 */
	private Tilemap map;
	/**
	 * Player class
	 */
	private Player player;
	/**
	 * debugger that lights its way and helps the player navigate through the level
	 */
	private AntiBug debugger;
	/**
	 * contains all enemies
	 */
	private final ArrayList<Bug> ListOfEnemies;
	/**
	 * maximum Number of Enemies
	 */
	private final int NumberOfEnemies;
	/**
	 * maximum Number of pickups
	 */
	private final int NumberOfPickUps;
	/**
	 * contains all pickups
	 */
	private final ArrayList<PickUp> ListOfPickUps;
	/**
	 * shape renderer for the fading effects
	 */
	private final ShapeRenderer shapeRenderer;
	/**
	 * alpha value used for the fading effect
	 */
	private float fadeAlpha;
	/**
	 * value for coordinating the speed of the fading effect
	 */
	private long fadeTimer;
	/**
	 * value used for Volume during fading
	 */
	private float fadeVolume;
	/**
	 * maintains environmental objects and effects
	 */
	private Environment lights;
	/**
	 * initialise required data for the creation of the world
	 * @param gameState : initialises the gamestate once, to ensure the correct behaviour
	 * @param level : the actual level of the game
	 */
	public World(GameState gameState, int level)
	{
		timer = new Watch(105 - (level * 5));
		// time limit for the entry animation
		gameStateTimer = new Watch(10);
		assets = new Assets();
		batch = new SpriteBatch();
		camera = new OrthographicCamera();

		shapeRenderer = new ShapeRenderer();


		// setup numbers
		NumberOfEnemies = 7 + level;
		NumberOfPickUps = 9 + level;

		fadeAlpha = 1.0f;
		fadeTimer = 0L;
		fadeVolume = 0f;

		if(level != 1)
			this.gameState = GameState.START;
		else
			this.gameState = gameState;

		ListOfEnemies = new ArrayList<Bug>();
		ListOfPickUps = new ArrayList<PickUp>();

	}
	/**
	 * config camera, map, player and load assets
	 */
	public void create() {
		// sets the camera to the world bounds
		camera.viewportWidth  = WorldBounds.WIDTH;
		camera.viewportHeight = WorldBounds.HEIGHT;
		// centers camera at 0,0
		camera.position.set(WorldBounds.WIDTH / 2.f, WorldBounds.HEIGHT / 2.f, 0);
		camera.update();
		// set the projection matrix of the ScreenBatch to camera's size
		batch.setProjectionMatrix(camera.combined);


		// load assets
		assets.load();

		userInterface = new UserInterface(assets.holder, timer, NumberOfPickUps);

		// distribute textures & create Entities
		map = new Tilemap(assets.holder.get(Assets.currentMap), camera);
		player = new Player(assets.holder);
		// set position to the center of the map
		player.setPosition(625.f, 465.f);

		debugger = new AntiBug(assets.holder);

		music = new BackgroundMusic(assets.holder.get(Assets.background));


		lights = new Environment(assets.holder);

		// create FrameBuffer with width/height of the screen
		lightBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, (int)WorldBounds.WIDTH, (int)WorldBounds.HEIGHT, false);
		lightBuffer.getColorBufferTexture().setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

		lightBufferRegion = new TextureRegion(lightBuffer.getColorBufferTexture(), 0, 0, 1280, 960);
		lightBufferRegion.flip(false, true);

		if(gameState == GameState.INITIALIZE)
		{
			// start timer for the entry animation
			gameStateTimer.startWatch();
			fadeTimer = gameStateTimer.getRemainingMilliSeconds();
			gameState = GameState.START;
		}
		else if(gameState == GameState.START)
			startGameSession();
	}
	/**
	 * prepare the game session
	 * spawns pickups and enemies, play background music and set the player alive
	 */
	private void startGameSession()
	{
		player.isAlive = true;
		debugger.spawn(625, 400);

		spawnEnemies();
		spawnPickUps();

		lights.create();

		music.setloop(true);
		music.setVolume(0.5f);
		music.play();
		music.setloop(true);

		// start game timer
		timer.startWatch();

		// reset gameStateTimer for later use
		gameStateTimer.reset(6);

		gameState = GameState.RUN;
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
			case LOOSE:
				looseUpdate(Delta);
				break;
			default:
		}
		// update camera
		camera.update();

		return gameState;
	}

	/**
	 * handles the entry animations including fading in effects etc.
	 */
	private void startUpdate()
	{
		gameStateTimer.update();

		// change the alphablending over the first 5 seconds
		if(gameStateTimer.isActive && gameStateTimer.getRemainingSeconds() > 5 && fadeAlpha > 0.f)
		{
			if(fadeTimer >= (gameStateTimer.getRemainingMilliSeconds() + 50L))
			{
				fadeTimer = gameStateTimer.getRemainingMilliSeconds();
				fadeAlpha -= 0.01;
				fadeVolume += 0.005;
			}
		}
		else if(gameStateTimer.isActive && gameStateTimer.getRemainingSeconds() <= 5)
			player.isAlive = true;

		if(gameStateTimer.isActive && gameStateTimer.getRemainingSeconds() <= 3)
			fadeAlpha = 1.f;

		
		// if time is over start the game session and allow user manipulation
		if(!gameStateTimer.isActive)
			startGameSession();
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
			lights.update();
			updateBugs(Delta);
			updatePickUps();
			userInterface.update();

			checkCollisions();

			if(player.isSaved())
			{
				debugger.playSound();
				music.pause();
				lights.playSirene();
			}
			else
				music.play();

			if (!timer.isActive || !player.isAlive) {
				gameState = GameState.LOOSE;
				gameStateTimer.startWatch();
				music.setStuttering(0.3f);
				lights.changeEffect(LightedEntity.EffectType.DEACTIVATE);
			}
	}

	/**
	 * updates the game logic when players looses the game
	 * @param Delta : elapsed time since last frame
	 */
	public void looseUpdate(float Delta)
	{
		gameStateTimer.update();
		if(!gameStateTimer.isActive)
			gameState = GameState.LOOSE_SHUTDOWN;

		music.update();
	}
	/**
	 * manages all render calls inside the world
	 * @param Delta : elapsed time since last frame
	 */
	public void render(final float Delta)
	{
		// Clear the Screen
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		// render the map
		map.render();

		switch(gameState)
		{
			case START:
				startRender(Delta);
				break;
			case RUN:
			case LOOSE:
				runRender(Delta);
				break;
			default:
		}
	}
	/**
	 * handles the drawing of the entry animation
	 * takes over when GameState = START
	 * Notice: lights must be rendered inside the lightbuffer
	 * @param Delta : elapsed time since last frame
	 */
	private void startRender(float Delta)
	{
		Gdx.gl.glEnable(Gdx.gl.GL_BLEND);
		Gdx.gl.glBlendFunc(Gdx.gl.GL_SRC_ALPHA, Gdx.gl.GL_ONE_MINUS_SRC_ALPHA);
		shapeRenderer.setColor(0, 0, 0, fadeAlpha);
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		shapeRenderer.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		shapeRenderer.end();
		Gdx.gl.glDisable(Gdx.gl.GL_BLEND);

		music.play(fadeVolume);
		batch.begin();
		player.draw(batch, Delta);
		batch.end();
	}
	/**
	 * handles the drawing of the scene in a running game
	 * takes over when GameState = RUN
	 * Notice: lights must be rendered inside the lightbuffer
	 * @param Delta : elapsed time since last frame
	 */
	private void runRender(float Delta)
	{
		// draw objects
		batch.begin();
		for(PickUp pickUp : ListOfPickUps)
			pickUp.draw(batch, Delta);
		for(Bug bug : ListOfEnemies)
			bug.draw(batch, Delta);
		lights.draw(batch, Delta);

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
		lights.drawLight(batch, Delta);
		player.drawFlashlight(batch, Delta);
		debugger.drawFlashlight(batch,Delta);
		batch.end();
		lightBuffer.end();

		// draw second buffer and objects on top that should not be affected by the lightning
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
		if(userInterface!=null) userInterface.dispose();
		if(lightBuffer!=null) lightBuffer.dispose();
		if(music!=null) music.dispose();
		if(sound!=null) sound.dispose();
		if(shapeRenderer!=null) shapeRenderer.dispose();
		if(batch!=null) batch.dispose();
		if(assets!=null) assets.dispose();

		PickUp.pickUpCounter = 0;

		if(aWorldTest) System.out.println("world: world disposed");
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
		for(Bug enemy : ListOfEnemies)
		{
			enemy.isCollided(map);

			if(player.isCollided(enemy))
			{
				if(player.isSaved())
				{
					enemy.isAlive = false;
					sound = new Soundeffect(assets.holder.get(Assets.save));
				}
				else
				{
					player.isAlive = false;
					sound = new Soundeffect(assets.holder.get(Assets.lose));
				}
				sound.play();
			}
		}
		// check debugger collision
		debugger.isCollided(map);

		// check pickup collision
		for(PickUp pickUp : ListOfPickUps)
		{
			if(player.isCollided(pickUp))
			{
				player.collect(pickUp);
				sound = new Soundeffect(assets.holder.get(Assets.blop));
				sound.play();
			}
		}
		// check debugger collision 
		if (player.isCollided(debugger))
			player.save();
	}
	/**
	 * spawn the maximum amount of enemies given by MaxNumberOfEnemies
	 */
	void spawnEnemies()
	{
		Random random = new Random();

		for(int i = 0; i < NumberOfEnemies; i++) {
			ListOfEnemies.add(new Bug(assets.holder.get(Assets.enemy)));

			do {
				if(i < NumberOfEnemies / 2)
					ListOfEnemies.get(i).spawn(random.nextInt((int)(WorldBounds.WIDTH / 3)), random.nextInt((int)WorldBounds.HEIGHT) );
				else
					ListOfEnemies.get(i).spawn((int)(WorldBounds.WIDTH / 3 * 2) + random.nextInt((int)(WorldBounds.WIDTH / 3)), random.nextInt((int)WorldBounds.HEIGHT) );
			} while(map.isCollided(ListOfEnemies.get(i)));
		}

	}
	/**
	 * spawn the maximum amount of pickups given by MaxNumberOfPickUps
	 */
	private void spawnPickUps()
	{
		Random random = new Random();

		for(int i = 0; i < NumberOfPickUps; i++)
		{
			ListOfPickUps.add(new PickUp(assets.holder.get(Assets.pickUp)));

			// if a pickup is colliding with the map, repeat with new coordinates
			do {
				ListOfPickUps.get(i).spawn(random.nextInt((int)WorldBounds.WIDTH), random.nextInt((int)WorldBounds.HEIGHT));
			} while(map.isCollided(ListOfPickUps.get(i)));
		}
	}
	/**
	 * checks if pickups are collected and can be deleted
	 */
	private void updatePickUps()
	{
		for(int i = 0; i < ListOfPickUps.size(); i++)
		{
			if(!ListOfPickUps.get(i).isAlive) {
				ListOfPickUps.remove(i);
				PickUp.pickUpCounter--;
			}
		}
	}
	
	private void updateBugs(float Delta)
	{
		for(int i = 0; i < ListOfEnemies.size(); i++) {

			ListOfEnemies.get(i).update(Delta, player);

			if (ListOfEnemies.get(i).isAlive == false) {
				ListOfEnemies.remove(i);
			}
		}
	}
}
