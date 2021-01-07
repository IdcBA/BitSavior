package com.bitsavior.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Player extends Entity
{
    // private Members
    private int health;

    // public Methods
    /**
     * Constructor
     * @param texture : players texture
     */
    public Player(Texture texture)
    {
        // call constructor of Entity and set texture
        super(texture);
        health = 10;

        // set player size
        sprite.setSize(10, 10);

    }

    /**
     * draw()
     * @param batch : current SpriteBatch for drawing
     */
    public void draw(SpriteBatch batch)
    {
        batch.draw(sprite,position.x, position.y, sprite.getWidth(), sprite.getHeight());
    }

}
