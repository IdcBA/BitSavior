package com.bitsavior.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

/**
 * represents the Player
 */
public class Player
        extends MovingEntity
        implements ICollision
{

    // private Methods
    private int pickUpCounter;

    /**
     * Constructor
     *
     * @param texture : players texture
     */
    public Player(Texture texture, float velocity) {
        // call constructor of Entity and set texture
        super(texture, velocity);

        setCurrentMovement(Movement.UNMOVED);


        isAlive = true;

        pickUpCounter = 0;

        // set player size
        sprite.setSize(10, 10);

    }


    public void update(float Delta)
    {
        move(Delta);
        setCurrentMovement(Movement.UNMOVED);
    }


    public void collect(PickUp pickUp)
    {
        pickUp.isAlive = false;
        pickUpCounter++;
        System.out.println(pickUpCounter);


    }


    public boolean isCollided(Entity entity) {
        // get the sprites bounding rectangle
        Rectangle boundaries = new Rectangle(sprite.getBoundingRectangle());

        // check if the boundaries collided and return
        return boundaries.overlaps(entity.sprite.getBoundingRectangle());
    }
}



