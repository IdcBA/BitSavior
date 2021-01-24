package com.bitsavior.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

public class Enemy
        extends MovingEntity
        implements ICollision
{
    // private Members
    /**
     * responsible for steering behavior and general A.I
     */
    ArtificalIntelligence brain;

    /**
     * view range of the enemy in units
     */
    private float viewRange;

    /**
     * move direction of the enemy
     */
    private Movement direction;

    /**
     * maximum walk distance before change of direction
     */
    private final float maxWalkingDistance;

    /**
     * walked worldunits before direction change
     */
    private float walkDistance;


    boolean collision;
    // public Methods

    /**
     * Constructor()
     * @param texture : texture of the enemy
     * @param viewRange : viewRange in worldunits
     * @param collisionLayer : object layer of the map for the artifical intelligence
     */
    public Enemy(Texture texture, float viewRange, float velocity, MapLayer collisionLayer)
    {
        super(texture, velocity);

        brain = new ArtificalIntelligence(collisionLayer, viewRange);

        maxWalkingDistance = 100.f;
        isAlive = false;
        this.viewRange = viewRange;

        walkDistance = 0.f;


        collision = false;
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
        setCurrentMovement(Movement.RIGHT);
    }

    /**
     * update enemies game logic
     */
    public void update(float Delta)
    {
        // testing

        move(Delta);
        walkDistance += (velocity * Delta) / 2;

          if(walkDistance >= maxWalkingDistance || collision == true) {
            collision = false;
            chooseDirection();
        }


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
                setCurrentMovement(Movement.LEFT);
                break;
            case 1:
                setCurrentMovement(Movement.RIGHT);
                break;
            case 2:
                setCurrentMovement(Movement.UP);
                break;
            case 3:
                setCurrentMovement(Movement.DOWN);
                break;
            default:
                System.out.println("fail");

        }
    }

}
