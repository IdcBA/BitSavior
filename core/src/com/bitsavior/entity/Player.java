package com.bitsavior.entity;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bitsavior.asset.Assets;
import com.bitsavior.game.Watch;

/**
 * represents the Player
 * including functionality for collecting of pickups
 */
public class Player
        extends MovingEntity
{
    /**
     * counts the collected pickups
     */
    private int pickUpCounter;


    private LightSource flashlight;
    private Watch saveTime;
	
    /**
     * Constructor
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
        setSize(30, 30);
		
    }

    /**
     * updates players position on the screen
     * @param Delta : elapsed time since last frame
     */
    public void update(float Delta)
    {
        if(!this.isAlive)
            System.out.println("Hit");
        
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

    public void drawFlashlight(final SpriteBatch batch, float Delta)
    {
        if(isAlive)
            flashlight.draw(batch, Delta);
    }

    public int getPickUpCounter() { return pickUpCounter; }


	public void Save() {
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



