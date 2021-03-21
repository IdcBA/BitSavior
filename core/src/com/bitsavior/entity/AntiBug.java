package com.bitsavior.entity;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bitsavior.asset.Assets;
import com.bitsavior.game.Soundeffect;
import com.bitsavior.game.Watch;
import java.util.Random;

public class AntiBug extends MovingEntity
{
    private LightSource flashlight;
    private Watch waitingTime;

    /**
     * maximum walk distance before change of direction
     */
    private final float maxWalkingDistance = 100.f;
    /**
     * walked worldunits before direction change
     */
    private float walkDistance = 0.f;

    public AntiBug(final AssetManager manager)
    {
        super(manager.get(Assets.antibug), 100.f, 2, 1, 0.1f);

        isAlive = false;

        flashlight = new LightSource(manager.get(Assets.light));
        flashlight.attach(this);

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

    public void drawFlashlight(final SpriteBatch batch, float Delta)
    {
        if(isAlive)
            flashlight.draw(batch, Delta);
    }


    // private Methods
    /**
     * set move direction of the enemy randomly
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
    
    public void playSound(Soundeffect sound) {
    	
        if(waitingTime == null) { 
       	sound.play();
       	waitingTime = new Watch(2);
       	waitingTime.startWatch();
       	}
       	else {
       		waitingTime.update();
       		if(!waitingTime.isActive()) {
   	    	sound.play();
   	    	waitingTime = new Watch(2);
   	    	waitingTime.startWatch();
       		}
       		
       		else return;
       	}
       }
    
    public boolean WaitingtimeIsActive() {
    	if(waitingTime == null) 
    		return false;
    	else {
    		waitingTime.update();
    		return waitingTime.isActive();
    	}
    }
}