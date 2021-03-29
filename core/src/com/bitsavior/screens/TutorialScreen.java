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

/**
 * Screen which is shown before a level 1
 * <p> explains general game information
 */
public class TutorialScreen extends ScreenAdapter {
	
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
    /** font for the text */
    private BitmapFont fontText;
    /** label to write text */
    private Label labelText;
    
    //Button properties
    /** Skin for the Buttons */
    private Skin bSkin1;
    /** starts the game */
    private TextButton button1;
    /** norm X size for Buttons */
    private float bSizeX = 300;
    /** norm Y size for Buttons */
    private float bSizeY = 100;
    
    /**
     * Creates the tutorial screen
     * @param screenManager to access other screens
     */
    public TutorialScreen(final ScreenManager screenManager) {
    	if(ScreenManager.aScreenTestMode) System.out.println("TutorialScreen created");
    	
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
        
        //set font&label for Text
        Label.LabelStyle labelTextStyle = new Label.LabelStyle();
        fontText = new BitmapFont(Gdx.files.internal("font/s32verdana_bold_blue.fnt"));
        labelTextStyle.font = fontText;
        labelText = new Label( //TODO edit text
        		"Somewhere in germany...\n It is dark outside, light shines only out of an \nunimpressive basement window at the bottom of the street." +
                        "\nThe reason for it is a guy sitting in front of a computer,\nsteering at his screen, desperate about the things happening there.\n" +
                        "The deadline is tomorrow but how should he fix all these memory leaks hidden in his code?\n\nAs the time goes by and he wondered why the hell\n" +
                        "he decided to go for a language like C++ he fell in a restless sleep..." +
                        "\n\nRemember...\n" +
                        "- The debugger is there to protect you from what lurks in the darkness, go and find him!\n" +
                        "- You need to collect all memory leaks intime before the deadline is over and the system crashes!\n" +
                        "- Be aware of the bugs! They will be furious if you get close to them.\n" +
                        "- Next time think about using smart pointers to not get into this kind of trouble again",
        		labelTextStyle);
        labelText.setPosition(0, Gdx.graphics.getHeight() * 0.1f);
        labelText.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        labelText.setAlignment(Align.center);
        stage.addActor(labelText);
        
        //load Skin for Buttons
        bSkin1 = new Skin(Gdx.files.internal("skin/glassy-ui.json"));
        
        //initialize Button 1
        button1 = new TextButton("Continue", bSkin1, "small");
        button1.setSize(bSizeX, bSizeY);
        button1.setPosition(Gdx.graphics.getWidth() * 0.5f - bSizeX / 2,
                Gdx.graphics.getHeight() * 0.25f - (bSizeY * 0.5f) ); //TODO adjust button height
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
    }
    
    /**
     * sets the input to the stage
     */
    @Override
    public void show() {
    	if(ScreenManager.aScreenTestMode) System.out.println("TutorialScreen is shown");
    	
        //set Input to stage
        Gdx.input.setInputProcessor(stage);
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
     * disposes stage and other holders (e.g. font, textures, skins)
     */
    public void dispose() {
    	if(ScreenManager.aScreenTestMode) System.out.println("TitleScreen is disposed");
    	
        if(stage!=null) stage.dispose();
        if(fontTitle!=null) fontTitle.dispose();
        if(fontText!=null) fontText.dispose();
        if(textureBackground!=null) textureBackground.dispose();
    	if(bSkin1!=null) bSkin1.dispose();
    }
}
