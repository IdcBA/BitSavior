package com.bitsavior.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * represents a light source in the game
 * works with textures and alphablending
 */
public class LightSource extends Entity
{
    /**
     * holds a reference to the entity the light is attached to
     */
    private Entity parent;

    /**
     * hue of the lightsource
     */
    private Color color;

    /**
     * represents the huetype of the lightsource
     */
    public enum TYPE
    {
        COLD,
        WARM
    }
    /**
     * Constructor
     * sets a cold hue and attach the texture
     * @param texture : lightsource texture that is used to create the lightningeffect
     */
    public LightSource(Texture texture)
    {
        super(texture);
        parent = null;
        color = new Color(0.7f, 0.7f, 0.8f, 0.99f);

    }
    /**
     * attaches the lightsource to an entity
     * attach an entity automatically sets the center of the lightsource to the center of the attached
     * entity
     * @param entity : entity that should hold this light
     */
    public void attach(Entity entity)
    {
        parent = entity;
    }
    /**
     * updates the position accordingly to the attached parent entity
     * need to be called in the entities update function to work properly
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
     * sets the hue of the lightsource
     * @param colorType : desired hue
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
     * draws the lightsource
     * must be called inside the lightbuffer to work properly
     * @param batch : current SpriteBatch for drawing
     * @param Delta : elapsed time since last frame
     */
    public void draw(SpriteBatch batch, float Delta)
    {
        batch.setColor(color);

        super.draw(batch, Delta);
    }
}
