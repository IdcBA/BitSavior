package com.bitsavior.ai;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.bitsavior.entity.Entity;

public class EnemyAI
{
    public boolean isActive;
    private final Entity owner;
    private final Vector2 ownersCenter;
    float viewRange;

    public EnemyAI(final Entity owner, float viewRange)
    {
        isActive = false;
        ownersCenter = new Vector2();

        this.owner = owner;
        owner.getBoundings().getCenter(ownersCenter);

        this.viewRange = viewRange;
    }

    public void update()
    {
        owner.getBoundings().getCenter(ownersCenter);
    }

    public boolean isEntityInRange(final Entity entity)
    {
        Circle view = new Circle(ownersCenter.x, ownersCenter.y, viewRange);

        return view.contains(entity.getPosition());
    }
}
