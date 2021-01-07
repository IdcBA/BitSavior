package com.bitsavior.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class Entity
{

    // public Members
    /**
     * Velocity of the Entity
     */
    public final float velocity;

    // protected Members
    /**
     * Players position
     */
    protected Vector2 position;
    /**
     * holds the players texture
     */
    protected Sprite sprite;

    // public Methods
    public Entity(Texture texture)
    {
        velocity = 50.f;
        position = new Vector2(0.f, 0.f);
        sprite = new Sprite(texture);
    }

    /**
     * setPosition()
     * @param x : horizontal position of the Player
     * @param y : vertical position of the Player
     */
    public void setPosition(float x, float y)
    {
        position.x += x;
        position.y += y;
    }

    /**
     * getPosition()
     * @return : current position as worldunits
     */
    public Vector2 getPosition(){ return position; }

    /**
     * getSize()
     * @return : size of the Object(Width, Height)
     */
    public Vector2 getSize()
    {
        Vector2 size = new Vector2(sprite.getWidth(), sprite.getHeight());

        return size;
    }

}
