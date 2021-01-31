package com.bitsavior.entity;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bitsavior.asset.Assets;

/**
 * represents the Player
 * including functionality for collecting of pickups
 */
public class Player
        extends MovingEntity
{
    // private Methods
    /**
     * counts the collected pickups
     */
    private int pickUpCounter;


    private LightSource flashlight;
	
    /**
     * Constructor
     * @param manager : assetHolder for player and flashlight assets
     */
    public Player(AssetManager manager) {

        super(manager.get(Assets.player), 200.f, 4, 4, 0.05f);


        flashlight = new LightSource(manager.get(Assets.light));
        flashlight.attach(this);

        isAlive = true;

        direction = Movement.UNMOVED;

        pickUpCounter = 0;

        // set player size
        sprite.setSize(35, 35);
		
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
        System.out.println(pickUpCounter);
    }

    public void drawFlashlight(SpriteBatch batch, float Delta)
    {
        if(isAlive)
            flashlight.draw(batch, Delta);
    }

    public int getPickUpCounter() { return pickUpCounter; }

}



