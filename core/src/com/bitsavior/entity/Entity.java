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
    protected float velocity = 0.f;

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
     */
    // public Methods
    public Entity(Texture texture)
    {
        sprite = new Sprite(texture);

    }
    public Entity(Texture texture, float velocity)
    {
        this.velocity = velocity;
        sprite = new Sprite(texture);
    }

    public Entity(Entity entity)
    {
        this.velocity = entity.velocity;
        this.sprite = new Sprite(entity.sprite);
        isAlive = false;
    }

    /**
     * add the position of the entity
     * @param x : horizontal position of the Player
     * @param y : vertical position of the Player
     */
    protected void updatePosition(float x, float y) { sprite.setPosition(sprite.getX() + x,sprite.getY() + y); }

    /**
     * sets the position of the entity
     * @param x : horizontal position of the Player
     * @param y : vertical position of the Player
     */
    public void setPosition(float x, float y) { sprite.setPosition(x, y); }
    /**
     * sets the size of the sprite
     * @param width : width of the sprite
     * @param height : height of the sprite
     */
    public void setSize(float width, float height) { sprite.setSize(width, height); }

    /**
     * sets the current velocity
     * @param velocity : velocity of the entity
     */
    public void setVelocity(float velocity){ this.velocity = velocity; }
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
