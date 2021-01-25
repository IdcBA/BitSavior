package com.bitsavior.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.bitsavior.entity.Entity;
import com.bitsavior.game.ICollision;
import com.bitsavior.game.Movement;
import com.bitsavior.game.Tilemap;

/**
 * specifies the moving functionality for moving entities
 */
public class MovingEntity extends Entity implements ICollision
{

    // public members
    /**
     * contains the actual movement direction
     */
    public Movement direction;
    /**
     * constructor
     * @param texture : texture of the entity
     * @param velocity : velocity of the entity
     */
    public MovingEntity(Texture texture, float velocity)
    {
        super(texture, velocity);
        direction = Movement.UNMOVED;
    }

    /**
     * sets the coordinates of the entity accordingly to the given direction
     * @param inversion : 1 for the normal direction, -1 for the inverse direction
     * @param Delta : elapsed time since last frame
     */
    protected void move(int inversion, float Delta) {
        switch (direction) {
            case LEFT:
                setPosition(inversion * -velocity * Delta, 0);
                break;
            case RIGHT:
                setPosition(inversion * velocity * Delta, 0);
                break;
            case UP:
                setPosition(0, inversion * velocity * Delta);
                break;
            case DOWN:
                setPosition(0, inversion * -velocity * Delta);
                break;
            default:
        }
    }
    /**
     * check if enemy collided with the given entity
     * @param entity : collision to be checked with
     * @return : returns true if a collision happened
     */
    public boolean isCollided(Entity entity)
    {
        // get the sprites bounding rectangle
        Rectangle boundaries = new Rectangle(sprite.getBoundingRectangle());

        // check if the boundaries collided and return
        return boundaries.overlaps(entity.sprite.getBoundingRectangle());
    }

    /**
     * check object layer of the map if a collision happened
     * @param map : map with the collideable objects to check
     */
    public void isCollided(Tilemap map)
    {
        // get all objects out of the collision layer
        MapObjects objects = map.getLayer(1).getObjects();
        // get the sprites bounding rectangle
        Rectangle boundaries = new Rectangle(getBoundings());

        // iterate through all objects and check for collision
        for (RectangleMapObject rectangleObject : objects.getByType(RectangleMapObject.class)) {
            if (boundaries.overlaps(rectangleObject.getRectangle()))
            {
                // if a collision happened, correct position
                move(-1, Gdx.graphics.getDeltaTime());
                break;
            }
        }
    }

}
