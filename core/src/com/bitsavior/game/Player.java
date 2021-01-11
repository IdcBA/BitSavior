package com.bitsavior.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;

/**
 * represents the Player
 */
public class Player
        extends Entity
        implements IMovement, ICollision
{
    // public Methods
    /**
     * move direction of the player
     */

    Direction direction;

    /**
     * Constructor
     *
     * @param texture : players texture
     */
    public Player(Texture texture, float velocity) {
        // call constructor of Entity and set texture
        super(texture, velocity);


        isAlive = true;

        direction = Direction.UNMOVED;

        // set player size
        sprite.setSize(10, 10);

    }


    public void update()
    {
        move(1);
    }

    public void move(int inversion) {
        switch (direction) {
            case LEFT:
                setPosition(inversion * -velocity * Gdx.graphics.getDeltaTime(), 0);
                this.direction = Player.Direction.LEFT;
                break;
            case RIGHT:
                setPosition(inversion * velocity * Gdx.graphics.getDeltaTime(), 0);
                this.direction = Player.Direction.RIGHT;
                break;
            case UP:
                setPosition(0, inversion * velocity * Gdx.graphics.getDeltaTime());
                this.direction = Player.Direction.UP;
                break;
            case DOWN:
                setPosition(0, inversion * -velocity * Gdx.graphics.getDeltaTime());
                this.direction = Player.Direction.DOWN;
                break;
            default:

        }

    }


    public boolean isCollided(Entity entity) {
        // get the sprites bounding rectangle
        Rectangle boundaries = new Rectangle(sprite.getBoundingRectangle());

        // check if the boundaries collided and return
        return boundaries.overlaps(entity.sprite.getBoundingRectangle());
    }


    public boolean isCollided(MapLayer collisionLayer) {
        // get all objects out of the collision layer
        MapObjects objects = collisionLayer.getObjects();
        // get the sprites bounding rectangle
        Rectangle boundaries = new Rectangle(sprite.getBoundingRectangle());

        // iterate through all objects and check for collision
        for (RectangleMapObject rectangleObject : objects.getByType(RectangleMapObject.class)) {
            if (boundaries.overlaps(rectangleObject.getRectangle()))
                return true;
        }
        return false;
    }
}



