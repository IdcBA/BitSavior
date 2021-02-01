package com.bitsavior.entity;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bitsavior.asset.Assets;

/**
 * A Class that represents the player
 * including functionality for collecting pickups
 * @author Valentin Zirngibl
 * @author Samreet
 */
public class Player
        extends MovingEntity
{
    // private Methods
    /**
     * Counts the collected pickups
     */
    private int pickUpCounter;
    /**
     * adds a lightcone around the player, so he can see in the darkness
     */
    private LightSource flashlight;
    /**
     * constructor
     * <p><ul>
     *     <li>passes the texture of this entity to the base class</li>
     *     <li>passes animation data to the base class</li>
     *     <li>creates a lightsource and attaches it to this entity</li>
     * </ul><p>
     * @param manager : contains textures for antibug and the lightsource
     */
    public Player(AssetManager manager) {

        super(manager.get(Assets.player), 200.f, 4, 4, 0.05f);


        flashlight = new LightSource(manager.get(Assets.light));
        flashlight.attach(this);

        isAlive = true;

        direction = Movement.UNMOVED;

        pickUpCounter = 0;

        // set player size
        sprite.setSize(35, 35);
		
    }
    /**
     * Updates players position on the screen
     * @param Delta : elapsed time since last frame
     */
    public void update(float Delta)
    {
        if(!this.isAlive)
            System.out.println("Hit");
        move(1, Delta);

        flashlight.update();

    }
    /**
     * Collects the given pickup
     * @param pickUp : Pickup that should be collected from the player
     */
    public void collect(PickUp pickUp)
    {
        pickUp.isAlive = false;
        pickUpCounter++;
        System.out.println(pickUpCounter);
    }
    /**
     * responsible for drawing the flashlight
     * This additional draw method is necessary because of
     * the special lightsource mechanic.&nbsp;
     * This function needs to be called between lightbuffer.begin()
     * and lightbuffer.end
     * @param batch : the current spritebatch
     * @param Delta : elapsed time since last frame
     */
    public void drawFlashlight(SpriteBatch batch, float Delta)
    {
        if(isAlive)
            flashlight.draw(batch, Delta);
    }
    /**
     * Gets the current amount of collected pickups
      * @return : Amount of currently collected pickups
     */
    public int getPickUpCounter() { return pickUpCounter; }
}



