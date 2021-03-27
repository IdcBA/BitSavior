package com.bitsavior.screens;//package com.bitsavior.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * Main menu and first screen which is shown
 */
public class TitleScreen extends ScreenAdapter
{	
    /** to store/draw Buttons, Labels, Images */
    private Stage stage;
    
    //visuals
    /** background */
	private Texture textureBackground;
	/** background region */
	private TextureRegion tRegion;
	/** background image */
	private Image imageBackground;
    /** font for the title */
    private BitmapFont fontTitle;
    /** label to write title */
    private Label labelTitle;

    //Button properties
    /** Skin for the Buttons */
    private Skin bSkin1;
    /** Button 1: starts a new level 1*/
    private TextButton button1;
    /** Button 9: continues if there is a paused game */
    private TextButton button9;
    /** Button Exit: exits the game */
    private TextButton buttonExit;
    /** Button Settings: opens settings menu*/
    private TextButton buttonSettings; 
    /** norm X size for Buttons */
    private float bSizeX = 300;
    /** norm Y size for Buttons */
    private float bSizeY = 100;

    /**
     * Creates the main menu screen
     * @param screenManager to access other screens
     */
    public TitleScreen(final ScreenManager screenManager) {
    	if(ScreenManager.aScreenTestMode) System.out.println("TitleScreen created");

        //add Stage
        stage = new Stage();
        
        //add background as Texture wrapped in an Image
      	textureBackground = new Texture("title_screen.png");
      	textureBackground.setWrap(TextureWrap.MirroredRepeat, TextureWrap.MirroredRepeat);
      	tRegion = new TextureRegion(textureBackground);
      	tRegion.setRegion(0, 0, textureBackground.getWidth(), textureBackground.getHeight());
      	imageBackground = new Image(tRegion);
      	imageBackground.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
      	imageBackground.setPosition(0, 0);
      	stage.addActor(imageBackground);
        
        //set font&label for title
        Label.LabelStyle labelTitleStyle = new Label.LabelStyle();
        fontTitle = new BitmapFont(Gdx.files.internal("font/s100verdana_bold_blue.fnt"));
        labelTitleStyle.font = fontTitle;
        labelTitle = new Label("Welcome to\nBitsavior", labelTitleStyle);
        labelTitle.setPosition(0, Gdx.graphics.getHeight() * 0.5f);
        labelTitle.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight() * 0.5f);
        labelTitle.setAlignment(Align.center);
        stage.addActor(labelTitle);

        //load Skin for Buttons
        bSkin1 = new Skin(Gdx.files.internal("skin/glassy-ui.json"));

        //initialize Button 1
        button1 = new TextButton("Start new Level 1", bSkin1, "small");
        button1.setSize(bSizeX, bSizeY);
        button1.setPosition(Gdx.graphics.getWidth() * 0.5f - bSizeX / 2,
                Gdx.graphics.getHeight() * 0.5f - (bSizeY * 0.5f) );
        //add listener to manage input -> add actions
        button1.addListener(new InputListener() {
            //touchDown returning true is necessary as precondition for touchUp
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                screenManager.setGameLevel(1);
                screenManager.showScreen(Screens.GAME);
            }
        } );
        stage.addActor(button1);

        //initialize continue Button 9
        button9 = new TextButton(" ", bSkin1, "small"); //edit name in show()
        button9.setSize(bSizeX, bSizeY);
        button9.setPosition(Gdx.graphics.getWidth() * 0.5f  - bSizeX / 2,
                Gdx.graphics.getHeight() * 0.5f - (bSizeY * 2.0f) );
        button9.addListener(new InputListener() {
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                //continue paused game...
                if(screenManager.gameIsRunning()) {
                    if(ScreenManager.aScreenTestMode) System.out.println("last gScreen gets loaded");
                    screenManager.showScreen(Screens.GAME);
                }
                //...or edit Buttons text
                else {
                    button9.setText("No game in progress.");
                }
            }
        } );
        stage.addActor(button9);
        
        //initialize exit Button
        buttonExit = new TextButton("Exit the game", bSkin1, "small");
        buttonExit.setSize(bSizeX, bSizeY);
        buttonExit.setPosition(Gdx.graphics.getWidth() * 0.25f - bSizeX / 2,
                Gdx.graphics.getHeight() * 0.125f - bSizeY * 0.5f );
        buttonExit.addListener(new InputListener() {
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
        		Gdx.app.exit();
            }
        } );
        stage.addActor(buttonExit);
        
        //initializes settings Button
        buttonSettings = new TextButton("Settings", bSkin1, "small");
        buttonSettings.setSize(bSizeX, bSizeY);
        buttonSettings.setPosition(Gdx.graphics.getWidth() * 0.75f - bSizeX / 2,
        		Gdx.graphics.getHeight() * 0.125f - bSizeY * 0.5f );
        buttonSettings.addListener(new InputListener() {
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            	screenManager.showScreen(Screens.SETTINGS);
            }
        } );
        stage.addActor(buttonSettings);
        
        //for testing screens: win=NUM1 and lose=NUM2
        if(ScreenManager.aScreenEasySwitch) {
	        stage.addListener(new InputListener() {
	        	@Override
	        	public boolean keyDown(InputEvent event, int keyCode) {
					if (keyCode == Input.Keys.NUM_1) {
						screenManager.setWinStats(-1, -1, 0);
						screenManager.showScreen(Screens.WIN);
						return true;
					}
					if (keyCode == Input.Keys.NUM_2) {
						screenManager.setLoseStats();
						screenManager.showScreen(Screens.LOSE);
						return true;
					}
					return false;
				}
			});
        }
    }

    /**
     * sets the input to the stage
     * <p> resets the text of the continue button
     */
    @Override
    public void show() {
    	if(ScreenManager.aScreenTestMode) System.out.println("TitleScreen is shown");
    	
        //set Input to stage
        Gdx.input.setInputProcessor(stage);

        //reset "continue" button
        button9.setText("Continue paused Game");
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
    public void dispose() {
    	if(ScreenManager.aScreenTestMode) System.out.println("TitleScreen is disposed");
    	
        if(stage!=null) stage.dispose();
        if(fontTitle!=null) fontTitle.dispose();
        if(textureBackground!=null) textureBackground.dispose();
    	if(bSkin1!=null) bSkin1.dispose();
    }
}