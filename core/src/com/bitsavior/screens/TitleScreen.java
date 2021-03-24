package com.bitsavior.screens;//package com.bitsavior.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class TitleScreen extends ScreenAdapter
{
    //variables for testing
    /** to find best background color with QWE ASD*/
	private boolean colorChange = false;
	private float colorDepthRed = 0f;
	private float colorDepthGreen = 0.25f;
	private float colorDepthBlue = 0f;

	
    //Stage to store/draw Buttons, fonts, etc
    private Stage stage;
    
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
    /** Button 1: New Game */
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
     * Constructor
     * @param the game
     */
    public TitleScreen(final BitSavior game) {
    	if(ScreenManager.aScreenTestMode) System.out.println("TitleScreen created");

        //add Stage
        stage = new Stage(); //new (ScreenViewport())
        
        /*add background as Texture wrapped in an Image
      	textureBackground = new Texture("...");  //TODO add filename of background
      	textureBackground.setWrap(TextureWrap.MirroredRepeat, TextureWrap.MirroredRepeat);
      	tRegion = new TextureRegion(textureBackground);
      	tRegion.setRegion(0, 0, textureBackground.getWidth(), textureBackground.getHeight());
      	imageBackground = new Image(tRegion);
      	imageBackground.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
      	imageBackground.setPosition(0, 0);
      	stage.addActor(imageBackground);*/
        
        //set font&label for title
        Label.LabelStyle labelTitleStyle = new Label.LabelStyle();
        fontTitle = new BitmapFont(Gdx.files.internal("font/s64verdana_blue.fnt"));
        labelTitleStyle.font = fontTitle;
        labelTitle = new Label("Welcome to Bitsavior!", labelTitleStyle);
        labelTitle.setPosition(0, Gdx.graphics.getHeight() * 0.5f);
        labelTitle.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight() * 0.5f);
        labelTitle.setAlignment(Align.center);
        stage.addActor(labelTitle);

        //load Skin for Buttons
        bSkin1 = new Skin(Gdx.files.internal("skin/glassy-ui.json"));

        //initialize Button 1
        button1 = new TextButton("Start new Game", bSkin1, "small");
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
                game.manager.setGameLevel(1);
                game.manager.showScreen(Screens.GAME);
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
                if(game.manager.gameIsRunning()) {
                    if(ScreenManager.aScreenTestMode) System.out.println("last gScreen gets loaded");
                    game.manager.showScreen(Screens.GAME);
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
            	game.manager.showScreen(Screens.SETTINGS);
            }
        } );
        stage.addActor(buttonSettings);
        
        //for testing screens: win=NUM1 and lose=NUM2
        if(ScreenManager.aScreenEasySwitch) {
	        stage.addListener(new InputListener() {
	        	@Override
	        	public boolean keyDown(InputEvent event, int keyCode) {
					if (keyCode == Input.Keys.NUM_1) {
						game.manager.setWinStats(-1, -1);
						game.manager.showScreen(Screens.WIN);
						return true;
					}
					if (keyCode == Input.Keys.NUM_2) {
						game.manager.setLoseStats();
						game.manager.showScreen(Screens.LOSE);
						return true;
					}
					if (keyCode == Input.Keys.Q && colorChange) { //+red
						if(colorDepthRed<1) colorDepthRed += 0.125f;
				        labelTitle.setText(colorDepthRed+" "+colorDepthGreen+" "+colorDepthBlue);
					}
					if (keyCode == Input.Keys.A && colorChange) { //-red
						if(colorDepthRed>0) colorDepthRed -= 0.125f;
				        labelTitle.setText(colorDepthRed+" "+colorDepthGreen+" "+colorDepthBlue);
					}
					if (keyCode == Input.Keys.W && colorChange) { //+green
						if(colorDepthGreen<1) colorDepthGreen += 0.125f;
				        labelTitle.setText(colorDepthRed+" "+colorDepthGreen+" "+colorDepthBlue);
					}
					if (keyCode == Input.Keys.S && colorChange) { //-green
						if(colorDepthGreen>0) colorDepthGreen -= 0.125f;
				        labelTitle.setText(colorDepthRed+" "+colorDepthGreen+" "+colorDepthBlue);
					}
					if (keyCode == Input.Keys.E && colorChange) { //+blue
						if(colorDepthBlue<1) colorDepthBlue += 0.125f;
				        labelTitle.setText(colorDepthRed+" "+colorDepthGreen+" "+colorDepthBlue);
					}
					if (keyCode == Input.Keys.D && colorChange) { //-blue
						if(colorDepthBlue>0) colorDepthBlue -= 0.125f;
				        labelTitle.setText(colorDepthRed+" "+colorDepthGreen+" "+colorDepthBlue);
					}
					return false;
				}
			});
        }
    }

    @Override
    public void show() {
    	if(ScreenManager.aScreenTestMode) System.out.println("TitleScreen is shown");
    	
        //set Input to stage
        Gdx.input.setInputProcessor(stage);

        //reset "continue" button
        button9.setText("Continue paused Game");
    }

    @Override
    public void render(float delta) {
        if(colorChange) Gdx.gl.glClearColor(colorDepthRed, colorDepthGreen, colorDepthBlue, 1); 
        else Gdx.gl.glClearColor(0, 0.25f, 0, 1); //old/first green
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();
    }

    @Override
    public void hide()
    {
        Gdx.input.setInputProcessor(null);
    }

    public void dispose() {
    	if(ScreenManager.aScreenTestMode) System.out.println("TitleScreen is disposed");
    	
        if(stage!=null) stage.dispose();
        if(fontTitle!=null) fontTitle.dispose();
    }
}