package com.bitsavior.screens;//package com.bitsavior.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
//import com.bitsavior.screens.BitSavior;
//import com.bitsavior.screens.Screens;

public class TitleScreen extends ScreenAdapter
{
    //variables for testing
    /** 0: no messages; 1: send messages; 2: --- */
    private int aScreenTestMode=0;
    /** 0: no messages<p>1: send messages for Buttons */
    //private int aButtonTestMode=0; //not used atm

    /** Stage to store/draw Buttons, fonts, etc
     * <p>
     * TODO: integrate fonts, +buttons, etc (also from BitSavior AND delete batch)
     */
    private Stage stage;
    private SpriteBatch batch;
    private BitmapFont font;

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
    	
        //add Stage and batch&font to display objects
        stage = new Stage(new ScreenViewport());
        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("s64verdana_blue.fnt"));

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
                    if(aScreenTestMode==1) System.out.println("last gScreen gets loaded");
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
                Gdx.graphics.getHeight() * 0.5f - (bSizeY * 4.0f) );
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
        		Gdx.graphics.getHeight() * 0.5f - (bSizeY * 4.0f) );
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
    }

    @Override
    public void show() {
        //set Input to stage
        Gdx.input.setInputProcessor(stage);

        //reset "continue" button
        button9.setText("Continue paused Game");
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0.25f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();

        batch.begin();
        font.draw(batch, "Welcome to Bitsavior!", Gdx.graphics.getWidth() * .25f - 30f, Gdx.graphics.getHeight() * .75f);
        batch.end();
    }

    @Override
    public void hide()
    {
        Gdx.input.setInputProcessor(null);
    }

    public void dispose() {
        stage.dispose();
        batch.dispose();
        font.dispose();
    }
}