package com.bitsavior.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
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

public class WinScreen extends ScreenAdapter {
	
	//variables for testing
	/** ... */
	private boolean aErrorMessage = false;
	
	//visuals e.g. stage, batch, font
	/** Stage to store Buttons, fonts, etc */
    private Stage stage;
    private SpriteBatch batch;
	private Texture imgBackground;
	private BitmapFont font;
	private Label labelText;
    
	//game result
	private int timeLeft;
	private int bugsLeft;
	
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
    	
    	//add Stage and batch&font to display objects
        stage = new Stage(new ScreenViewport());
        batch = new SpriteBatch();
        /*TODO fill in filename of background; including data type (.png)
		imgBackground = new Texture("");*/
        
        Label.LabelStyle lStyle = new Label.LabelStyle();
        font = new BitmapFont();
        lStyle.font = font;

        labelText = new Label("blablalalalla", lStyle);
        labelText.setFontScale(2.f);
        labelText.setColor(74.7f, 75.6f, 74.6f, 1.f);

        labelText.setPosition(Gdx.graphics.getWidth() * 0.015f, Gdx.graphics.getHeight() * -0.020f);
        labelText.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        //edit height: 1 is top and higher is nearer screenCenter

        labelText.setAlignment(Align.topLeft);
        stage.addActor(labelText);

        
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

       imgBackground = new Texture("winscreen_back.png");
    }
    
    @Override
    public void show() {
    	if(ScreenManager.aScreenTestMode) System.out.println("WinScreen is shown");
    	
    	//edit win message
    	labelText.setText( //TODO insert text, bugsLeft and timeLeft
    			"> Executing Task: Bitsavior.level 1\n"
                + "> Task:core:Bitsavior:level 1:compile \n\n"
    			+ "> Task:core:Bitsavior:level 1:classes\n\n"
    			+ "> Task:core:Bitsavior:level 1:main:compile successfull\n"
                        + "> Log:\n"
                        + "> compiled with 0 errors and 5 warnings\n"
                        + "> compiled in 50 seconds"
        + Gdx.graphics.getWidth());
    	
    	//set Input to stage
    	Gdx.input.setInputProcessor(stage);
    }
    
    @Override
    public void render(float delta) {    	
        Gdx.gl.glClearColor(0, 0.25f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        batch.begin();
        try {
        	batch.draw(imgBackground, 0, Gdx.graphics.getHeight() / 2, Gdx.graphics.getWidth(), Gdx.graphics.getHeight() / 2);
        }
        catch (NullPointerException e) {
        	if(aErrorMessage) System.out.println("winScreen background image is null");;
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
    	if(ScreenManager.aScreenTestMode) System.out.println("WinScreen is disposed");
    	
    	if(stage!=null) stage.dispose();
    	if(batch!=null) batch.dispose();
    	if(imgBackground!=null) imgBackground.dispose();
    }
}
