package com.bitsavior.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * A class that represents a conic light.
 * Can be attached to different entities
 * @author Valentin Zirngibl
 */
public class LightSource extends Entity
{
    /**
     * Reference to the entity this lightsource is attached to
     */
    private Entity parent;
    /**
     * Color(r,g,b,a) that determines how the light is tinted
     */
    private Color color;
    /**
     * Describes the warmth of the light
     */
    public enum TYPE
    {
        COLD,
        WARM
    }
    /**
     * Constructor passes the lights texture to the base class and
     * sets the color to default
     * @param texture : texture of the lightsource
     */
    public LightSource(Texture texture)
    {
        super(texture);
        parent = null;
        color = new Color(0.7f, 0.7f, 0.8f, 0.99f);

    }
    /**
     * Attaches this lightsource to an entity to synchronize positions
     * @param entity : Related entity
     */
    public void attach(Entity entity)
    {
        parent = entity;
    }
    /**
     * Updates the position of this lightsource with the position of the parent entity, if attached
     */
    public void update()
    {
        if(parent != null)
        {
            setPosition((parent.getPosition().x + (parent.getSize().x / 2)) - (getSize().x / 2),
                    (parent.getPosition().y + (parent.getSize().y / 2)) - (getSize().y / 2));
        }
    }
    /**
     * Sets the type of light tinting
     * @param colorType : Type of light tinting
     */
    public void setColor(TYPE colorType)
    {
        switch(colorType)
        {
            case COLD:
                color.set(0.7f, 0.7f, 0.8f, 0.99f);
                break;
            case WARM:
                color.set(0.83f, 0.76f, 0.65f, 0.99f);
                break;
            default:
        }

    }
    /**
     *  Draws the light source with the proper light tinting
     *  Note that this function have to be called inside the lightBuffer rendering to
     *  work properly
     * @param batch : Current SpriteBatch for drawing
     * @param Delta : Elapsed time since last frame
     */
    public void draw(SpriteBatch batch, float Delta)
    {
        batch.setColor(color);

        super.draw(batch, Delta);
    }










}
