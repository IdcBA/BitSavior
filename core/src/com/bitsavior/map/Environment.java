package com.bitsavior.map;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.bitsavior.entity.LightedEntity;

import java.util.ArrayList;

/**
 * handles the lights in the scene including
 * positioning of the lighted objects
 */
public class Environment
{
    /**
     * list of all lighted objects
     */
    private ArrayList<LightedEntity> lights;
    /**
     * number of lights in the scene
     */
    private final int NumberOfLights = 4;
    /**
     * list of the positions of the lights
     */
    private Vector2[] lightPositions;

    /**
     * constructor
     * creates the lighted entities and fills the positions into the array
     * @param manager : assetmanager that holds all the assets
     */
    public Environment(final AssetManager manager)
    {
        lights = new ArrayList<LightedEntity>();

        for(int i = 0; i < NumberOfLights; i++)
            lights.add(new LightedEntity(manager));

        lightPositions = new Vector2[NumberOfLights];

        lightPositions[0] = new Vector2(390, 191);
        lightPositions[1] = new Vector2(638, 767);
        lightPositions[2] = new Vector2(1252, 26);
        lightPositions[3] = new Vector2(152, 615);
    }

    /**
     * sets the positions for the lighted entities
     */
    public void create()
    {
        for(int i = 0; i < NumberOfLights; i++) {
            lights.get(i).setPosition(lightPositions[i].x, lightPositions[i].y);
            lights.get(i).setEffect(LightedEntity.EffectType.FLICKER, 500L);

        }
    }

    /**
     * updates all lighted entities
     */
    public void update()
    {
        for(LightedEntity lightbulb : lights)
            lightbulb.update();
    }

    /**
     * draws all lighted entities
     * @param batch : current Spritebatch
     * @param Delta : elapesd time since last frame in milliseconds
     */
    public void draw(SpriteBatch batch, float Delta)
    {
        for(LightedEntity lightbulb : lights)
            lightbulb.draw(batch, Delta);
    }

    /**
     * draws all the lightsources of the lighted entities
     * @param batch : current Spritebatch
     * @param Delta : elapesd time since last frame in milliseconds
     */
    public void drawLight(SpriteBatch batch, float Delta)
    {
        for(LightedEntity lightbulb : lights) {
            lightbulb.drawLight(batch, Delta);
        }
    }
}
