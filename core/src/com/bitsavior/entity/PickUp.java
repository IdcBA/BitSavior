package com.bitsavior.entity;

import com.badlogic.gdx.graphics.Texture;


/**
 * a pickup that can be collected
 */
public class PickUp extends Entity
{
    /**
     * counts the amount of existing pickups
     */
    public static int pickUpCounter = 0;

    /**
     * constructor
     * @param texture : texture for the representation of the pickup
     */
    public PickUp(final Texture texture)
    {
        super(texture);

        isAlive = false;

        // set size of the pickup
        setSize(25, 25);

        pickUpCounter++;
    }
    /**
     * set the enemy alive and spawn it at the given position
     * @param x : x-coordinate(spawn)
     * @param y : y-coordinate(spawn)
     */
    public void spawn(float x, float y)
    {
        isAlive = true;
        setPosition(x, y);
    }
}
