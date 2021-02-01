package com.bitsavior.entity;

import com.badlogic.gdx.graphics.Texture;
import com.bitsavior.entity.Entity;

/**
 * A class that represents a collectible item
 * @author Valentin Zirngibl
 */
public class PickUp extends Entity
{
    // static Members
    /**
     * Counts the amount of existing pickups
     */
    public static int pickUpCounter = 0;
    // public Methods

    /**
     * Constructor passes the texture of the pickup to the base class
     * and increments the pickupCounter
     * @param texture : Texture for this pickup
     */
    public PickUp(Texture texture)
    {
        super(texture);

        isAlive = false;

        // set size of the pickup
        sprite.setSize(25, 25);

        pickUpCounter++;
    }
    /**
     * Spawns the pickup at the given position
     * @param x : x-coordinate(spawn)
     * @param y : y-coordinate(spawn)
     */
    public void spawn(float x, float y)
    {
        isAlive = true;
        sprite.setPosition(x, y);
    }
}
