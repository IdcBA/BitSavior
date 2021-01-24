package com.bitsavior.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

public class Enemy
        extends MovingEntity
        implements ICollision
{
    // private Members
    /**
     * view range of the enemy in units
     */
    private float viewRange;


    /**
     * maximum walk distance before change of direction
     */
    private final float maxWalkingDistance;

    /**
     * walked worldunits before direction change
     */
    private float walkDistance;


    // public Methods

    /**
     * Constructor()
     * @param texture : texture of the enemy
     * @param viewRange : viewRange in worldunits
     */
    public Enemy(Texture texture, float viewRange, float velocity)
    {
        super(texture, velocity);

        maxWalkingDistance = 100.f;
        isAlive = false;
        this.viewRange = viewRange;

        walkDistance = 0.f;


        // set size
        sprite.setSize(25, 25);
    }

    /**
     * set the enemy alive and spawn it at the given position
     * @param x : x-coordinate(spawn)
     * @param y : y-coordinate(spawn)
     */
    public void spawn(float x, float y)
    {
        // testing
        isAlive = true;
        sprite.setPosition(x, y);
        chooseDirection();
    }

    /**
     * update enemies game logic
     */
    public void update(float Delta)
    {
        // testing
        if(walkDistance >= maxWalkingDistance)
            chooseDirection();
        move(1, Delta);

        // add covered distance of the frame
        walkDistance += velocity * Delta;
    }

    public boolean isCollided(Entity entity)
    {
        // get the sprites bounding rectangle
        Rectangle boundaries = new Rectangle(sprite.getBoundingRectangle());

        // check if the boundaries collided and return
        return boundaries.overlaps(entity.sprite.getBoundingRectangle());
    }


    // private Methods
    /**
     * set move direction of the enemy randomly
     */
    private void chooseDirection()
    {
        Random random = new Random();

        // reset walked distance
        walkDistance = 0.f;

        switch(random.nextInt(4))
        {
            case 0:
                this.direction = Movement.LEFT;
                break;
            case 1:
                this.direction = Movement.RIGHT;
                break;
            case 2:
                this.direction = Movement.UP;
                break;
            case 3:
                this.direction = Movement.DOWN;
                break;
            default:
                System.out.println("fail");

        }
    }

}
