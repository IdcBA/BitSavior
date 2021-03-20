package com.bitsavior.entity;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bitsavior.asset.Assets;

/**
 * a lighted entity
 * contains functionality for manipulating lightsources
 * and display visual effects
 */
public class LightedEntity extends Entity
{

    /**
     * represents a type of effect for the lightsource
     */
    public enum EffectType
    {
        /**
         * lightsource pulsates periodically
         */
        PULSATING,
        /**
         * lightsource changes size from small to large periodically
         */
        SIZE_CHANGE,
        /**
         * lightsource flickers
         * cant be used together with the pulsating effect
         */
        FLICKER,
        /**
         * deactivates all lightsource effects
         */
        DEACTIVATE
    }

    /**
     * maximum diameter of the lightsource for the size change effect
     */
    private float maxLightDiameter = 200.f;
    /**
     * minimum diameter of the lightsource for the size change effect
     */
    private float minLightDiameter = 150.f;
    /**
     * internal value that holds the time elapsed in milliseconds since the last effect
     */
    private long timer;
    /**
     * holds the desired time in milliseconds for effects to happen
     */
    private long effectTimer = 50L;
    /**
     * internal variable for checking if a growth or shrink is needed(size change)
     */
    private boolean grow = true;
    /**
     * internal variable for checking if brightening or darkening is needed(flicker, pulsate)
     */
    private boolean brighter = true;
    /**
     * true if size change is desired
     */
    private boolean sizeChange = false;
    /**
     * true if pulsating is desired
     */
    private boolean pulsating  = false;
    /**
     * true if flicker is desired
     */
    private boolean flicker    = false;
    /**
     * lightsource for the lighted entity
     */
    private final LightSource light;

    /**
     * constructor
     * sets a red dot for the lighted entity and a red light light for the lightsource
     * @param manager : assetmanager that holds all assets
     */
    public LightedEntity(final AssetManager manager)
    {
        this(manager.get(Assets.breakpoint),manager.get(Assets.redLight) );
    }

    /**
     * constructor
     * sets the textures, attaches the lightsource and set the timer for the effects
     * @param lightedObject : texture for the lighted entity
     * @param light : texture for the lightsource
     */
    public LightedEntity(Texture lightedObject, Texture light)
    {
        super(lightedObject);
        this.light = new LightSource(light);

        this.light.attach(this);

        this.setSize(10, 10);
        this.light.setRadius(30.f);

        timer = System.currentTimeMillis();
    }

    /**
     * updates the logic for the lighted entity
     * checks the timer and activate required effects
     */
    public void update()
    {

        if(timer <= System.currentTimeMillis() - effectTimer)
        {
            timer = System.currentTimeMillis();

            if(pulsating)
                pulsateEffect();
            if(sizeChange)
                sizeChangeEffect();
            if(flicker)
                flickerEffect();
        }
        light.update();
    }

    /**
     * sets the desired effect for the lightsource
     * @param type : type of effect for the lightsource
     */
    public void setEffect(EffectType type)
    {
        switch(type)
        {
            case PULSATING:
                pulsating = true;
                break;
            case SIZE_CHANGE:
                sizeChange = true;
                break;
            case FLICKER:
                pulsating = false;
                flicker = true;
                break;
            case DEACTIVATE:
                sizeChange = false;
                pulsating = false;
                flicker = false;
            default:

        }
    }

    /**
     * sets the desired effect for the lightsource and sets the effect timer
     * @param type : type of effect for the lightsource
     * @param effectTimer : the desired time in milliseconds for effects to happen
     */
    public void setEffect(EffectType type, long effectTimer)
    {
        this.effectTimer = effectTimer;
        switch(type)
        {
            case PULSATING:
                pulsating = true;
                break;
            case SIZE_CHANGE:
                sizeChange = true;
                break;
            case FLICKER:
                pulsating = false;
                flicker = true;
                break;
            case DEACTIVATE:
                sizeChange = false;
                pulsating = false;
                flicker = false;
            default:

        }
    }
    /**
     * sets the radius of the attached lightsource
     * @param radius : radius of the lightsource
     */
    public void setLightRadius(float radius)
    {
        light.setRadius(radius);
    }

    /**
     * sets the range for the size change
     * @param minLightDiameter : minimum diameter of the lightsource for the size change effect
     * @param maxLightDiameter : maximum diameter of the lightsource for the size change effect
     */
    public void setSizeChangeDiameter(float minLightDiameter, float maxLightDiameter)
    {
        if(minLightDiameter >= maxLightDiameter || minLightDiameter < 0.f)
            throw new IllegalArgumentException("Error: invalid diameter values!");
        this.minLightDiameter = minLightDiameter;
        this.maxLightDiameter = maxLightDiameter;
    }

    /**
     * manages the size change effect
     */
    private void sizeChangeEffect()
    {
        if (light.getSize().x >= maxLightDiameter)
            grow = false;
        if (light.getSize().x <= minLightDiameter)
            grow = true;
        if (grow)
            light.setSize(light.getSize().x + 2.f, light.getSize().y + 2.f);
        else
            light.setSize(light.getSize().x - 2.f, light.getSize().y - 2.f);
    }

    /**
     * manages the pulsate effect
     */
    private void pulsateEffect()
    {
        if(light.getIntensityFactor() >= 0.99f)
            brighter = false;
        if(light.getIntensityFactor() <= 0.01f)
            brighter = true;
        if(brighter)
            light.setIntensity(light.getIntensityFactor() + 0.03f);
        else
            light.setIntensity(light.getIntensityFactor() - 0.03f);
    }

    /**
     * manages the flicker effect
     */
    private void flickerEffect()
    {
        if(brighter) {
            light.setIntensity(0.99f);
            brighter = false;
        }
        else
        {
            light.setIntensity(0.f);
            brighter = true;
        }
    }

    /**
     * draws the lightsource
     * @param batch : current Spritebatch
     * @param Delta : elapsed time since last frame in milliseconds
     */
    public void drawLight(final SpriteBatch batch, float Delta)
    {
        light.draw(batch, Delta);
    }
}
