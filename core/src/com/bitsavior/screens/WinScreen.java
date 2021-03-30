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
 * Screen which is shown after a win
 */
public class WinScreen extends ScreenAdapter {
	
	//variables for testing
	//...
	
	//visuals e.g. stage, font
	/** Stage to store and display Buttons, etc */
    private Stage stage;
    /** texture of the background */
    private Texture textureBackground;
    /** necessary for image */
    private TextureRegion tRegionBackground;
    /** background image (which gets displayed) */
    private Image imageBackground;
    /** rectangle for "console" */
	private Texture textureConsole;
	/** necessary for image */
	private TextureRegion tRegionConsole;
	/** "console" image (which gets displayed) */
	private Image imageConsole;
	/** "console" font */
	private BitmapFont font;
	/** "console" text */
	private Label labelText;
	
	//timer to manage "console" messages
	/** time when winScreen gets shown */
	private long startTime = 0;
	/** delay between "console" messages */
	private long delayTime = 1000;
    /**
     * displayed text
     */
	private String line1;
	private String line2;
	private String line3;
	private String line4;
	private String line5;
	private String line6;
	private String line7;
	private String line8;
	private String line9;
	
    //Button properties
    /** Skin for the Buttons */
    private Skin bSkin1;
    /** Button: to return to main menu */
    private TextButton buttonMenu;
    /** Button 2: start Level 2 */
    private TextButton button2;
    /** norm X size for Buttons */
    private float bSizeX = 300;
    /** norm Y size for Buttons */
    private float bSizeY = 100;

    /**
	 * Creates the win screen
	 * @param screenManager to access other screens
	 * @param timeLeft time left in seconds
	 * @param bugsLeft number of living or not caught bugs
     * @param gameLevel level which was completed
	 */
    public WinScreen(final ScreenManager screenManager, int timeLeft, int bugsLeft, final int gameLevel) {
    	if(ScreenManager.aScreenTestMode) System.out.println("WinScreen created");
    	
    	//add Stage
        stage = new Stage(new ScreenViewport());
        
        //add background as Texture wrapped in an Image
        textureBackground = new Texture("pictures/title_screen.png");
        textureBackground.setWrap(TextureWrap.MirroredRepeat, TextureWrap.MirroredRepeat);
        tRegionBackground = new TextureRegion(textureBackground);
        tRegionBackground.setRegion(0, 0, textureBackground.getWidth(), textureBackground.getHeight());
        imageBackground = new Image(tRegionBackground);
        imageBackground.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        imageBackground.setPosition(0, 0);
        stage.addActor(imageBackground);
        
        //add "console" as Texture wrapped in an Image
		textureConsole = new Texture("UI/winscreen_back.png");
		textureConsole.setWrap(TextureWrap.MirroredRepeat, TextureWrap.MirroredRepeat);
		tRegionConsole = new TextureRegion(textureConsole);
		tRegionConsole.setRegion(0, 0, textureConsole.getWidth(), textureConsole.getHeight());
		imageConsole = new Image(tRegionConsole);
		imageConsole.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight() / 2);
		imageConsole.setPosition(0, Gdx.graphics.getHeight() / 2);
		stage.addActor(imageConsole);
		
        //add label and its font for "console" text
        Label.LabelStyle lStyle = new Label.LabelStyle();
        font = new BitmapFont(Gdx.files.internal("font/s32arial_2_white.fnt"));
        lStyle.font = font;
        labelText = new Label("", lStyle);
        labelText.setPosition(Gdx.graphics.getWidth() * 0.015f, Gdx.graphics.getHeight() * -0.020f);
        labelText.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        labelText.setAlignment(Align.topLeft);
        stage.addActor(labelText);
        
        //load Skin for Buttons
        bSkin1 = new Skin(Gdx.files.internal("skin/glassy-ui.json"));
        
        //initialize menu Button
        buttonMenu = new TextButton("Back to main menu\n(Progress lost)", bSkin1, "small");
        buttonMenu.setSize(bSizeX, bSizeY);
        buttonMenu.setPosition(Gdx.graphics.getWidth()*0.5f - bSizeX / 2,
        		Gdx.graphics.getHeight()*0.25f - bSizeY / 2 ); //height()*... from bottom
        //add listener to manage input -> add actions
        buttonMenu.addListener(new InputListener() {
    		//touchDown returning true is necessary as precondition for touchUp
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
        
        //initialize Button 2
        button2 = new TextButton("Start new Level " + (gameLevel+1), bSkin1, "small");
        button2.setSize(bSizeX, bSizeY);
        button2.setPosition(Gdx.graphics.getWidth() * 0.5f  - bSizeX / 2,
                Gdx.graphics.getHeight() * 0.5f - (bSizeY * 1.5f) );
        button2.addListener(new InputListener() {
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                screenManager.setGameLevel(gameLevel+1);
                screenManager.showScreen(Screens.GAME);
            }
        } );
        stage.addActor(button2);
        
        //TODO set lines for delayed text output
        line1 = "> Executing Task: Bitsavior.level_" + gameLevel + "\n";
        line2 = "> Task:core:Bitsavior:level_"+ gameLevel + ": compile \n\n";
		line3 = "> Task:core:Bitsavior:level_"+ gameLevel + ": classes \n\n";
		line4 = "> Task:core:Bitsavior:level_"+ gameLevel + ": main:compiling successfull\n";
        line5 = "> Log: debug/release\n";
        line6 = "> compiled with 0 errors and " + bugsLeft + " warnings\n";
        line7 = "> compiled in " + timeLeft + " seconds";
        line8 = "";
        line9 = "";
    }
    
    /**
     * sets the input to the stage
     */
    @Override
    public void show() {
    	if(ScreenManager.aScreenTestMode) System.out.println("WinScreen is shown");
    	
    	//time start of winScreen
        startTime = System.currentTimeMillis();
    	
    	//set Input to stage
    	Gdx.input.setInputProcessor(stage);
    }
    
    /**
     * updates the text
     * <p> calls the stage to draw everything
     */
    @Override
    public void render(float delta) {
    	
        //update the text
        updateText();
        
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
     * checks the current time and sets the labels text depending on this
     */
    private void updateText() {
    	//last line first for more efficiency later on
    	if(startTime+(delayTime*9) < System.currentTimeMillis())       labelText.setText(line1+line2+line3+line4+line5+line6+line7+line8+line9);
    	else if (startTime+(delayTime*8) < System.currentTimeMillis()) labelText.setText(line1+line2+line3+line4+line5+line6+line7+line8);
    	else if (startTime+(delayTime*7) < System.currentTimeMillis()) labelText.setText(line1+line2+line3+line4+line5+line6+line7);
    	else if (startTime+(delayTime*6) < System.currentTimeMillis()) labelText.setText(line1+line2+line3+line4+line5+line6);
    	else if (startTime+(delayTime*5) < System.currentTimeMillis()) labelText.setText(line1+line2+line3+line4+line5);
    	else if (startTime+(delayTime*4) < System.currentTimeMillis()) labelText.setText(line1+line2+line3+line4);
    	else if (startTime+(delayTime*3) < System.currentTimeMillis()) labelText.setText(line1+line2+line3);
    	else if (startTime+(delayTime*2) < System.currentTimeMillis()) labelText.setText(line1+line2);
    	else if (startTime+(delayTime*1) < System.currentTimeMillis()) labelText.setText(line1);
    }
    
    /**
     * disposes stage and other holders (e.g. font, textures)
     */
    @Override
    public void dispose() {
    	if(ScreenManager.aScreenTestMode) System.out.println("WinScreen is disposed");
    	
    	if(stage!=null) stage.dispose();
    	if(font!=null) font.dispose();
    	if(textureConsole!=null) textureConsole.dispose();
    	if(bSkin1!=null) bSkin1.dispose();
    }
}
