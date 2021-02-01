package com.bitsavior.entity;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bitsavior.asset.Assets;

import java.util.Random;

/**
 * a class to represent a friendly bug that adds a moving lightsource
 * to help the player navigate through the level
 * @author Valentin Zirngibl
 */
public class AntiBug extends MovingEntity
{

    /**
     * the lightcone of this bug, lightens the surrounding area
     */
    private LightSource flashlight;
    /**
     * maximum walk distance before change of direction
     */
    private final float maxWalkingDistance = 100.f;
    /**
     * currently moved distance in worldunits, resets after direction change
     */
    private float walkDistance = 0.f;

    /**
     * constructor
     * <p><ul>
     *     <li>passes the texture of this entity to the base class</li>
     *     <li>passes animation data to the base class</li>
     *     <li>creates a lightsource and attaches it to this entity</li>
     * </ul><p>
     * @param manager : contains textures for antibug and the lightsource
     */
    public AntiBug(AssetManager manager)
    {
        super(manager.get(Assets.antibug), 100.f, 2, 1, 0.1f);

        isAlive = false;

        flashlight = new LightSource(manager.get(Assets.light));
        flashlight.attach(this);
        flashlight.setColor(LightSource.TYPE.WARM);

        // set size
        sprite.setSize(25, 25);
    }

    /**
     * spawn the entity at the given position
     * @param x : x-coordinate(spawn)
     * @param y : y-coordinate(spawn)
     */
    public void spawn(float x, float y)
    {
        // testing
        isAlive = true;
        sprite.setPosition(x, y);
        chooseDirection();
    }

    /**
     * updates position of entity, attached lightsource and chooses a new
     * direction if the entity moved a given distance
     * @param Delta : elapsed time since last frame
     */
    public void update(float Delta)
    {
        // change enemies direction if moved enough
        if(walkDistance >= maxWalkingDistance)
            chooseDirection();
        move(1, Delta);

        flashlight.update();

        // add covered distance of the frame
        walkDistance += velocity * Delta;

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
    // private Methods
    /**
     * sets the move direction randomly out of 4 possible directions
     */
    private void chooseDirection()
    {
        Random random = new Random();

        // reset walked distance
        walkDistance = 0.f;

        switch(random.nextInt(4))
        {
            case 0:
                this.direction = Movement.LEFT;
                break;
            case 1:
                this.direction = Movement.RIGHT;
                break;
            case 2:
                this.direction = Movement.UP;
                break;
            case 3:
                this.direction = Movement.DOWN;
                break;
            default:
                System.out.println("fail");

        }
    }
}