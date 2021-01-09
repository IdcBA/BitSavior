package com.bitsavior.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;

/**
 * represents the Player
 */
public class Player
        extends Entity
        implements ICollision
{
    // public Methods
    /**
     * Constructor
     * @param texture : players texture
     */
    public Player(Texture texture, float velocity)
    {
        // call constructor of Entity and set texture
        super(texture, velocity);

        isAlive = true;

        // set player size
        sprite.setSize(10, 10);

    }
    public boolean isCollided(Entity entity)
    {
        // get the sprites bounding rectangle
        Rectangle boundaries = new Rectangle(sprite.getBoundingRectangle());

        // check if the boundaries collided and return
        return boundaries.overlaps(entity.sprite.getBoundingRectangle());
    }
    public boolean isCollided(TiledMapTileLayer collisionLayer)
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



}
