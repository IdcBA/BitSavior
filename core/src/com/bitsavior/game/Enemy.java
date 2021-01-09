package com.bitsavior.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import java.util.Random;

public class Enemy
        extends Entity
        implements ICollision
{
    // public Members
    public boolean isAlive;

    // private Members
    /**
     * view range of the enemy in units
     */
    private float viewRange;

    /**
     * move direction of the enemy
     */
    private enum Direction
    {
        LEFT,
        RIGHT,
        UP,
        DOWN
    }
    private Direction direction;

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

        isAlive = false;
        this.viewRange = viewRange;
        walkDistance = 0.f;


        // set size
        sprite.setSize(10, 10);
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

    /**
     * update enemies game logic
     */
    public void update()
    {
        chooseDirection();


    }

    public boolean isCollided(Entity entity)
    {
        return true;
    }

    public boolean isCollided(TiledMapTileLayer collisionLayer)
    {
        return true;
    }

    // private Methods
    /**
     * set move direction of the enemy randomly
     */
    private void chooseDirection()
    {
        Random random = new Random();

        switch(random.nextInt(4))
        {
            case 0:
                direction = Direction.LEFT;
                break;
            case 1:
                direction = Direction.RIGHT;
                break;
            case 2:
                direction = Direction.UP;
                break;
            case 3:
                direction = Direction.DOWN;
                break;
            default:
        }
    }


    /**
     * move the enemy
     */
    private void move()
    {
        switch(direction)
        {
            case LEFT:
                sprite.setPosition(-velocity * Gdx.graphics.getDeltaTime(), 0);
                break;
            case RIGHT:
                sprite.setPosition(velocity * Gdx.graphics.getDeltaTime(), 0);
                break;
            case UP:
                sprite.setPosition(0,velocity * Gdx.graphics.getDeltaTime());
                break;
            case DOWN:
                sprite.setPosition(0,-velocity * Gdx.graphics.getDeltaTime());
                break;
            default:
        }

        // add covered distance of the frame
        walkDistance += velocity * Gdx.graphics.getDeltaTime();

    }

}
