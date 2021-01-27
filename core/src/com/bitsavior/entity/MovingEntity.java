package com.bitsavior.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.bitsavior.collision.ICollision;
import com.bitsavior.map.Tilemap;

/**
 * specifies the moving functionality for moving entities
 * and takes care of the moving animations
 */
public class MovingEntity extends Entity implements ICollision
{

    /**
     * represents the animation of the moving entity
     */
    private Animation<TextureRegion> entityAn;
    /**
     * the time spend in the current animation frame
     */
    private float stateTime = 0f;
    /**
     * displays how long a animation should be played
     */
    private float frameDuration;
    /**
     * rotation angle of the sprite in degrees
     */
    private float rotation = 0.f;

    // public members
    /**
     * contains the actual movement direction
     */
    public Movement direction;
    /**
     * constructor
     * @param texture : texture of the entity
     * @param velocity : velocity of the entity
     * @param colums : colums of the used spritesheet(for animation)
     * @param rows : rows of the used spritesheet(for animation)
     * @param frameDuration : sets the duration of one animation frame
     */
    public MovingEntity(Texture texture, float velocity, int colums, int rows, float frameDuration)
    {
        super(texture, velocity);
        direction = Movement.UNMOVED;

        this.frameDuration = frameDuration;

        // setup the animation
        TextureRegion[][] tmp = TextureRegion.split(texture,
                texture.getWidth() / colums,
                texture.getHeight() / rows);

        TextureRegion[] entityFrames = new TextureRegion[colums * rows];
        int index = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < colums; j++) {
                entityFrames[index++] = tmp[i][j];
            }
        }
        entityAn = new Animation<TextureRegion>(this.frameDuration, entityFrames);

    }

    /**
     * sets the coordinates of the entity accordingly to the given direction
     * @param inversion : 1 for the normal direction, -1 for the inverse direction
     * @param Delta : elapsed time since last frame
     */
    protected void move(int inversion, float Delta) {
        switch (direction) {
            case LEFT:
                setPosition(inversion * -velocity * Delta, 0);
                rotation = 270.f;
                break;
            case RIGHT:
                setPosition(inversion * velocity * Delta, 0);
                rotation = 90.f;
                break;
            case UP:
                setPosition(0, inversion * velocity * Delta);
                rotation = 180.f;
                break;
            case DOWN:
                setPosition(0, inversion * -velocity * Delta);
                rotation = 0.f;
                break;
            default:
        }
    }
    /**
     * check if enemy collided with the given entity
     * @param entity : collision to be checked with
     * @return : returns true if a collision happened
     */
    public boolean isCollided(Entity entity)
    {
        // get the sprites bounding rectangle
        Rectangle boundaries = new Rectangle(sprite.getBoundingRectangle());

        // check if the boundaries collided and return
        return boundaries.overlaps(entity.sprite.getBoundingRectangle());
    }

    /**
     * check object layer of the map if a collision happened
     * @param map : map with the collideable objects to check
     */
    public void isCollided(Tilemap map)
    {
        // get all objects out of the collision layer
        MapObjects objects = map.getLayer(1).getObjects();
        // get the sprites bounding rectangle
        Rectangle boundaries = new Rectangle(getBoundings());

        // iterate through all objects and check for collision
        for (RectangleMapObject rectangleObject : objects.getByType(RectangleMapObject.class)) {
            if (boundaries.overlaps(rectangleObject.getRectangle()))
            {
                // if a collision happened, correct position
                move(-1, Gdx.graphics.getDeltaTime());
                break;
            }
        }
    }

    /**
     * draw the current animation with the given rotation
     * @param batch : current SpriteBatch for drawing
     * @param Delta : elapsed time since last frame
     */
    @Override
    public void draw(SpriteBatch batch, float Delta)
    {
        if(isAlive)
        {
            stateTime += Delta;

            batch.draw(entityAn.getKeyFrame(stateTime, true), sprite.getX(), sprite.getY(),
                    sprite.getWidth() / 2.f,
                    sprite.getHeight() / 2.f,
                    sprite.getWidth(), sprite.getHeight(),1.f, 1.f, rotation);
        }
    }
}
