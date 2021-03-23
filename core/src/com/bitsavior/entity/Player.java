package com.bitsavior.entity;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bitsavior.asset.Assets;
import com.bitsavior.game.Watch;

/**
 * represents the Player
 * including functionality for collecting of pickups
 * and extends a moving entity
 */
public class Player
        extends MovingEntity
{
    /**
     * counts the collected pickups
     */
    private int pickUpCounter;
    /**
     * a lightsource for the light effect
     */
    private LightSource flashlight;
    /**
     * timer for the player invincibility after touches the debugger
     */
    private Watch saveTime;
    /**
     * constructor
     * @param manager : assetHolder for player and flashlight assets
     */
    public Player(final AssetManager manager) {

        // call constructor of parent class and pass parameters for animation
        super(manager.get(Assets.player), 200.f, 4, 4, 0.05f);


        flashlight = new LightSource(manager.get(Assets.light));
        flashlight.attach(this);


        direction = Movement.UNMOVED;

        pickUpCounter = 0;

        // set player size
        setSize(25, 25);
		
    }
    /**
     * updates players position on the screen
     * @param Delta : elapsed time since last frame
     */
    @Override
    public void update(float Delta)
    {

        super.update(Delta);
        move(1, Delta);

        flashlight.update();

    }
    /**
     * collect the given pickup
     * @param pickUp : pickup that should be collected from the player
     */
    public void collect(PickUp pickUp)
    {
        pickUp.isAlive = false;
        pickUpCounter++;
    }
    /**
     * draws the lightsource if the entity is alive
     * @param batch : current Spritebatch
     * @param Delta : elapsed time since last frame in milliseconds
     */
    public void drawFlashlight(final SpriteBatch batch, float Delta) {
        if (isAlive)
            flashlight.draw(batch, Delta);
    }

    /**
     * gets the current number of collected pickups
     * @return : number of currently collected pickups
     */
    public int getPickUpCounter() { return pickUpCounter; }


	public void save() {
		saveTime = new Watch(10);
		saveTime.startWatch();
	}
	
	public boolean isSaved() {
		if(saveTime == null) 
			return false;
		saveTime.update();
		return saveTime.isActive();
	}
}



