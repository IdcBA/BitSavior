package com.bitsavior.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class MovingEntity
        extends Entity
{

    private MoveMessage currentMovement;

    public MovingEntity(Texture texture, float velocity)
    {
        super(texture, velocity);

        currentMovement = new MoveMessage(Movement.UNMOVED);
    }

    public void move(float Delta) {
        switch (currentMovement.getDirection()) {
            case LEFT:
                setPosition(currentMovement.getInversion() * (-velocity) * Delta, 0);
                break;
            case RIGHT:
                setPosition(currentMovement.getInversion() * (velocity) * Delta, 0);
                break;
            case UP:
                setPosition(0, currentMovement.getInversion() * (velocity) * Delta);
                break;
            case DOWN:
                setPosition(0, currentMovement.getInversion() * (-velocity) * Delta);
                break;
            default:
        }
    }

    public void setCurrentMovement(Movement direction) {
        if(direction == Movement.BACK)
            this.currentMovement.setInversion(-1);
        else if(direction == Movement.UNMOVED)
            this.currentMovement.setInversion(0);
        else if(direction == Movement.CONTINUE)
            this.currentMovement.setInversion(1);
        else
            this.currentMovement = new MoveMessage(direction);
    }
}
