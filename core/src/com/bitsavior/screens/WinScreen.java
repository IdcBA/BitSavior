package com.bitsavior.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
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

public class WinScreen extends ScreenAdapter {
	
	//variables for testing
	/** true for messages */
	private boolean aErrorMessage = false;
	
	//visuals e.g. stage, font
	/** Stage to store and display Buttons, etc */
    private Stage stage;
    /** "console" background */
	private Texture textureBackground;
	/** "console" background position */
	private TextureRegion tRegion;
	/** "console" background image */
	private Image imageBackground;
	/** "console" font */
	private BitmapFont font;
	/** "console" text */
	private Label labelText;
	
	//timer to manage "console" messages
	/** time when winScreen gets shown */
	private long startTime = 0;
	/** delay between "console" messages */
	private long delayTime = 1000;
	//messages
	private String line1;
	private String line2;
	private String line3;
	private String line4;
	private String line5;
	private String line6;
	private String line7;
	private String line8;
	private String line9;
    
	//game result
	/** timeLeft to display it <p> -10 is default */
	private int timeLeft = -10;
	/** bugsLeft to display it <p> -10 is default */
	private int bugsLeft = -10;
	
    //Button properties
    /** Skin for the Buttons */
    private Skin bSkin1;
    /** Button: to return to main menu */
    private TextButton buttonMenu;
    /** norm X size for Buttons */
    private int bSizeX = 300;
    /** norm Y size for Buttons */
    private int bSizeY = 100;

    
    /**
	 * Constructor
	 * <p> TODO if necessary: edit data type of timeLeft
	 * @param game the game (for manager)
	 * @param timeLeft time (in seconds?) left
	 * @param bugsLeft number of living/not caught bugs
	 */
    public WinScreen(final BitSavior game, int timeLeft, int bugsLeft) {
    	if(ScreenManager.aScreenTestMode) System.out.println("WinScreen created");
        
        //save game results to render them in the text
    	this.timeLeft=timeLeft;
    	this.bugsLeft=bugsLeft;
    	
    	//add Stage
        stage = new Stage(new ScreenViewport());
        
        //add "console" background as Texture wrapped in an Image
		textureBackground = new Texture("winscreen_back.png");
		textureBackground.setWrap(TextureWrap.MirroredRepeat, TextureWrap.MirroredRepeat);
		tRegion = new TextureRegion(textureBackground);
		tRegion.setRegion(0, 0, textureBackground.getWidth(), textureBackground.getHeight());
		imageBackground = new Image(tRegion);
		imageBackground.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight() / 2);
		imageBackground.setPosition(0, Gdx.graphics.getHeight() / 2);
		stage.addActor(imageBackground);
		
        //add label and its font for "console" text
        Label.LabelStyle lStyle = new Label.LabelStyle();
        font = new BitmapFont(Gdx.files.internal("font/s32arial_2_white.fnt"));
        lStyle.font = font;
        labelText = new Label("", lStyle);
        //edit height: 1 is top and higher is nearer screenCenter
        labelText.setPosition(Gdx.graphics.getWidth() * 0.015f, Gdx.graphics.getHeight() * -0.020f);
        labelText.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        labelText.setAlignment(Align.topLeft);
        stage.addActor(labelText);
        
        //TODO set lines for delayed text output
        line1 = "> Executing Task: Bitsavior.level 1 \n";
        line2 = "> Task:core:Bitsavior:level 1:compile \n\n";
		line3 = "> Task:core:Bitsavior:level 1:classes \n\n";
		line4 = "> Task:core:Bitsavior:level 1:main:compile successfull\n";
        line5 = "> Log:\n";
        line6 = "> compiled with 0 errors and " + bugsLeft + " warnings\n";
        line7 = "> compiled in " + timeLeft + " seconds";
        line8 = "";
        line9 = "";

        //load Skin for Buttons
        bSkin1 = new Skin(Gdx.files.internal("skin/glassy-ui.json"));
        
        //initialize Button 1
        buttonMenu = new TextButton("Back to main menu", bSkin1, "small");
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
                game.manager.showScreen(Screens.TITLE);
            }
       	} );
        stage.addActor(buttonMenu); 
    }
    
    @Override
    public void show() {
    	if(ScreenManager.aScreenTestMode) System.out.println("WinScreen is shown");
    	
    	//time start of winScreen
        startTime = System.currentTimeMillis();
    	
    	//set Input to stage
    	Gdx.input.setInputProcessor(stage);
    }
    
    @Override
    public void render(float delta) {    	
        Gdx.gl.glClearColor(0, 0.6353f, 0.9294f, 1); //microsoft blue --> https://encycolorpedia.com/00a2ed
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        //update the text
        updateText();
        
        //draw stage(buttons, etc.)
        stage.act();
        stage.draw();
    }
    
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
    
    public void dispose() {
    	if(ScreenManager.aScreenTestMode) System.out.println("WinScreen is disposed");
    	
    	if(stage!=null) stage.dispose();
    	if(textureBackground!=null) textureBackground.dispose();
    }
}
