package com.bitsavior.entity;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bitsavior.asset.Assets;
import com.bitsavior.game.BackgroundMusic;
import com.bitsavior.game.Soundeffect;
import com.bitsavior.game.Watch;

/**
 * represents the Player
 * including functionality for collecting of pickups
 */
public class Player
        extends MovingEntity
{
    /**
     * counts the collected pickups
     */
    private int pickUpCounter;


    private LightSource flashlight;
    private Watch save_time;
    private Watch waiting_time;
	
    /**
     * Constructor
     * @param manager : assetHolder for player and flashlight assets
     */
    public Player(final AssetManager manager) {

        // call constructor of parent class and pass parameters for animation
        super(manager.get(Assets.player), 200.f, 4, 4, 0.05f);


        flashlight = new LightSource(manager.get(Assets.light));
        flashlight.attach(this);


        direction = Movement.UNMOVED;

        pickUpCounter = 0;

        // set player size
        setSize(30, 30);
		
    }
    /**
     * updates players position on the screen
     * @param Delta : elapsed time since last frame
     */
    @Override
    public void update(float Delta)
    {

        super.update(Delta);
        move(1, Delta);

        flashlight.update();

    }
    /**
     * collect the given pickup
     * @param pickUp : pickup that should be collected from the player
     */
    public void collect(PickUp pickUp)
    {
        pickUp.isAlive = false;
        pickUpCounter++;
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

    public int getPickUpCounter() { return pickUpCounter; }


    /**
     * @param effect : Sound, that should be played
     * @param waiting : waiting time needed before the sound should be played again
     */
    public void playSound(final Sound effect, boolean waiting) {

        Soundeffect sound;
        if(!waiting) {
    	sound = new Soundeffect(effect);
    	sound.play();
    	}

    	else if(waiting_time==null) { 
    			waiting_time = new Watch(1);
    			waiting_time.startWatch();
    			sound = new Soundeffect(effect);
    	    	sound.play();
    	}
    	else {
    		waiting_time.update();
    		if(!waiting_time.isActive()) {
    		sound = new Soundeffect(effect);
	    	sound.play();
	    	waiting_time = new Watch(1);
			waiting_time.startWatch();
    		}
    	}
    }    


	public void stopMusic(final BackgroundMusic music) {
		if(!this.isAlive) {
			music.stop();
		}
		
	}

	public void Save() {
		
		save_time = new Watch(10);
		save_time.startWatch();
		System.out.println("Timer set");
		
	}
	
	public boolean isSaved() {
		save_time.update();
        return save_time.isActive();
	}
}



