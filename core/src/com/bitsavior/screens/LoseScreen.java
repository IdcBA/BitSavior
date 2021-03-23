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

public class LoseScreen extends ScreenAdapter {

	//variables for testing
	/** to test input, if true: sends messages*/
	private boolean aInputTest = false;
	/** to test input: becomes true when listener was successfully added to stage */
	private boolean sucessful = false;
	
	//visuals e.g. stage, batch, font
	/** Stage to handle input*/
    private Stage stage;
	private SpriteBatch batch;
	private Texture imgBackground;
    
    /**
	 * Constructor
	 * @param the game
	 */
    public LoseScreen(final BitSavior game) {
    	if(ScreenManager.aScreenTestMode) System.out.println("LoseScreen created");
    	
        //add Stage and batch&font to display objects
        stage = new Stage();
        batch = new SpriteBatch();
    	imgBackground = new Texture("bluescreen_reworked.png");
        
    	//actions
    	sucessful = stage.addListener(new InputListener() {
        	@Override
        	public boolean keyDown(InputEvent event, int keyCode) {
				if(aInputTest) System.out.println("key pressed");
        		if (keyCode == Input.Keys.ESCAPE) {
					game.manager.showScreen(Screens.TITLE);
					return true;
				}
				return false;
			}
		});
    	if(aInputTest) System.out.println("stage added listener is "+sucessful);
    }
    
    @Override
    public void show() {
    	if(ScreenManager.aScreenTestMode) System.out.println("LoseScreen is shown");
    	
    	//set Input to stage
    	Gdx.input.setInputProcessor(stage);
    	if(aInputTest) System.out.println("stage processes");
    }
    
    @Override
    public void render(float delta) {    	
        Gdx.gl.glClearColor(0, 0.25f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        try {
        	batch.draw(imgBackground, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }
        catch (NullPointerException e) {
        	System.out.println("loseScreen background image is null");
        }
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
    	if(ScreenManager.aScreenTestMode) System.out.println("LoseScreen is disposed");
    	
    	if(stage!=null) stage.dispose();
    	if(batch!=null) batch.dispose();
    	if(imgBackground!=null) imgBackground.dispose();
    }
}
