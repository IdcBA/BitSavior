package com.bitsavior.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * represents the Player
 */
public class Player
        extends MovingEntity
{
    // private Methods
    private int pickUpCounter;
	
    Animation<TextureRegion> playerAn;
	float stateTime = 0f;
	static final int COLUMS = 4, ROWS = 4;
	
    /**
     * Constructor
     * @param texture : players texture
     * @param velocity : velocity of the player
     */
    public Player(Texture texture, float velocity) {
        // call constructor of Entity and set texture
        super(texture, velocity);

        isAlive = true;

        direction = Movement.UNMOVED;

        pickUpCounter = 0;

        // set player size
        sprite.setSize(35, 35);
        
        
     // Animation
        TextureRegion[][] tmp = TextureRegion.split(texture,
        		texture.getWidth() / COLUMS,
        		texture.getHeight() / ROWS);
        
    	TextureRegion[] playerFrames = new TextureRegion[COLUMS * ROWS];
		int index = 0;
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLUMS; j++) {
				playerFrames[index++] = tmp[i][j];
			}
    }
		playerAn = new Animation<TextureRegion>(0.05f, playerFrames);
		
    }
    
    @Override
	public void draw(SpriteBatch batch)
    {
        if(isAlive)
        {
            stateTime += Gdx.graphics.getDeltaTime();
            batch.draw(playerAn.getKeyFrame(stateTime, true), sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
        }
    }
    
    public void update(float Delta)
    {
        if(!this.isAlive)
            System.out.println("Hit");
        move(1, Delta);
    }

    public void collect(PickUp pickUp)
    {
        pickUp.isAlive = false;
        pickUpCounter++;
        System.out.println(pickUpCounter);


    }

}



