package com.bitsavior.map;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.bitsavior.asset.Assets;
import com.bitsavior.entity.LightedEntity;
import com.bitsavior.game.SoundEffect;
import com.bitsavior.game.Watch;

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
     * standard effect, if no other is set
     */
    private final LightedEntity.EffectType standardEffect =  LightedEntity.EffectType.DEACTIVATE;
    /**
     * timer to set a effect for a limited time
     */
    private Watch timer;
    /**
     * soundeffect for the light objects
     */
    private SoundEffect sirene;
    /**
     * timer for the sound effect
     */
    private Watch soundTimer;

    
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

        lightPositions[0] = new Vector2(370, 30);
        lightPositions[1] = new Vector2(625, 752);
        lightPositions[2] = new Vector2(1252, 0);
        lightPositions[3] = new Vector2(145, 590);

        soundTimer = new Watch(10);
        sirene = new SoundEffect(manager.get(Assets.sirene));
    }

    /**
     * sets the positions for the lighted entities
     */
    public void create()
    {
        for(int i = 0; i < NumberOfLights; i++) {
            lights.get(i).setPosition(lightPositions[i].x, lightPositions[i].y);
        }

        lights.get(0).setEffect(LightedEntity.EffectType.PULSATING, 500L);
        lights.get(2).setEffect(LightedEntity.EffectType.PULSATING);
        lights.get(2).setLightRadius(80.f);
        lights.get(3).setEffect(LightedEntity.EffectType.PULSATING, 500L);
    }

    /**
     * updates all lighted entities
     */
    public void update()
    { 
    	if(timer != null) {
    		if(!timer.isActive()) 
    			changeEffect(standardEffect);
    	}
        for(LightedEntity lightbulb : lights)
            lightbulb.update();

        if(!soundTimer.isActive())
           sirene.stop();
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
    
    /**
     * changes the effect for a limited time
     * @param type : new effect
     * @param effectTime : time limit for effect
     */
    public void changeEffect(LightedEntity.EffectType type, int effectTime) {
    	timer = new Watch(effectTime);
    	timer.startWatch();
    	for(int i = 0; i < NumberOfLights; i++) {
            lights.get(i).setEffect(type, 18L);
        }
    }

    /**
     * changes the effect
     * @param type : new effect
     */
	public void changeEffect(LightedEntity.EffectType type) {
		for(int i = 0; i < NumberOfLights; i++) {
			lights.get(i).setEffect(type);
		}
	}

    /**
     * plays the sirene sound
     * @param active : true for playing the sound, false for deactivate a playing sound
     */
	public void sirene(boolean active)
    {
        if(!soundTimer.isActive()) {
            soundTimer.startWatch();
            sirene.play();
            changeEffect(LightedEntity.EffectType.PULSATING, soundTimer.getTimeLimit());
        }

        if(!active)
            sirene.stop();
    }
}