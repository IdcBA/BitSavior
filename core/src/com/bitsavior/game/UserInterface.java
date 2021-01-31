package com.bitsavior.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bitsavior.asset.Assets;
import com.bitsavior.entity.Entity;

public class UserInterface {

    Watch timer;
    BitmapFont font;
    Entity uiRect;
    Entity uiRect_2;


    int timeLeft = 0;
    int pickUpsLeft = 0;


    public UserInterface(AssetManager holder, Watch timer) {
        this.timer = timer;
        font = new BitmapFont();
        uiRect = new Entity(holder.get(Assets.uiRect), 0.f);
        uiRect_2 = new Entity(uiRect);


        uiRect.setSize(130, 35);
        uiRect.setPosition(1280 * 0.005f, 960 * 0.96f);

        uiRect_2.setSize(150, 35);
        uiRect_2.setPosition(1280 * 0.295f, 960 * 0.96f);

    }

    public void update(int pickUpsLeft) {

            this.timeLeft = timer.getRemainingSeconds();
            this.pickUpsLeft = pickUpsLeft;
    }

    public void draw(SpriteBatch batch) {



        uiRect.draw(batch, 0.f);
        uiRect_2.draw(batch, 0.f);
        font.draw(batch, "Leaks fixed: " + pickUpsLeft + " / 10",1280 * 0.01f, 960 * 0.985f );
        font.draw(batch, "Time left: " + timeLeft + " / " + timer.getTimeLimit() + "sec",1280 * 0.3f, 960 * 0.985f );



    }

    public void dispose()
    {
        font.dispose();
    }
}