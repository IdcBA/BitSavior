package com.bitsavior.entity;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bitsavior.asset.Assets;
import com.bitsavior.game.Soundeffect;
import com.bitsavior.game.Watch;
import java.util.Random;

/**
 * represents a helping bug that has a light attached and random movement
 */
public class AntiBug extends MovingEntity
{
    /**
     * lightsource to implement light effects
     */
    private LightSource flashlight;

    private Watch waitingTime;
    /**
     * maximum walk distance before change of direction
     */
    private final float maxMovingDistance = 150.f;
    /**
     * walked worldunits before direction change
     */
    private float coveredDistance = 0.f;

    private Soundeffect saveSound;

    /**
     * constructor
     * load texture and attach lightsource
     * @param manager : assetmanager that holds all assets
     */
    public AntiBug(final AssetManager manager)
    {
        super(manager.get(Assets.antibug), 100.f, 2, 1, 0.1f);

        isAlive = false;

        flashlight = new LightSource(manager.get(Assets.light));
        flashlight.attach(this);

        saveSound = new Soundeffect(manager.get(Assets.save));

        waitingTime = new Watch(10);

        // set size
        setSize(25, 25);
    }
    /**
     * set the enemy alive and spawn it at the given position
     * @param x : x-coordinate(spawn)
     * @param y : y-coordinate(spawn)
     */
    public void spawn(float x, float y)
    {
        isAlive = true;
        setPosition(x, y);
        chooseDirection();
    }
    /**
     * updates position, direction and artifical intelligence
     * @param Delta : elapsed time since last frame
     */
    @Override
    public void update(float Delta)
    {
        super.update(Delta);
        // change enemies direction if moved enough
        if(coveredDistance >= maxMovingDistance || isRecentlyCollided())
            chooseDirection();
        move(1, Delta);

        flashlight.update();

        // add covered distance of the frame
        coveredDistance += velocity * Delta;

    }
    /**
     * draws the lightsource if the entity is alive
     * @param batch : current Spritebatch
     * @param Delta : elapsed time since last frame in milliseconds
     */
    public void drawFlashlight(final SpriteBatch batch, float Delta)
    {
        if(isAlive)
            flashlight.draw(batch, Delta);
    }
    /**
     * set move direction of the enemy randomly
     */
    private void chooseDirection()
    {
        Random random = new Random();

        // reset walked distance
        coveredDistance = 0.f;

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
    
    public void playSound() {
    	
        if(!waitingTime.isActive()) {
       	saveSound.play();
       	waitingTime.reset(10);
       	waitingTime.startWatch();
       	}
       }
    
    public boolean WaitingtimeIsActive() { return !waitingTime.isActive(); }
}