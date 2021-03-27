package com.bitsavior.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 * Screen for the user to adjust settings
 */
public class SettingsScreen extends ScreenAdapter {
	
	//variables for testing
	/** for testing the Volume adjustments */
	private boolean aStaticTest = false;
	
	/** interval to change settings per button click */
	private static int changeInterval = 10;
	
	//visuals e.g. stage, images, labels
	/** Stage to store Buttons, fonts, etc */
    private Stage stage;
    //background
    /** background */
	private Texture textureBackground;
	/** background region */
	private TextureRegion tRegion;
	/** background image */
	private Image imageBackground;
	/** font for title */
	//title
    private BitmapFont fontTitle;
    /** label to write title */
    private Label labelTitle;
    //text (music,sound)
    /** font for text */
    private BitmapFont fontText;
    /** label to write text */
    private Label labelText;
    
    //Buttons and button properties
    /** Button: to return to main menu */
    private TextButton buttonMenu;
    private TextButton buttonMusicPlus;
    private TextButton buttonMusicMinus;
    private TextButton buttonSoundPlus;
    private TextButton buttonSoundMinus;
    /** Skin for the Buttons */
    private Skin bSkin1;
    /** norm X size for Buttons */
    private float bSizeX = 300;
    /** norm Y size for Buttons */
    private float bSizeY = 100;
    /** X/Y size for small Buttons */
    private float bSizeXY = 50;
    /** X position for left buttons (-) */
    private float bMinusX = Gdx.graphics.getWidth() * 0.875f - 0.5f*bSizeXY;
    /** X position for right buttons (+) */
    private float bPlusX = Gdx.graphics.getWidth() * 0.875f + bSizeXY; // (-bSizeXY to be mirrored) not necessary due that text is not in the middle anymore
    /** lineHeight of fontText to adjust button positions */
    private float lineHeight;
    /** Y position for buttons in 1. column */
    private float bLine1Y;
    /** Y position for buttons in 2. column */
    private float bLine2Y;
    
    /**
	 * Creates the settings screen
	 * @param screenManager to access other screens
	 */
    public SettingsScreen(final ScreenManager screenManager) {
    	if(ScreenManager.aScreenTestMode) System.out.println("SettingsScreen created");
        
        //add Stage to manage objects
        stage = new Stage(new ScreenViewport());
        
        //add background as Texture wrapped in an Image
      	textureBackground = new Texture("setting_screen.png");
      	textureBackground.setWrap(TextureWrap.MirroredRepeat, TextureWrap.MirroredRepeat);
      	tRegion = new TextureRegion(textureBackground);
      	tRegion.setRegion(0, 0, textureBackground.getWidth(), textureBackground.getHeight());
      	imageBackground = new Image(tRegion);
      	imageBackground.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
      	imageBackground.setPosition(0, 0);
      	stage.addActor(imageBackground);

        //set fonts for labels
        Label.LabelStyle labelTitleStyle = new Label.LabelStyle();
        fontTitle = new BitmapFont(Gdx.files.internal("font/s100verdana_bold_blue.fnt"));
        labelTitleStyle.font = fontTitle;
        Label.LabelStyle labelTextStyle = new Label.LabelStyle();
        fontText = new BitmapFont(Gdx.files.internal("font/s32verdana_bold_blue.fnt"));
        labelTextStyle.font = fontText;
        
        //label to display title
        labelTitle = new Label("Settings", labelTitleStyle);
        labelTitle.setPosition(0, 0);
        labelTitle.setSize(Gdx.graphics.getWidth() * 0.8f, Gdx.graphics.getHeight() * 0.9f);
        labelTitle.setAlignment(Align.top);
        stage.addActor(labelTitle);
        //label to display text
        labelText = new Label("Music x%\n\nSound x%", labelTextStyle);
        labelText.setPosition(Gdx.graphics.getWidth() * 0.5f, 0);
        labelText.setSize(Gdx.graphics.getWidth() * 0.5f, Gdx.graphics.getHeight() * 0.5f);
        labelText.setAlignment(Align.top);
        stage.addActor(labelText);
        
        //load Skin for buttons
        bSkin1 = new Skin(Gdx.files.internal("skin/glassy-ui.json"));
        
        //load lineHeight of font to adjust button Y positions
        lineHeight = fontText.getLineHeight();
        //bLine(line) = Gdx.graphics.getHeight() - (2*(line)-1)*lineHeight
        bLine1Y = Gdx.graphics.getHeight() * 0.5f - 1*lineHeight;
        bLine2Y = Gdx.graphics.getHeight() * 0.5f - 3*lineHeight;
        
        //initialize menu button
        buttonMenu = new TextButton("Back to main menu", bSkin1, "small");
        buttonMenu.setSize(bSizeX, bSizeY);
        buttonMenu.setPosition(Gdx.graphics.getWidth()*0.5f - bSizeX * 0.5f,
        		Gdx.graphics.getHeight()*0.125f - bSizeY * 0.5f );
        //add listener to manage input -> add actions
        buttonMenu.addListener(new InputListener() {
    		//touchDown returning true is necessary as precondition for touchUp(less errors due to continued pressing a button->multiple action circles)
    		@Override
    		public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
    			return true;
    		}
    		@Override
    		public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                screenManager.showScreen(Screens.TITLE);
            }
       	} );
        stage.addActor(buttonMenu);
        
        //initialize Button Music+
        buttonMusicPlus = new TextButton("+"+changeInterval, bSkin1, "small");
        buttonMusicPlus.setSize(bSizeXY, bSizeXY);
        buttonMusicPlus.setPosition(bPlusX, bLine1Y);
        buttonMusicPlus.addListener(new InputListener() {
    		@Override
    		public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
    		@Override
    		public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
    			//if volume stays within [0,100] including changeInterval
    			if(AppPreferences.getMusicVolume()<=100-changeInterval) {
    				//then change the volume
    				AppPreferences.setMusicVolume(AppPreferences.getMusicVolume() + changeInterval);
                    if(aStaticTest) System.out.println("+1M = "+AppPreferences.getMusicVolume());
                }
            }
    	});
        stage.addActor(buttonMusicPlus);
        
        //initialize Button Music-
        buttonMusicMinus = new TextButton("-"+changeInterval, bSkin1, "small");
        buttonMusicMinus.setSize(bSizeXY, bSizeXY);
        buttonMusicMinus.setPosition(bMinusX, bLine1Y);
        buttonMusicMinus.addListener(new InputListener() {
    		@Override
    		public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
    		@Override
    		public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
    			if(AppPreferences.getMusicVolume()>=0+changeInterval) {
    				AppPreferences.setMusicVolume(AppPreferences.getMusicVolume() - changeInterval);
                    if(aStaticTest) System.out.println("-1M = "+AppPreferences.getMusicVolume());
                }
            }
    	});
        stage.addActor(buttonMusicMinus);
        
        //initialize Button Sound+
        buttonSoundPlus = new TextButton("+"+changeInterval, bSkin1, "small");
        buttonSoundPlus.setSize(bSizeXY, bSizeXY);
        buttonSoundPlus.setPosition(bPlusX, bLine2Y);
        buttonSoundPlus.addListener(new InputListener() {
    		@Override
    		public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
    		@Override
    		public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
    			if(AppPreferences.getSoundVolume()<=100-changeInterval) {
    				AppPreferences.setSoundVolume(AppPreferences.getSoundVolume() + changeInterval);
                    if(aStaticTest) System.out.println("+1S = "+AppPreferences.getSoundVolume());
                }
            }
    	});
        stage.addActor(buttonSoundPlus);
        
        //initialize Button Sound-
        buttonSoundMinus = new TextButton("-"+changeInterval, bSkin1, "small");
        buttonSoundMinus.setSize(bSizeXY, bSizeXY);
        buttonSoundMinus.setPosition(bMinusX, bLine2Y);
        buttonSoundMinus.addListener(new InputListener() {
    		@Override
    		public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
    		@Override
    		public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                if(AppPreferences.getSoundVolume()>=0+changeInterval) {
                	AppPreferences.setSoundVolume(AppPreferences.getSoundVolume() - changeInterval);
                    if(aStaticTest) System.out.println("-1S = "+AppPreferences.getSoundVolume());
                }
            }
    	});
        stage.addActor(buttonSoundMinus);
    }
    
    /**
     * sets the input to the stage
     */
    @Override
    public void show() {
    	if(ScreenManager.aScreenTestMode) System.out.println("SettingScreen is shown");
    	
    	//set Input to stage
    	Gdx.input.setInputProcessor(stage);
    }
    
    /**
     * updates the labelText to display current settings
     * <p> calls the stage to draw everything
     */
    @Override
    public void render(float delta) {
        //update text
        labelText.setText("Music " + AppPreferences.getMusicVolume() + "%\n\nSound " + AppPreferences.getSoundVolume() + "%");
        
        //draw stage(buttons, etc.)
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
    	if(ScreenManager.aScreenTestMode) System.out.println("SettingsScreen is disposed");
    	
    	if(stage!=null) stage.dispose();
    	if(fontTitle!=null) fontTitle.dispose();
    	if(fontText!=null) fontText.dispose();
    	if(textureBackground!=null) textureBackground.dispose();
    	if(bSkin1!=null) bSkin1.dispose();
    }
    
}
