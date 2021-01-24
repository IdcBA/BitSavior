package com.bitsavior.game;

import com.badlogic.gdx.graphics.Texture;

public class MovingEntity extends Entity
{

    Movement direction;

    public MovingEntity(Texture texture, float velocity)
    {
        super(texture, velocity);
        direction = Movement.UNMOVED;

    }

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

    protected void moveBack(float Delta)
    {
        move(-1, Delta);
    }


}
