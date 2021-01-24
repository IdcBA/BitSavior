package com.bitsavior.game;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.math.Rectangle;

public class ArtificalIntelligence
{

    // private members
    MapLayer collisionLayer;

    float viewRange;

    // public members
    boolean active;


    // public methods
    public ArtificalIntelligence(MapLayer collisionLayer, float viewRange)
    {
        this.collisionLayer = collisionLayer;
        active = false;
        this.viewRange = viewRange;
    }

    public void update(Rectangle player, Rectangle owner)
    {



    }

    private void calculatePath(Rectangle player, Rectangle owner)
    {
        float targetX = player.getX();
        float targetY = player.getY();

        if(Math.abs(targetX - owner.getX()) < Math.abs(targetY - owner.getY()) )
        {
            float difference = targetX - owner.getX();



        }


    }





}
