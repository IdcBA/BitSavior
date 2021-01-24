package com.bitsavior.game;

import com.badlogic.gdx.graphics.Texture;

/**
 * specifies the moving functionality for moving entities
 */
public class MovingEntity extends Entity
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
     * move in the opposite direction after a collision
     * @param Delta : elapsed time since last frame
     */
    protected void moveBack(float Delta)
    {
        move(-1, Delta);
    }
}
