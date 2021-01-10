package com.bitsavior.game;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

/**
 * Interface for collision detection between two Entities
 */
public interface ICollision
{
    /**
     * check if the entity is collided with the passed one
     * @param entity : collision to be checked with
     * @return : returns true if a collision happened
     */
    boolean isCollided(Entity entity);

    /**
     *  check if the entity is collided with the environment
     * @param collisionLayer : collision layer of the TiledMap
     * @return : returns true if a collision happened
     */
    boolean isCollided(TiledMapTileLayer collisionLayer);
}
