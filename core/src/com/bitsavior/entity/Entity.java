package com.bitsavior.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Entity
{

    // protected Members
    /**
     * Velocity of the Entity
     */
    protected float velocity;

    // protected Members
    /**
     * holds the entity's texture
     */
    protected Sprite sprite;
    /**
     * lifestate of the entity
     */
    public boolean isAlive;

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
     * get the bounding rectangle of the entity
     * @return : bounding rectangle
     */
    public Rectangle getBoundings() { return new Rectangle(sprite.getBoundingRectangle()); }

    /**
     * draw()
     * @param batch : current SpriteBatch for drawing
     * @param Delta : elapsed time since last frame
     */
    public void draw(SpriteBatch batch, float Delta)
    {
        batch.draw(sprite,sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
    }

}
