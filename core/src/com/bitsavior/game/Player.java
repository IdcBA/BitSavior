package com.bitsavior.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;

/**
 * represents the Player
 */
public class Player
        extends Entity
        implements IMovement, ICollision
{
    // public Methods
    /**
     * move direction of the player
     */
    Direction direction;

    // private Methods
    private int pickUpCounter;
	
    Animation<TextureRegion> playerAn;
	float stateTime = 0f;
	static final int COLUMS = 3, ROWS = 1;
	
    /**
     * Constructor
     *
     * @param texture : players texture
     */
    public Player(Texture texture, float velocity) {
        // call constructor of Entity and set texture
        super(texture, velocity);

        isAlive = true;

        direction = Direction.UNMOVED;

        pickUpCounter = 0;

        // set player size
        sprite.setSize(35, 35);
        
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
		playerAn = new Animation<TextureRegion>(0.33f, playerFrames);
		
    }
    
    @Override
	public void draw(SpriteBatch batch) {
		stateTime += Gdx.graphics.getDeltaTime();
		batch.draw(playerAn.getKeyFrame(stateTime, true), sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
		
		}
    
    public void update()
    {
        move(1);
    }


    public void move(int inversion) {
        switch (direction) {
            case LEFT:
                setPosition(inversion * -velocity * Gdx.graphics.getDeltaTime(), 0);
                this.direction = Player.Direction.LEFT;
                break;
            case RIGHT:
                setPosition(inversion * velocity * Gdx.graphics.getDeltaTime(), 0);
                this.direction = Player.Direction.RIGHT;
                break;
            case UP:
                setPosition(0, inversion * velocity * Gdx.graphics.getDeltaTime());
                this.direction = Player.Direction.UP;
                break;
            case DOWN:
                setPosition(0, inversion * -velocity * Gdx.graphics.getDeltaTime());
                this.direction = Player.Direction.DOWN;
                break;
            default:

        }

    }

    public void collect(PickUp pickUp)
    {
        pickUp.isAlive = false;
        pickUpCounter++;
        System.out.println(pickUpCounter);


    }


    public boolean isCollided(Entity entity) {
        // get the sprites bounding rectangle
        Rectangle boundaries = new Rectangle(sprite.getBoundingRectangle());

        // check if the boundaries collided and return
        return boundaries.overlaps(entity.sprite.getBoundingRectangle());
    }

}



