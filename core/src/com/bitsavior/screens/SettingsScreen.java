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
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class SettingsScreen extends ScreenAdapter {
	
	//variables for testing
	/** for testing the Volume adjustments */
	private int aStaticTest = 1;
	
	//settings
	private static final String PREF_MUSIC_VOLUME = "musicVolume";
	private static final String PREF_SOUND_VOLUME = "soundVolume";
	private static final String PREFS_NAME = "b2dtut";
	private float changeInterval = 0.0625f;
	
	//visuals e.g. stage, batch, font
	/** Stage to store Buttons, fonts, etc */
    private Stage stage;
    private SpriteBatch batch;
    private BitmapFont font;
    private BitmapFont fontMusic;
    private BitmapFont fontSound;
    
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
    
    /**
	 * Constructor
	 * @param the game
	 */
    public SettingsScreen(final BitSavior game) {
        
        //add Stage and batch&font to display objects
        stage = new Stage(new ScreenViewport());
        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("s32verdana_blue.fnt"));
        fontMusic = new BitmapFont(Gdx.files.internal("s32verdana_blue.fnt"));
        fontSound = new BitmapFont(Gdx.files.internal("s32verdana_blue.fnt"));

        //load Skin for Buttons
        bSkin1 = new Skin(Gdx.files.internal("skin/glassy-ui.json"));
        
        
        //initialize Button 1
        buttonMenu = new TextButton("Back to main menu", bSkin1, "small");
        buttonMenu.setSize(bSizeX, bSizeY);
        buttonMenu.setPosition(Gdx.graphics.getWidth()*0.5f - bSizeX / 2,
        		Gdx.graphics.getHeight()*0.25f - bSizeY * 0.5f ); //height()*... from bottom
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
        buttonMusicPlus = new TextButton("+M", bSkin1, "small");
        buttonMusicPlus.setSize(bSizeXY, bSizeXY);
        buttonMusicPlus.setPosition(Gdx.graphics.getWidth()*0.625f + bSizeXY * 1.5f,
        		Gdx.graphics.getHeight()*0.5f - bSizeXY * 0.5f );
        buttonMusicPlus.addListener(new InputListener() {
    		@Override
    		public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
    		@Override
    		public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
    			if(getMusicVolume()<1) {
                	setMusicVolume(getMusicVolume() + changeInterval);
                    if(aStaticTest==1) System.out.println("+1M = "+getMusicVolume());
                }
            }
    	});
        stage.addActor(buttonMusicPlus);
        
        //initialize Button Music-
        buttonMusicMinus = new TextButton("-M", bSkin1, "small");
        buttonMusicMinus.setSize(bSizeXY, bSizeXY);
        buttonMusicMinus.setPosition(Gdx.graphics.getWidth()*0.625f - bSizeXY * 0.5f,
        		Gdx.graphics.getHeight()*0.5f - bSizeXY * 0.5f );
        buttonMusicMinus.addListener(new InputListener() {
    		@Override
    		public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
    		@Override
    		public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
    			if(getMusicVolume()>0) {
                	setMusicVolume(getMusicVolume() - changeInterval);
                    if(aStaticTest==1) System.out.println("-1M = "+getMusicVolume());
                }
            }
    	});
        stage.addActor(buttonMusicMinus);
        
        //initialize Button Sound+
        buttonSoundPlus = new TextButton("+S", bSkin1, "small");
        buttonSoundPlus.setSize(bSizeXY, bSizeXY);
        buttonSoundPlus.setPosition(Gdx.graphics.getWidth()*0.625f + bSizeXY * 1.5f,
        		Gdx.graphics.getHeight()*0.5f - bSizeXY * 2.5f );
        buttonSoundPlus.addListener(new InputListener() {
    		@Override
    		public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
    		@Override
    		public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
    			if(getSoundVolume()<1) {
                	setSoundVolume(getSoundVolume() + changeInterval);
                    if(aStaticTest==1) System.out.println("+1S = "+getSoundVolume());
                }
            }
    	});
        stage.addActor(buttonSoundPlus);
        
        //initialize Button Sound-
        buttonSoundMinus = new TextButton("-S", bSkin1, "small");
        buttonSoundMinus.setSize(bSizeXY, bSizeXY);
        buttonSoundMinus.setPosition(Gdx.graphics.getWidth()*0.625f - bSizeXY * 0.5f,
        		Gdx.graphics.getHeight()*0.5f - bSizeXY * 2.5f );
        buttonSoundMinus.addListener(new InputListener() {
    		@Override
    		public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
    		@Override
    		public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                if(getSoundVolume()>0) {
                	setSoundVolume(getSoundVolume() - changeInterval);
                    if(aStaticTest==1) System.out.println("-1S = "+getSoundVolume());
                }
            }
    	});
        stage.addActor(buttonSoundMinus);
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
        
        //draw stage(buttons, etc.)
        stage.act();
        stage.draw();
        
        //draw fonts
        batch.begin();
        font.draw(batch, "Settings",
        		Gdx.graphics.getWidth() * 0.25f , Gdx.graphics.getHeight() * .75f);
        fontMusic.draw(batch, "Music " + getMusicVolume()*100 + "%",
        		Gdx.graphics.getWidth() * 0.375f , Gdx.graphics.getHeight() * 0.5f - bSizeXY * 0.0f);
        fontSound.draw(batch, "Sound " + getSoundVolume()*100 + "%",
        		Gdx.graphics.getWidth() * 0.375f , Gdx.graphics.getHeight() * 0.5f - bSizeXY * 2.0f);
        batch.end();
    }
    
    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }
    
    public void dispose() {
    	stage.dispose();
    	batch.dispose();
    	font.dispose();
    	fontMusic.dispose();
    	fontSound.dispose();
    }
    
    
    //methods to edit settings
    /** for volume settings */
    private Preferences getPrefs() {
    	return Gdx.app.getPreferences(PREFS_NAME);
    }
    
    /** returns the volume of the music [0,1] */
    public float getMusicVolume() {
    	return getPrefs().getFloat(PREF_MUSIC_VOLUME, 0.1f);
    }
    
    /** saves the music volume
     * @param volume 0(silent) to 1(loud)
     * @return true if volume was 0 to 1 and got accepted
     */
    private boolean setMusicVolume(float volume) {
    	if (0<=volume && volume<=1) {
			getPrefs().putFloat(PREF_MUSIC_VOLUME, volume);
			getPrefs().flush();
			return true;
		}
    	else return false;
    }
    
    /** returns the volume of the sound [0,1] */
    public float getSoundVolume() {
    	return getPrefs().getFloat(PREF_SOUND_VOLUME, 0.1f);
    }
    
    /** saves the sound volume
     * @param volume 0(silent) to 1(loud)
     * @return true if volume was 0 to 1 and got accepted
     */
    private boolean setSoundVolume(float volume) {
    	if (0<=volume && volume<=1) {
	    	getPrefs().putFloat(PREF_SOUND_VOLUME, volume);
	    	getPrefs().flush();
	    	return true;
    	}
    	else return false;
    }
}
