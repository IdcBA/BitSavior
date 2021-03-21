package com.bitsavior.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class LoseScreen extends ScreenAdapter{

	//variables for testing
	/** ... */
	
	//visuals e.g. stage, batch, font
	/** Stage to handle input*/
    private Stage stage;
	private SpriteBatch batch;
	private Texture img;
    
    /**
	 * Constructor
	 * @param the game
	 */
    public LoseScreen(final BitSavior game) {
    	
        //add Stage and batch&font to display objects
        stage = new Stage();
        batch = new SpriteBatch();
    	img = new Texture("bluescreen_reworked.png");
        
    	//actions
        stage.addListener(new InputListener() {
        	@Override
        	public boolean keyDown(InputEvent event, int keyCode) {
				if (keyCode == Input.Keys.ESCAPE) {
					game.manager.showScreen(Screens.TITLE);
					return true;
				}
				return false;
			}
		});
    }
    
    @Override
    public void show() {
    	//set Input to stage
    	Gdx.input.setInputProcessor(stage);
    }
    
    @Override
    public void render(float delta) {    	
        Gdx.gl.glClearColor(0, 0.25f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(img, 0, 0); // img.getWidth()/2, img.getHeight()/2
        batch.end();
        
        //draw stage(buttons, etc.)
        stage.act();
        stage.draw();
    }
    
    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }
    
    public void dispose() {
    	stage.dispose();
    }
}
