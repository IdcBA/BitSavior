package screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
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
    int aScreenTestMode=0;
    /** 0: no messages<p>1: send messages for Buttons*/
    int aButtonTestMode=1;
    
    //the game and its screens
    BitSavior game;
    GameScreen gScreen;
    /** the TitleScreen itself, as a "jump back" for the GameScreen */
    TitleScreen tScreen;
    
    /** Stage to store Buttons, fonts, etc
     * <p>
     * TODO: integrate fonts, +buttons, etc (also from BitSavior AND delete batch)
     */
    private Stage stage;
    
    //Button properties
    /** Skin for the Buttons */
    private Skin bSkin1;
    /** Button 1 */
    private TextButton button1;
    /** Button 2 */
    private TextButton button2;
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
        tScreen = this;
        
        //add Stage to display objects
        stage = new Stage(new ScreenViewport());
        
        //load Skin for Buttons
        bSkin1 = new Skin(Gdx.files.internal("skin/glassy-ui.json"));
        
        //initialize Button 1
        button1 = new TextButton("Start new Game", bSkin1, "small");
        button1.setSize(bSizeX, bSizeY);
        button1.setPosition(Gdx.graphics.getWidth()*0.5f - bSizeX / 2,
        		Gdx.graphics.getHeight()*0.5f - bSizeY / 2 );
        //actions for Button 1 in show()
        stage.addActor(button1);
        
        //initialize Button 2
        button2 = new TextButton("Continue Game", bSkin1, "small");
        button2.setSize(bSizeX, bSizeY);
        button2.setPosition(Gdx.graphics.getWidth()*0.5f  - bSizeX / 2,
        		Gdx.graphics.getHeight() / 2f - bSizeY * 2.5f );
        //actions for Button 2 in show()
        if(aButtonTestMode==1) System.out.println("Button2 x:"+ (Gdx.graphics.getWidth()*0.5f  - bSizeX / 2) + ", y:" + (Gdx.graphics.getHeight() / 2f - bSizeY * 2.5f ));
        stage.addActor(button2);
    }
    
    @Override
    public void show() {
    	//set Input to stage
    	Gdx.input.setInputProcessor(stage);
    	
    	//reset "continue" button
    	button2.setText("Continue Game");
    	
    	//actions for button 1
    	button1.addListener(new InputListener() {
    		@Override
    		public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
    			//if no game is started/paused
    			if(gScreen==null) {
    				if(aScreenTestMode==1) System.out.println("no gScreen");
                	gScreen = new GameScreen(game, tScreen);
                	game.setScreen(gScreen);
                }
    			else {
            		System.out.println("existing game gets replaced");
            		gScreen.dispose();
            		gScreen = new GameScreen(game, tScreen);
                	game.setScreen(gScreen);
            	}
    			return true;
            }
       	} );
    	
    	//actions for button 2
    	button2.addListener(new InputListener() {
        	@Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
	     		if(gScreen!=null) {
	     			if(aScreenTestMode==1) System.out.println("last gScreen gets loaded");
	     			game.setScreen(gScreen);
	     		}
	     		else {
	     			button2.setText("No game in progress.");
	     		}
				return true;
        	}
        } );
    	
    }

    @Override
    public void render(float delta) {    	
        Gdx.gl.glClearColor(0, 0.25f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        stage.act();
        stage.draw();
        
        game.batch.begin();
        game.font.draw(game.batch, "Welcome to Bitsavior!", Gdx.graphics.getWidth() * .25f - 30f, Gdx.graphics.getHeight() * .75f);
        //game.font.draw(game.batch, "Press space to play.", Gdx.graphics.getWidth() * .25f, Gdx.graphics.getHeight() * .25f);
        game.batch.end();
    }

    @Override
    public void hide()
    {
        Gdx.input.setInputProcessor(null);
    }
}
