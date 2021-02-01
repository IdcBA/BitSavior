package com.bitsavior.collision;

import com.bitsavior.entity.Entity;

/**
 * Interface for collision detection between two Entities
 * @author Valentin Zirngibl
 */
public interface ICollision
{
    /**
     * Check if the entity is collided with the passed one
     * @param entity : collision to be checked with
     * @return : returns true if a collision happened
     */
    boolean isCollided(Entity entity);
}
