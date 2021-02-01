package com.bitsavior.ai;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.bitsavior.entity.Entity;
import com.bitsavior.entity.Movement;

/**
 * The artifical intelligence of the enemy
 * <p><ul>
 *     <li>Gets the owner as reference</li>
 *     <li>Implements a view system</li>
 * </ul><p>
 *
 * @author Valentin Zirngibl
 *
 */
public class EnemyAI
{
    /**
     * Reference to the owner of this class
     */
    private Entity owner;
    /**
     * The center of the owners sprite to calculate the viewing circle correctly
     */
    private Vector2 ownersCenter;
    /**
     *  Determines the radius of the viewing circle
     */
    float viewRange;


    /**
     * Constructor
     * sets the ownersCenter to the center of the owners sprite
     * @param owner : reference to the owner of this instance
     * @param viewRange : radius of the viewing circle
     */
    public EnemyAI(Entity owner, float viewRange)
    {
        ownersCenter = new Vector2();

        this.owner = owner;
        owner.getBoundings().getCenter(ownersCenter);

        this.viewRange = viewRange;
    }

    /**
     * Update the position of the owner for viewrange calculations
     */
    public void update()
    {
        owner.getBoundings().getCenter(ownersCenter);
    }

    /**
     * Checks if the given entity is in range of the owner
     * @param entity : entity to be checked for viewcontact
     * @return : returns true if the given entity is in range, false if not
     */
    public boolean isEntityInRange(final Entity entity)
    {
        Circle view = new Circle(ownersCenter.x, ownersCenter.y, viewRange);

        return view.contains(entity.getPosition());
    }
}
