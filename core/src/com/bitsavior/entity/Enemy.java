package com.bitsavior.entity;

import com.badlogic.gdx.graphics.Texture;
import com.bitsavior.ai.EnemyAI;
import com.bitsavior.collision.ICollision;
import com.bitsavior.map.Tilemap;

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
     * normal velocity
     */
    final float nVelocity;
    /**
     * controls the enemies behaviour
     */
    private EnemyAI brain;

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
     * @param velocity : velocity of the enemy
     */
    public Enemy(Texture texture, float viewRange, float velocity)
    {
        super(texture, velocity);

        nVelocity = velocity;

        maxWalkingDistance = 100.f;
        isAlive = false;
        this.viewRange = viewRange;

        walkDistance = 0.f;


        // set size
        sprite.setSize(25, 25);

        brain = new EnemyAI(this, viewRange);
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
     * updates position, direction and artifical intelligence
     * @param Delta : elapsed time since last frame
     * @param player : player data for the artifical intelligence
     * @param map : map data for the artifical intelligence
     */
    public void update(float Delta, Entity player, Tilemap map)
    {
        brain.update();
        if(brain.isEntityInRange(player)) {
            System.out.println("inRange");
            velocity = nVelocity * 2.f;
        }
        else
           velocity = nVelocity;

        // testing
        if(walkDistance >= maxWalkingDistance)
            chooseDirection();
        move(1, Delta);

        // add covered distance of the frame
        walkDistance += velocity * Delta;

    }

    public float getViewRange() { return viewRange; }

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
