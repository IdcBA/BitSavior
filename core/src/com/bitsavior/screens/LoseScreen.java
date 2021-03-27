package com.bitsavior.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Screen which is shown after a loss
 */
public class LoseScreen extends ScreenAdapter {

	//variables for testing
	/** to test input, if true: sends messages*/
	private boolean aInputTest = false;
	/** to test input: becomes true when listener was successfully added to stage */
	private boolean sucessful = false;
	
	//visuals e.g. stage, batch, font
	/** Stage to handle input*/
    private Stage stage;
    /** texture of the background */
	private Texture textureBackground;
	/** necessary for image */
	private TextureRegion tRegion;
	/** background image (which gets displayed) */
	private Image imageBackground;
    
    /**
	 * Creates the lose screen
	 * @param screenManager to access other screens
	 */
    public LoseScreen(final ScreenManager screenManager) {
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
        
    	//add actions
    	sucessful = stage.addListener(new InputListener() {
        	@Override
        	public boolean keyDown(InputEvent event, int keyCode) {
				if(aInputTest) System.out.println("key pressed");
        		if (keyCode == Input.Keys.ESCAPE) {
					screenManager.showScreen(Screens.TITLE);
					return true;
				}
				return false;
			}
		});
    	if(aInputTest) System.out.println("stage added listener is "+sucessful);
    }
    
    /**
     * sets the input to the stage
     */
    @Override
    public void show() {
    	if(ScreenManager.aScreenTestMode) System.out.println("LoseScreen is shown");
    	
    	//set Input to stage
    	Gdx.input.setInputProcessor(stage);
    	if(aInputTest) System.out.println("stage processes");
    }
    
    /**
     * calls the stage to draw everything
     */
    @Override
    public void render(float delta) {
        stage.act();
        stage.draw();
    }
    
    /**
     * sets the InputProcessor to null to prevent bugs
     */
    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }
    
    /**
     * disposes stage and other holders (e.g. font, textures)
     */
    @Override
    public void dispose() {
    	if(ScreenManager.aScreenTestMode) System.out.println("LoseScreen is disposed");
    	
    	if(stage!=null) stage.dispose();
    	if(textureBackground!=null) textureBackground.dispose();
    }
}
