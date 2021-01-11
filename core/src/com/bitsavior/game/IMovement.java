package com.bitsavior.game;

/**
 * Interface for Movement of Entities
 */
public interface IMovement
{
    enum Direction
    {
        LEFT,
        RIGHT,
        UP,
        DOWN,
        UNMOVED
    }

    void move(Direction direction, int inversion);
}
