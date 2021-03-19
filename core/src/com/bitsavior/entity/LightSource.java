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
    private final Color color;
    /**
     * alpha value of the color
     * adjusts the intensity of the light
     */
    private float intensityFactor = 0.99f;
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
    public LightSource(final Texture texture)
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
    public void attach(final Entity entity)
    {
        if(entity == null)
        {
            throw new IllegalArgumentException("passed entity is null!");
        }
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
        color.a = intensityFactor;
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
                color.set(0.7f, 0.7f, 0.8f, intensityFactor);
                break;
            case WARM:
                color.set(0.83f, 0.76f, 0.65f, intensityFactor);
                break;
            default:
        }
    }

    /**
     * sets the Intensity of the light via the alpha value
     * @param intensityFactor : must be between 0 to 1, 0 = invisible, 1 = full intensity
     */
    public void setIntensity(float intensityFactor)
    {
        if(intensityFactor < 0.f)
            intensityFactor = 0.f;
        if(intensityFactor > 1.f)
            intensityFactor = 1.f;

        this.intensityFactor = intensityFactor;
    }
    /**
     *
     * @return : intensity value of the light, 0 = invisible, 1 = full intensity
     */
    public float getIntensityFactor(){ return intensityFactor; }

    /**
     * set the radius of the lightsource
     * @param radius : radius of the lightsource
     */
    public void setRadius(float radius)
    {
        setSize(radius * 5.f, radius * 5.f);
    }
    /**
     * draws the lightsource
     * must be called inside the lightbuffer to work properly
     * @param batch : current SpriteBatch for drawing
     * @param Delta : elapsed time since last frame
     */
    public void draw(final SpriteBatch batch, float Delta)
    {
        batch.setColor(color);

        super.draw(batch, Delta);
    }
}
