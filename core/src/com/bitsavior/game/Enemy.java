package com.bitsavior.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

public class Enemy
        extends Entity
        implements ICollision, IMovement
{
    // private Members
    /**
     * view range of the enemy in units
     */
    private float viewRange;

    /**
     * move direction of the enemy
     */
    private Direction direction;

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
        sprite.setSize(50, 50);
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
    public void update()
    {
        // testing
        if(walkDistance >= maxWalkingDistance)
            chooseDirection();
        move(1);
    }

    public boolean isCollided(Entity entity)
    {
        // get the sprites bounding rectangle
        Rectangle boundaries = new Rectangle(sprite.getBoundingRectangle());

        // check if the boundaries collided and return
        return boundaries.overlaps(entity.sprite.getBoundingRectangle());
    }

    public boolean isCollided(MapLayer collisionLayer)
    {
        // get all objects out of the collision layer
        MapObjects objects = collisionLayer.getObjects();
        // get the sprites bounding rectangle
        Rectangle boundaries = new Rectangle(sprite.getBoundingRectangle());

        // iterate through all objects and check for collision
        for(RectangleMapObject rectangleObject : objects.getByType(RectangleMapObject.class))
            if(boundaries.overlaps(rectangleObject.getRectangle()))
                return true;

        return false;
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
                this.direction = Enemy.Direction.LEFT;
                System.out.println("left");
                break;
            case 1:
                this.direction = Enemy.Direction.RIGHT;
                System.out.println("right");
                break;
            case 2:
                this.direction = Enemy.Direction.UP;
                System.out.println("up");
                break;
            case 3:
                this.direction = Enemy.Direction.DOWN;
                System.out.println("down");
                break;
            default:
                System.out.println("fail");

        }
    }


    /**
     * move the enemy
     */
    public void move(int inversion)
    {
        switch(direction)
        {
            case LEFT:
                setPosition(inversion * -velocity * Gdx.graphics.getDeltaTime(), 0);
                break;
            case RIGHT:
                setPosition(inversion * velocity * Gdx.graphics.getDeltaTime(), 0);
                break;
            case UP:
                setPosition(0,inversion * velocity * Gdx.graphics.getDeltaTime());
                break;
            case DOWN:
                setPosition(0,inversion * -velocity * Gdx.graphics.getDeltaTime());
                break;
            default:
        }

        // add covered distance of the frame
        walkDistance += velocity * Gdx.graphics.getDeltaTime();

    }

}
