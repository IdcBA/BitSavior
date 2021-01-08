package com.bitsavior.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;

public class Enemy extends Entity{

    // public Members
    public boolean isAlive;

    // private Members
    /**
     * view range of the enemy in units
     */
    private float viewRange;

    /**
     * move direction of the enemy
     */
    private enum Direction
    {
        LEFT,
        RIGHT,
        UP,
        DOWN
    }
    private Direction direction;


    // public Methods

    /**
     * Constructor()
     * @param texture : texture of the enemy
     * @param viewRange : viewRange in worldunits
     */
    public Enemy(Texture texture, float viewRange)
    {
        super(texture);

        isAlive = false;
        this.viewRange = viewRange;


        // set size
        sprite.setSize(10, 10);
    }

    /**
     * set the enemy alive and spawn it at the given position
     * @param x : x-coordinate(spawn)
     * @param y : y-coordinate(spawn)
     */
    public void spawn(float x, float y)
    {
        isAlive = true;
        sprite.setPosition(x, y);
    }

    /**
     * update enemies game logic
     */
    public void update()
    {
        chooseDirection();


    }

    /**
     * draw()
     * @param batch : current SpriteBatch for drawing
     */
    public void draw(SpriteBatch batch)
    {
        if(isAlive)
            batch.draw(sprite,position.x, position.y, sprite.getWidth(), sprite.getHeight());
    }

    // private Methods

    /**
     * set move direction of the enemy randomly
     */
    private void chooseDirection()
    {
        Random random = new Random();

        switch(random.nextInt(4))
        {
            case 0:
                direction = Direction.LEFT;
                break;
            case 1:
                direction = Direction.RIGHT;
                break;
            case 2:
                direction = Direction.UP;
                break;
            case 3:
                direction = Direction.DOWN;
                break;
            default:
        }
    }

}
