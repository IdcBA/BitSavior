package com.bitsavior.entity;

import com.badlogic.gdx.graphics.Texture;
import com.bitsavior.ai.EnemyAI;
import com.bitsavior.collision.ICollision;
import com.bitsavior.map.Tilemap;

import java.util.Random;

/**
 * A class that represents the basic enemy of the player
 * @author Valentin Zirngibl
 */
public class Bug
        extends MovingEntity
        implements ICollision
{
    // private Members
    /**
     * View range of the enemy in units
     */
    private float viewRange;
    /**
     * Velocity of the enemy used if nothing is in viewrange
     */
    private final float nVelocity;
    /**
     * Controls the enemies behaviour
     */
    private EnemyAI brain;
    /**
     * Maximum walk distance before change of direction
     */
    private final float maxWalkingDistance = 100.f;
    /**
     * Currently moved distance in worldunits, resets after direction change
     */
    private float walkDistance = 0.f;
    // public Methods
    /**
     * Constructor passes the enemies texture to the base class and
     * attaches the artifical intelligence to this entity
     * @param texture : texture of the enemy
     */
    public Bug(Texture texture)
    {
        super(texture, 100.f, 2, 1, 0.1f);

        nVelocity = velocity;

        isAlive = false;
        this.viewRange = 200.f;

        // set size
        sprite.setSize(25, 25);

        // create the brain for the enemy
        brain = new EnemyAI(this, viewRange);

    }
    /**
     * Spawns the entity at the given position
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
     * Updates position, direction and artifical intelligence
     * @param Delta : elapsed time since last frame
     * @param player : player data for the artifical intelligence
     */
    public void update(float Delta, Entity player)
    {
        brain.update();

        // if player is in range, double the velocity
        if(brain.isEntityInRange(player)) {
            System.out.println("inRange");
            velocity = nVelocity * 2.f;
        }
        else
           velocity = nVelocity;

        // change enemies direction if moved enough
        if(walkDistance >= maxWalkingDistance)
            chooseDirection();
        move(1, Delta);

        // add covered distance of the frame
        walkDistance += velocity * Delta;

    }
    /**
     * Returns the viewranges radius of this enemy
     * @return : viewranges radius
     */
    public float getViewRange() { return viewRange; }
    /**
     * Sets the current viewranges radius
     * @param viewRange : radius of the viewrange
     */
    public void setViewRange(float viewRange) { this.viewRange = viewRange; }
    // private Methods
    /**
     * Sets the move direction randomly out of 4 possible directions
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
