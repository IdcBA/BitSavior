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
 * a moving entity controls the moving behaviour and the moving animations.
 * A moving entity extends an entity and implement a collision interface for
 * collision detection
 */
public class MovingEntity extends Entity implements ICollision
{
    /**
     * represents the animation of the moving entity
     */
    private final Animation<TextureRegion> entityAnimation;
    /**
     * current animationframe to be drawn
     */
    private TextureRegion keyFrame;
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
    /**
     * contains the actual movement direction
     */
    private boolean recentlyCollided = false;
    /**
     * current moving direction
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
    public MovingEntity(final Texture texture, float velocity, int colums, int rows, float frameDuration)
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
        entityAnimation = new Animation<TextureRegion>(this.frameDuration, entityFrames);
        try {
            keyFrame = new TextureRegion(entityFrames[0]);
        }
        catch(IndexOutOfBoundsException e)
        {
            System.out.println("Error: entitFrames index out of bounds!");
        }
    }
    /**
     * sets the coordinates of the entity accordingly to the given direction
     * @param inversion : 1 for the normal direction, -1 for the inverse direction
     * @param Delta : elapsed time since last frame
     */
    protected void move(int inversion, float Delta)
    {
        if(inversion != 1 && inversion != -1)
            throw new IllegalArgumentException("Error: Inversion must be 1 or -1!");

        switch (direction) {
            case LEFT:
                updatePosition(inversion * -velocity * Delta, 0);
                rotation = 270.f;
                break;
            case RIGHT:
                updatePosition(inversion * velocity * Delta, 0);
                rotation = 90.f;
                break;
            case UP:
                updatePosition(0, inversion * velocity * Delta);
                rotation = 180.f;
                break;
            case DOWN:
                updatePosition(0, inversion * -velocity * Delta);
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
    public boolean isCollided(final Entity entity)
    {
        // get the sprites bounding rectangle
        Rectangle boundaries = new Rectangle(getBoundings());

        // check if the boundaries collided and return
        return boundaries.overlaps(entity.getBoundings());
    }

    /**
     * updates the animation logic
     * Notice: if this class is inherited by another class,
     * this function must be called in the update function of the child
     * @param Delta : time since last frame in milliseconds
     */
    public void update(float Delta)
    {

        if(isAlive) {
            stateTime += Delta;
            keyFrame = entityAnimation.getKeyFrame(stateTime, true);
        }
    }
    /**
     * check object layer of the map if a collision happened
     * @param map : map with the collideable objects to check
     */
    public void isCollided(final Tilemap map)
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
                recentlyCollided = true;
                break;
            }
            recentlyCollided = false;
        }
    }
    /**
     * draw the current animation with the given rotation
     * @param batch : current SpriteBatch for drawing
     * @param Delta : elapsed time since last frame
     */
    @Override
    public void draw(final SpriteBatch batch, float Delta)
    {
        if(isAlive)
        {
            batch.draw(keyFrame, getPosition().x, getPosition().y,
                    getSize().x / 2.f,
                    getSize().y / 2.f,
                    getSize().x, getSize().y,1.f, 1.f, rotation);
        }
    }

    /**
     * gets back if the entityy is recently collided(since the last frame)
     * @return : true if collided since the last frame, false if not collided since the last frame
     */
    protected boolean isRecentlyCollided() { return recentlyCollided; }
}
