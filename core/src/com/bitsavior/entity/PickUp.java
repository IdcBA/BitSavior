package com.bitsavior.entity;

import com.badlogic.gdx.graphics.Texture;
import com.bitsavior.entity.Entity;


public class PickUp extends Entity
{
    // static Members

    /**
     * counts the amount of existing pickups
     */
    public static int pickUpCounter;

    static {
        pickUpCounter = 0;
    }


    // public Methods
    public PickUp(Texture texture)
    {
        super(texture);

        isAlive = false;

        // set size of the pickup
        sprite.setSize(25, 25);

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
        sprite.setPosition(x, y);
    }
}
