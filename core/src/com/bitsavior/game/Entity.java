package com.bitsavior.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
     * holds the players texture
     */
    protected Sprite sprite;
    /**
     * lifestate of the entity
     */
    protected boolean isAlive;

    /**
     * Constructor()
     * @param texture : texture for the entity
     * @param velocity : velocity of the entity
     */
    // public Methods
    public Entity(Texture texture, float velocity)
    {
        this.velocity = velocity;
        sprite = new Sprite(texture);
    }

    /**
     * sets the position of the entity
     * @param x : horizontal position of the Player
     * @param y : vertical position of the Player
     */
    public void setPosition(float x, float y) { sprite.setPosition(sprite.getX() + x,sprite.getY() + y); }

    /**
     * getPosition()
     * @return : current position as worldunits
     */
    public Vector2 getPosition() { return new Vector2(sprite.getX(), sprite.getY()); }

    /**
     * getSize()
     * @return : size of the Object(Width, Height)
     */
    public Vector2 getSize() { return new Vector2(sprite.getWidth(), sprite.getHeight()); }

    /**
     * draw()
     * @param batch : current SpriteBatch for drawing
     */
    public void draw(SpriteBatch batch)
    {
        batch.draw(sprite,sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
    }

}
