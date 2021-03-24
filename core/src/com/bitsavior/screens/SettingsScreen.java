package com.bitsavior.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class SettingsScreen extends ScreenAdapter {
	
	//variables for testing
	/** for testing the Volume adjustments */
	private boolean aStaticTest = false;
	
	//settings
	/** Format: integer 0 to 100 */
	private static final String PREF_MUSIC_VOLUME = "musicVolume";
	/** Format: integer 0 to 100 */
	private static final String PREF_SOUND_VOLUME = "soundVolume";
	private static final String PREFS_NAME = "b2dtut_v2";
	private static int changeInterval = 10;
	
	//visuals e.g. stage, batch, font
	/** Stage to store Buttons, fonts, etc */
    private Stage stage;
    private BitmapFont fontTitle;
    private BitmapFont fontText;
    private Label labelTitle;
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
    private int bSizeX = 300;
    /** norm Y size for Buttons */
    private int bSizeY = 100;
    /** X/Y size for small Buttons */
    private int bSizeXY = 50;
    /** X position for left buttons (-) */
    private float bMinusX = Gdx.graphics.getWidth() * 0.375f;
    /** X position for right buttons (+) */
    private float bPlusX = Gdx.graphics.getWidth() * 0.625f - bSizeXY; // -bSizeXY to be mirrored
    /** lineHeight of fontText to adjust button positions
     * <p> Y of the button regarding the line = bLine(line)Y - (2*(line)-1)*lineHeight */
    private float lineHeight;
    /** Y position for 1. column */
    private float bLine1Y;
    /** Y position for 2. column */
    private float bLine2Y;
    
    /**
	 * Constructor
	 * @param the game
	 */
    public SettingsScreen(final BitSavior game) {
    	if(ScreenManager.aScreenTestMode) System.out.println("SettingsScreen created");
        
        //add Stage to manage objects
        stage = new Stage(new ScreenViewport());

        //set fonts for labels
        Label.LabelStyle labelTitleStyle = new Label.LabelStyle();
        fontTitle = new BitmapFont(Gdx.files.internal("font/s64verdana_blue.fnt"));
        labelTitleStyle.font = fontTitle;
        Label.LabelStyle labelTextStyle = new Label.LabelStyle();
        fontText = new BitmapFont(Gdx.files.internal("font/s32verdana_blue.fnt"));
        labelTextStyle.font = fontText;
        
        //label to display title
        labelTitle = new Label("Settings", labelTitleStyle);
        labelTitle.setPosition(0, Gdx.graphics.getHeight() * 0.5f);
        labelTitle.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight() * 0.5f);
        labelTitle.setAlignment(Align.center);
        stage.addActor(labelTitle);
        //label to display text
        labelText = new Label("Music x%\n\nSound x%", labelTextStyle);
        labelText.setPosition(0, 0);
        labelText.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight() * 0.5f);
        labelText.setAlignment(Align.top);
        stage.addActor(labelText);
        
        //load Skin for buttons
        bSkin1 = new Skin(Gdx.files.internal("skin/glassy-ui.json"));
        
        //load lineHeight of font to adjust button positions
        lineHeight = fontText.getLineHeight();
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
                game.manager.showScreen(Screens.TITLE);
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
    			if(getMusicVolume()<=100-changeInterval) {
                	setMusicVolume(getMusicVolume() + changeInterval);
                    if(aStaticTest) System.out.println("+1M = "+getMusicVolume());
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
    			if(getMusicVolume()>=0+changeInterval) {
                	setMusicVolume(getMusicVolume() - changeInterval);
                    if(aStaticTest) System.out.println("-1M = "+getMusicVolume());
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
    			if(getSoundVolume()<=100-changeInterval) {
                	setSoundVolume(getSoundVolume() + changeInterval);
                    if(aStaticTest) System.out.println("+1S = "+getSoundVolume());
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
                if(getSoundVolume()>=0+changeInterval) {
                	setSoundVolume(getSoundVolume() - changeInterval);
                    if(aStaticTest) System.out.println("-1S = "+getSoundVolume());
                }
            }
    	});
        stage.addActor(buttonSoundMinus);
    }
    
    @Override
    public void show() {
    	if(ScreenManager.aScreenTestMode) System.out.println("SettingScreen is shown");
    	
    	//set Input to stage
    	Gdx.input.setInputProcessor(stage);
    }
    
    @Override
    public void render(float delta) {    	
        Gdx.gl.glClearColor(0, 0.25f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        //update text
        labelText.setText("Music " + getMusicVolume() + "%\n\nSound " + getSoundVolume() + "%");
        
        //draw stage(buttons, etc.)
        stage.act();
        stage.draw();
    }
    
    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }
    
    public void dispose() {
    	if(ScreenManager.aScreenTestMode) System.out.println("SettingsScreen is disposed");
    	
    	if(stage!=null) stage.dispose();
    	if(fontTitle!=null) fontTitle.dispose();
    	if(fontText!=null) fontText.dispose();
    }
    
    
    //methods to edit settings
    /** for volume settings */
    private static Preferences getPrefs() {
    	return Gdx.app.getPreferences(PREFS_NAME);
    }
    
    /** @return the volume of the music [0,100] as integer ! */
    static int getMusicVolume() {
    	return getPrefs().getInteger(PREF_MUSIC_VOLUME, 10);
    }
    
    /** saves the music volume
     * @param volume 0(silent) to 100(loud)
     * @return true if volume was 0 to 100 and got accepted
     */
    private boolean setMusicVolume(int volume) {
    	if (0<=volume && volume<=100) {
			getPrefs().putInteger(PREF_MUSIC_VOLUME, volume);
			getPrefs().flush();
			return true;
		}
    	else return false;
    }
    
    /** @return the volume of the sound [0,100] as integer ! */
    static int getSoundVolume() {
    	return getPrefs().getInteger(PREF_SOUND_VOLUME, 10);
    }
    
    /** saves the sound volume
     * @param volume 0(silent) to 100(loud)
     * @return true if volume was 0 to 100 and got accepted
     */
    private boolean setSoundVolume(int volume) {
    	if (0<=volume && volume<=100) {
	    	getPrefs().putInteger(PREF_SOUND_VOLUME, volume);
	    	getPrefs().flush();
	    	return true;
    	}
    	else return false;
    }
}
