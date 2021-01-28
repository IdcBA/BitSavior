package com.bitsavior.entity;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
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

    private Sprite flashlight;
	
    /**
     * Constructor
     * @param texture : players texture
     * @param velocity : velocity of the player
     */
    public Player(AssetManager manager, float velocity) {

        super(manager.get(Assets.player), velocity, 4, 4, 0.05f);

        flashlight = new Sprite(manager.get(Assets.flashlight));

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


        flashlight.setPosition((getPosition().x + (getSize().x / 2)) - (flashlight.getWidth() / 2),
                (getPosition().y + (getSize().y / 2)) - (flashlight.getHeight() / 2));
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
        // set the color for the flashlight cone
        batch.setColor(0.7f, 0.7f, 0.8f, 0.99f);
        if(isAlive)
            batch.draw(flashlight,flashlight.getX(),flashlight.getY(), flashlight.getWidth() , flashlight.getHeight() );
    }
}



