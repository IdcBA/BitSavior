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
import com.bitsavior.screens.AppPreferences;

import java.util.ArrayList;
import java.util.Random;

/**
 * represents the world of the game
 */
public class World
{

	/**
	 * trigger for one time adjustments regarding the translation between run and win gamestate
	 */
	boolean winEffectTrigger = true;
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
	 * the timer for the time limit of the level
	 */
	private final Watch gameTimer;
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
	 * holds the music when the player wins the level
	 */
	private Soundeffect winSound;
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
	 * sound Volume [0,1]
	 */
	private float soundVolume;
	/**
	 * music Volume [0,1]
	 */
	private float musicVolume;
	/**
	 * firework animation for exploding bugs
	 */
	private ArrayList<MovingEntity> fireworks;
	/**
	 * initialise required data for the creation of the world
	 * @param gameState : initialises the gamestate once, to ensure the correct behaviour
	 * @param level : the actual level of the game
	 */
	public World(GameState gameState, int level)
	{
		//load volumes from Preferences
		soundVolume = (float)AppPreferences.getSoundVolume() / 100f;
		musicVolume = (float)AppPreferences.getMusicVolume() / 100f;

		// time limit for the game session
		gameTimer = new Watch(105 - (level * 5));
		// time limit for the entry animation
		gameStateTimer = new Watch(10);

		assets = new Assets();
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		shapeRenderer = new ShapeRenderer();

		// setup numbers for enemies and pickups
		NumberOfEnemies = 5 + level;
		//NumberOfPickUps = 7 + level;
		NumberOfPickUps = 1;

		// setup numbers for various fade effects
		fadeAlpha = 1.0f;
		fadeTimer = 0L;
		fadeVolume = 0f;

		// show only the entry animation if it is level 1
		if(level != 1)
			this.gameState = GameState.START;
		else
			this.gameState = gameState;

		ListOfEnemies = new ArrayList<Bug>();
		ListOfPickUps = new ArrayList<PickUp>();
		fireworks = new ArrayList<MovingEntity>();
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

		userInterface = new UserInterface(assets.holder, gameTimer, NumberOfPickUps);

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

		// if level 1 is active start the entry animation
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
		debugger.spawn(55, 465);

		spawnEnemies();
		spawnPickUps();

		lights.create();

		music.setVolume(0.5f);
		music.play();
		music.setloop(true);

		// start timer for the game session
		gameTimer.startWatch();

		// reset alpha fading to normal for later animations
		fadeAlpha = 1.f;

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
				startUpdate(Delta);
				break;
			case RUN:
				runUpdate(Delta);
				break;
			case LOOSE:
				looseUpdate(Delta);
				break;
			case WIN:
				winUpdate(Delta);
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
	private void startUpdate(float Delta)
	{
		// fade in graphics
		if(gameStateTimer.isActive() && gameStateTimer.getRemainingSeconds() > 5 && fadeAlpha > 0.f)
		{
			if(fadeTimer >= (gameStateTimer.getRemainingMilliSeconds() + 50L))
			{
				fadeTimer = gameStateTimer.getRemainingMilliSeconds();
				fadeAlpha -= 0.5 * Delta;
				fadeVolume += 0.5 * Delta;
			}
		}
		// set player alive
		else if(gameStateTimer.isActive() && gameStateTimer.getRemainingSeconds() <= 5)
			player.isAlive = true;

		// darken the screen
		if(gameStateTimer.isActive() && gameStateTimer.getRemainingSeconds() <= 3)
			fadeAlpha = 1.f;

		// if time is over start the game session and allow user manipulation
		if(!gameStateTimer.isActive())
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

			// if player is saved by the debugger, pause music and play sirene sound
			if(player.isSaved())
			{
				debugger.playSound();
				music.pause();
				lights.sirene(true);
			}
			else
				music.play();

			// if player is loosing, start lose animation
			if (!gameTimer.isActive() || !player.isAlive) {
				gameState = GameState.LOOSE;
				gameStateTimer.reset(6);
				gameStateTimer.startWatch();
				music.setStuttering(0.3f);
				lights.changeEffect(LightedEntity.EffectType.DEACTIVATE);
			}
			// if player is win, start win animation
			else if(player.getPickUpCounter() == NumberOfPickUps) {
				gameState = GameState.WIN;
				gameStateTimer.reset(18);
				gameStateTimer.startWatch();
				fadeTimer = gameStateTimer.getRemainingMilliSeconds();

				// for all remaining bugs, create a firework and set the position to position of the bugs
				for(Bug remainingBug : ListOfEnemies)
				{
					fireworks.add(new MovingEntity(assets.holder.get(Assets.explosion), 0.f, 5, 1, 0.1f));
					fireworks.get(fireworks.size() - 1).setSize(90, 120);
					// dont look at me, i am ugly(sets the position of the firework correctly to the position of the bug)
					fireworks.get(fireworks.size() - 1).setPosition(remainingBug.getCenter().x - (fireworks.get(fireworks.size() - 1).getSize().x / 2), remainingBug.getCenter().y - (fireworks.get(fireworks.size() - 1).getSize().y / 2 ));
				}
				// stop the sirene sound
				lights.sirene(false);
				music.play();
			}
	}

	/**
	 * updates the game logic when players looses the game
	 * @param Delta : elapsed time since last frame
	 */
	private void looseUpdate(float Delta)
	{
		if(!gameStateTimer.isActive())
			gameState = GameState.LOOSE_SHUTDOWN;

		music.update();
	}
	private void winUpdate(float Delta)
	{
		winSound = new Soundeffect(assets.holder.get(Assets.winMusic));


		for(MovingEntity firework : fireworks)
		{
			firework.update(Delta);
		}

		if(fadeTimer >= (gameStateTimer.getRemainingMilliSeconds() + 50L))
		{
			fadeTimer = gameStateTimer.getRemainingMilliSeconds();
			fadeAlpha -= 0.1 * Delta;
			music.setVolume(music.getVolume() - 0.3f * Delta);

			if(music.getVolume() <= 0.f)
				music.stop();
			if(gameStateTimer.getRemainingSeconds() < 12)
			{
				if(winEffectTrigger) {
					for(Bug bug : ListOfEnemies)
						bug.isAlive = false;
					for(MovingEntity firework : fireworks)
						firework.isAlive = true;
					winSound.play();
					winEffectTrigger = false;
				}
			}
		}

		if(!gameStateTimer.isActive())
			gameState = GameState.WIN_SHUTDOWN;
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
			case WIN:
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
		Gdx.gl.glClearColor(0.f, 0.f, 0.f, fadeAlpha);
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

		for(MovingEntity firework : fireworks)
		{
			firework.draw(batch, Delta);
		}

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
		if(lights!= null) lights.dispose();
		if(winSound!= null) winSound.dispose();

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
	private void spawnEnemies()
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
		/*
		Random random = new Random();

		for(int i = 0; i < NumberOfPickUps; i++)
		{
			ListOfPickUps.add(new PickUp(assets.holder.get(Assets.pickUp)));

			// if a pickup is colliding with the map, repeat with new coordinates
			do {
				ListOfPickUps.get(i).spawn(random.nextInt((int)WorldBounds.WIDTH), random.nextInt((int)WorldBounds.HEIGHT));
			} while(map.isCollided(ListOfPickUps.get(i)));
		}

		 */
		ListOfPickUps.add(new PickUp(assets.holder.get(Assets.pickUp)));
		ListOfPickUps.get(0).spawn(625.f, 420.f);
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

			if (!ListOfEnemies.get(i).isAlive) {
				ListOfEnemies.remove(i);
			}
		}
	}

	public int getRemainingTime() {return gameTimer.getRemainingSeconds(); }

	public int getRemainingBugs() {return ListOfEnemies.size(); }

}
