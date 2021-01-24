package com.bitsavior.game;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class EnemyAI
{
    public boolean isActive;
    private Rectangle rectangle;
    private Vector2 rectCenter;
    float viewRange;

    public EnemyAI(Rectangle boundings, float viewRange)
    {
        isActive = false;
        rectCenter = new Vector2();

        rectangle = boundings;
        boundings.getCenter(rectCenter);

        this.viewRange = viewRange;
    }

    public void update(Rectangle boundings)
    {
        rectangle = boundings;
        boundings.getCenter(rectCenter);
    }

    public boolean isEntityInRange(Entity entity)
    {
        Circle view = new Circle(rectangle.getX(), rectangle.getY(), viewRange);

        return view.contains(entity.getPosition());

    }
}
