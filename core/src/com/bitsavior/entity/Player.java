package com.bitsavior.entity;

import com.badlogic.gdx.graphics.Texture;

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
	
    /**
     * Constructor
     * @param texture : players texture
     * @param velocity : velocity of the player
     */
    public Player(Texture texture, float velocity) {

        super(texture, velocity, 4, 4, 0.05f);

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
}



