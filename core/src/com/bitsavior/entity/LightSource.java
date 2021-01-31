package com.bitsavior.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class LightSource extends Entity
{
    Entity parent;

    Color color;


    public enum TYPE
    {
        COLD,
        WARM
    }


    public LightSource(Texture texture)
    {
        super(texture);
        parent = null;
        color = new Color(0.7f, 0.7f, 0.8f, 0.99f);

    }

    public void attach(Entity entity)
    {
        parent = entity;
    }

    public void update()
    {
        if(parent != null)
        {
            setPosition((parent.getPosition().x + (parent.getSize().x / 2)) - (getSize().x / 2),
                    (parent.getPosition().y + (parent.getSize().y / 2)) - (getSize().y / 2));
        }
    }

    public void setColor(TYPE colorType)
    {
        switch(colorType)
        {
            case COLD:
                color.set(0.7f, 0.7f, 0.8f, 0.99f);
                break;
            case WARM:
                color.set(0.83f, 0.76f, 0.65f, 0.99f);
                break;
            default:
        }

    }

    public void draw(SpriteBatch batch, float Delta)
    {
        batch.setColor(color);

        super.draw(batch, Delta);
    }










}
