package com.bitsavior.ai;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.bitsavior.entity.Entity;

/**
 * positronic brain!
 * (thanks to Mr. Data for the help)
 */
public class EnemyAI
{
    /**
     * shows the status of the ai
     */
    public boolean isActive;
    /**
     * the owner of the instance
     */
    private final Entity owner;
    /**
     * the center of the owners sprite
     */
    private final Vector2 ownersCenter;
    /**
     * the viewrange for the owner to detect other entities
     */
    float viewRange;
    /**
     * constructor
     * attaches the owner and get the center of the owners sprite
     * @param owner : owner of this instance
     * @param viewRange : the viewrange for the owner to detect other entities
     */
    public EnemyAI(final Entity owner, float viewRange)
    {
        isActive = false;
        ownersCenter = new Vector2();

        this.owner = owner;
        owner.getBoundings().getCenter(ownersCenter);

        this.viewRange = viewRange;
    }
    /**
     * updates the logic
     */
    public void update()
    {
        owner.getBoundings().getCenter(ownersCenter);
    }
    /**
     * checks if a given entity is in view range of the attached owner
     * @param entity : the entity to be checked
     * @return : true if the given entity is in range, false when not
     */
    public boolean isEntityInRange(final Entity entity)
    {
        Circle view = new Circle(ownersCenter.x, ownersCenter.y, viewRange);

        return view.contains(entity.getPosition());
    }
}
