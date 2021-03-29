package com.bitsavior.entity;

import com.badlogic.gdx.graphics.Texture;
import com.bitsavior.ai.EnemyAI;
import com.bitsavior.collision.ICollision;

import java.util.Random;

/**
 * represents an enemy including A.I
 * is a MovingEntity for the movement behaviour and
 * implements a collision interface
 */
public class Bug
        extends MovingEntity
{
    /**
     * view range of the enemy in units
     */
    private float viewRange;
    /**
     * normal velocity that is used if nothing is in viewrange
     */
    private final float nVelocity;
    /**
     * controls the enemies behaviour
     */
    private final EnemyAI brain;
    /**
     * maximum walk distance before change of direction
     */
    private final float maxMovingDistance = 150.f;
    /**
     * walked worldunits before direction change
     */
    private float coveredDistance = 0.f;
    /**
     * constructor
     * @param texture : texture of the enemy
     */
    public Bug(Texture texture)
    {
        super(texture, 90.f, 2, 1, 0.1f);

        nVelocity = velocity;

        isAlive = false;
        this.viewRange = 200.f;

        // set size
        setSize(25, 25);

        // create the brain for the enemy
        brain = new EnemyAI(this, viewRange);

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
        chooseDirection();
    }
    /**
     * updates position, direction and artifical intelligence
     * @param Delta : elapsed time since last frame
     * @param player : player data for the artifical intelligence
     */
    public void update(float Delta, final Entity player)
    {
        super.update(Delta);
        brain.update();

        // if player is in range, double the velocity
        if(brain.isEntityInRange(player)) {
            velocity = nVelocity * 2.f;
        }
        else
           velocity = nVelocity;

        // change enemies direction if moved enough
        if(coveredDistance >= maxMovingDistance || isRecentlyCollided())
            chooseDirection();
        move(1, Delta);

        // add covered distance of the frame
        coveredDistance += velocity * Delta;
    }
    /**
     * return the viewrange of the enemy
     * @return : viewrange of the enemy
     */
    public float getViewRange() { return viewRange; }
    /**
     * sets the current viewrange of the enemy
     * @param viewRange : describes how far the enemy can see
     */
    public void setViewRange(float viewRange) { this.viewRange = viewRange; }
    /**
     * set move direction of the enemy randomly
     */
    private void chooseDirection()
    {
        Random random = new Random();

        // reset walked distance
        coveredDistance = 0.f;

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
        }
    }
}
