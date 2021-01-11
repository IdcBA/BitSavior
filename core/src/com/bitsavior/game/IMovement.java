package com.bitsavior.game;

/**
 * Interface for Movement of Entities
 */
public interface IMovement
{
    /**
     * Movement Direction
     */
    enum Direction
    {
        LEFT,
        RIGHT,
        UP,
        DOWN,
        UNMOVED
    }

    /**
     * move the entity
     * @param inversion : 1 for normal movement, -1 for inversed movement
     */
    void move(int inversion);
}
