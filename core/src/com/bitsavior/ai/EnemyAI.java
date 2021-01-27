package com.bitsavior.ai;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.bitsavior.entity.Entity;
import com.bitsavior.entity.Movement;

public class EnemyAI
{
    public boolean isActive;
    private Entity owner;
    private Vector2 ownersCenter;
    float viewRange;

    public EnemyAI(Entity owner, float viewRange)
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

    private Movement follow(final Entity entity)
    {
        Vector2 entityPosition = new Vector2(entity.getPosition());

        return Movement.DOWN;
    }
}
