package com.bitsavior.game;


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

public class TitleScreen extends ScreenAdapter
{
    //variables for testing
	/** 0: no messages; 1: send messages; 2: --- */
    private int aScreenTestMode=0;
    /** 0: no messages<p>1: send messages for Buttons*/
    private int aButtonTestMode=0;
    
    //the game and visuals
    /** the game for screen management */
    private BitSavior game;
    /** Stage to store Buttons, fonts, etc */
    private Stage stage;
    /** Batch for title */
	private SpriteBatch batch;
	/** Font for title */
	private BitmapFont font;
    
    //Button properties
    /** Skin for the Buttons */
    private Skin bSkin1;
    /** Button 1: New Game */
    private TextButton button1;
    /** Button (former 9): continue */
    private TextButton buttonContinue;
    /** norm X size for Buttons */
    private int bSizeX = 300;
    /** norm Y size for Buttons */
    private int bSizeY = 100;

    /**
	 * Constructor
	 * @param the game
	 */
    public TitleScreen(final BitSavior game) {
    	//for the game
    	this.game = game;
        
        //add Stage and batch&font to display objects
        stage = new Stage(new ScreenViewport());
        batch = new SpriteBatch();
		font = new BitmapFont(Gdx.files.internal("s64verdana_blue.fnt"));
        
        //load Skin for Buttons
        bSkin1 = new Skin(Gdx.files.internal("skin/glassy-ui.json"));
        
        //initialize Button 1
        button1 = new TextButton("Start new Game", bSkin1, "small");
        button1.setSize(bSizeX, bSizeY);
        button1.setPosition(Gdx.graphics.getWidth()*0.5f - bSizeX / 2,
        		Gdx.graphics.getHeight()*0.5f - bSizeY / 2 );
        stage.addActor(button1);
        
        //initialize continue Button
        buttonContinue = new TextButton(" ", bSkin1, "small"); //edit name in show()
        buttonContinue.setSize(bSizeX, bSizeY);
        buttonContinue.setPosition(Gdx.graphics.getWidth()*0.5f  - bSizeX / 2,
        		Gdx.graphics.getHeight() / 2f - bSizeY * 2.5f ); //
        if(aButtonTestMode==1) System.out.println("Button2 x:"+ (Gdx.graphics.getWidth()*0.5f  - bSizeX / 2) + ", y:" + (Gdx.graphics.getHeight() / 2f - bSizeY * 2.5f ));
        stage.addActor(buttonContinue);
    }
    
    @Override
    public void show() {
    	//set Input to stage
    	Gdx.input.setInputProcessor(stage);
    	
    	//reset "continue" button
    	buttonContinue.setText("Continue paused Game");
    	
    	//actions for button 1
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
    	
    	//actions for continue button
    	buttonContinue.addListener(new InputListener() {
    		//touchDown returning true is necessary as precondition for touchUp
    		@Override
    		public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
    			return true;
    		}
        	@Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
	     		//continue paused game
        		if(game.manager.gameIsRunning()) {
	     			if(aScreenTestMode==1) System.out.println("last gScreen gets loaded");
	     			game.manager.showScreen(Screens.GAME);
	     		}
	     		else {
	     			buttonContinue.setText("No game in progress.");
	     		}
        	}
        } );
    	
    }

    @Override
    public void render(float delta) {    	
        Gdx.gl.glClearColor(0, 0.25f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        //draw stage(buttons, etc.)
        stage.act();
        stage.draw();
        
        //draw texts: e.g.: title
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
