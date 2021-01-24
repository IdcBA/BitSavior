package com.bitsavior.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;

public class TitleScreen extends ScreenAdapter
{
    BitSavior game;
    GameScreen gScreen;
    /** the TitleScreen itself */
    TitleScreen tScreen;
    
    /** 0: no messages; 1: send messages */
    int aScreenTestMode=1;

    /**
	 * Constructor:
	 * @param the game
	 */
    public TitleScreen(BitSavior game) {
        this.game = game;
        tScreen = this;
    }

    @Override
    public void show(){
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keyCode) {
                if (keyCode == Input.Keys.SPACE) { //keyCode == Input.Keys.SPACE //Gdx.input.isKeyPressed(Keys.SPACE)
                	
                	if(gScreen==null) {
                		if(aScreenTestMode==1) System.out.println("noch kein gScreen");
                		gScreen = new GameScreen(game, tScreen);
                		game.setScreen(gScreen);
                	}
                	else {
                		if(aScreenTestMode==1) System.out.println("bisheriger GameScreen wird geladen");
                		game.setScreen(gScreen); //new GameScreen(game)
                	}
                }
                return true;
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, .25f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        game.font.draw(game.batch, "Title Screen!", Gdx.graphics.getWidth() * .25f, Gdx.graphics.getHeight() * .75f);
        game.font.draw(game.batch, "Press space to play.", Gdx.graphics.getWidth() * .25f, Gdx.graphics.getHeight() * .25f);
        game.batch.end();
    }

    @Override
    public void hide(){
        Gdx.input.setInputProcessor(null);
    }
}
