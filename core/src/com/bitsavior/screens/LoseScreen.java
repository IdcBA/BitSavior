package com.bitsavior.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class LoseScreen extends ScreenAdapter {

	//variables for testing
	/** to test input, if true: sends messages*/
	private boolean aInputTest = false;
	/** to test input: becomes true when listener was successfully added to stage */
	private boolean sucessful = false;
	
	//visuals e.g. stage, batch, font
	/** Stage to handle input*/
    private Stage stage;
	private Texture textureBackground;
	private TextureRegion tRegion;
	private Image imageBackground;
    
    /**
	 * Constructor
	 * @param the game
	 */
    public LoseScreen(final BitSavior game) {
    	if(ScreenManager.aScreenTestMode) System.out.println("LoseScreen created");
    	
        //add Stage and batch&font to display objects
        stage = new Stage();
    	
    	//add "console" background as Texture wrapped in an Image
    	textureBackground = new Texture("bluescreen_reworked.png");
    	textureBackground.setWrap(TextureWrap.MirroredRepeat, TextureWrap.MirroredRepeat);
    	tRegion = new TextureRegion(textureBackground);
    	tRegion.setRegion(0, 0, textureBackground.getWidth(), textureBackground.getHeight());
    	imageBackground = new Image(tRegion);
		imageBackground.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		imageBackground.setPosition(0, 0);
		stage.addActor(imageBackground);    	
        
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
    	if(textureBackground!=null) textureBackground.dispose();
    }
}
