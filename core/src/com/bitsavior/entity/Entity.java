package com.bitsavior.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * A class contains the basic functionality for any entity in game
 * Note that everything that is shown on the display has to be at
 * least derived from entity
 * @author Valentin Zirngibl
 */
public class Entity
{
    // protected Members
    /**
     * Velocity of the entity
     */
    protected float velocity = 0.f;
    /**
     * Holds the entity's texture that is to be rendered
     */
    protected Sprite sprite;
    /**
     * True if the entity is alive and to be drawn
     * false if not
     */
    public boolean isAlive;
    /**
     * Constructor
     * @param texture : Texture for this entity
     */
    // public Methods
    public Entity(Texture texture)
    {
        sprite = new Sprite(texture);

    }
    /**
     * Constructor that also sets the velocity at creation time
     * @param texture : Texture for this entity
     * @param velocity : Velocity of this entity
     */
    public Entity(Texture texture, float velocity)
    {
        this.velocity = velocity;
        sprite = new Sprite(texture);
    }

    /**
     * Copy constructor
     * @param entity : Entity to be copied
     */
    public Entity(Entity entity)
    {
        this.velocity = entity.velocity;
        this.sprite = new Sprite(entity.sprite);
        isAlive = false;
    }
    /**
     * Adds the given x and y values to the current position
     * @param x : Horizontal change of the entities position
     * @param y : Vertical change of the entities position
     */
    protected void updatePosition(float x, float y) { sprite.setPosition(sprite.getX() + x,sprite.getY() + y); }
    /**
     * Sets the position of the entity
     * @param x : Horizontal position of the Player
     * @param y : Vertical position of the Player
     */
    public void setPosition(float x, float y) { sprite.setPosition(x, y); }
    /**
     * Sets the size of the sprite
     * @param width : Width of the sprite
     * @param height : Height of the sprite
     */
    public void setSize(float width, float height) { sprite.setSize(width, height); }
    /**
     * Sets the current velocity
     * @param velocity : Velocity of the entity
     */
    public void setVelocity(float velocity){ this.velocity = velocity; }
    /**
     * GetPosition()
     * @return : Current position as vector
     */
    public Vector2 getPosition() { return new Vector2(sprite.getX(), sprite.getY()); }
    /**
     * Gets the size of this entity
     * @return : Size of the Object as vector
     */
    public Vector2 getSize() { return new Vector2(sprite.getWidth(), sprite.getHeight()); }
    /**
     * Gets the bounding rectangle of the entity
     * @return : bounding rectangle
     */
    public Rectangle getBoundings() { return new Rectangle(sprite.getBoundingRectangle()); }
    /**
     * Draws the sprite to the screen.
     * Note that this function needs to be called either after the first batch.begin after the drawing of the map
     * or after the drawing of the lightBuffer
     * @param batch : Current SpriteBatch for drawing
     * @param Delta : Elapsed time since last frame
     */
    public void draw(SpriteBatch batch, float Delta)
    {
        batch.draw(sprite,sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
    }

}
