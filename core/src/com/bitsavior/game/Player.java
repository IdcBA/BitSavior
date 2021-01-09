package com.bitsavior.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class Player
        extends Entity
        implements ICollision
{
    // private Members
    private int health;

    // public Methods
    /**
     * Constructor
     * @param texture : players texture
     */
    public Player(Texture texture, float velocity)
    {
        // call constructor of Entity and set texture
        super(texture, velocity);
        health = 10;

        // set player size
        sprite.setSize(10, 10);

    }
    public boolean isCollided(Entity entity)
    {
        return true;
    }
    public boolean isCollided(TiledMapTileLayer collisionLayer)
    {
        return true;
    }



}
