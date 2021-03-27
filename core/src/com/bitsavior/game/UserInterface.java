package com.bitsavior.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bitsavior.asset.Assets;
import com.bitsavior.entity.Entity;
import com.bitsavior.entity.PickUp;

/**
 * holds all data related to the user interface during a game session
 */
public class UserInterface {

    /**
     * holds a reference to the game timer
     */
    Watch timer;
    /**
     * the font used for the text of the user interface
     */
    BitmapFont font;
    /**
     * holds a graphical textbox for the user interface
     */
    Entity uiRect;
    /**
     * holds a graphical textbox for the user interface
     */
    Entity uiRect_2;
    /**
     * holds the time left in seconds for the remaining game session
     */
    int timeLeft = 0;
    /**
     * holds the remaining number of pickups
     */
    int pickUpsLeft = 0;
    /**
     * holds the initial number of pickups during start
     */
    int NumberOfPickups;
    /**
     * constructor
     * @param holder : assetmanager that holds all the assets
     * @param timer : a reference to the game timer
     * @param NumberOfPickups : the initial number of pickups during start
     */
    public UserInterface(final AssetManager holder, final Watch timer, int NumberOfPickups) {
        this.timer = timer;
        font = new BitmapFont();
        uiRect = new Entity(holder.get(Assets.uiRect), 0.f);
        uiRect_2 = new Entity(uiRect);

        this.NumberOfPickups = NumberOfPickups;

        uiRect.setSize(130, 35);
        uiRect.setPosition(WorldBounds.WIDTH * 0.005f, WorldBounds.HEIGHT * 0.96f);

        uiRect_2.setSize(150, 35);
        uiRect_2.setPosition(WorldBounds.WIDTH * 0.295f, WorldBounds.HEIGHT * 0.96f);

    }
    /**
     * updates the necessary information displayed in the user interface
     */
    public void update() {

            this.timeLeft = timer.getRemainingSeconds();
            this.pickUpsLeft = NumberOfPickups - PickUp.pickUpCounter;
    }
    /**
     * draws the user interface
     * @param batch : current spritebatch
     */
    public void draw(final SpriteBatch batch)
    {
        uiRect.draw(batch, 0.f);
        uiRect_2.draw(batch, 0.f);
        font.draw(batch, "Leaks fixed: " + pickUpsLeft + " / " + NumberOfPickups,WorldBounds.WIDTH * 0.01f, WorldBounds.HEIGHT * 0.985f );
        font.draw(batch, "Time left: " + timeLeft + " / " + timer.getTimeLimit() + "sec",WorldBounds.WIDTH * 0.3f, WorldBounds.HEIGHT * 0.985f );
    }
    /**
     * dispose all related assets
     */
    public void dispose()
    {
        font.dispose();
    }
}