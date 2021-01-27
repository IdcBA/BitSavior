package com.bitsavior.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.Align;

public class TitleScreen extends ScreenAdapter
{
    BitSavior game;
    /** the TitleScreen itself */
    TitleScreen tScreen;
    /** the GameScreen */
    GameScreen gScreen;
    
    /** for testing: 0 no messages; 1 send messages */
    int aScreenTestMode = 1;

    /**
	 * Constructor
	 * @param the game
	 */
    public TitleScreen(BitSavior game) {
        this.game = game;
        tScreen = this;
    }

    @Override
    public void show(){
        Gdx.input.setInputProcessor(new InputAdapter() {
            /**
             * <p>SPACE should continue loaded Level </p>
             * <p>NUM1,... should dispose loaded Level & create new Level 1,2,...<p>
             */
            @Override
            public boolean keyDown(int keyCode) {
            	
            	//SPACE to continue current level
                if (keyCode == Input.Keys.SPACE) { //keyCode == Input.Keys.SPACE //Gdx.input.isKeyPressed(Keys.SPACE)
                	if(gScreen==null) {
                		if(aScreenTestMode==1) System.out.println("no gScreen yet");
                		//gScreen = new GameScreen(game, tScreen);
                		//game.setScreen(gScreen);
                	}
                	else {
                		if(aScreenTestMode==1) System.out.println("continue last (paused) gScreen");
                		game.setScreen(gScreen); //new GameScreen(game)
                	}
                }
                
                //NUM_1 to start new Level 1
                if (keyCode == Input.Keys.NUM_1) {
                	if(gScreen==null) {
                		if(aScreenTestMode==1) System.out.println("no gScreen yet");
                	}
                	else {
                		if(aScreenTestMode==1) System.out.println("dispose active gScreen & start new Level 1");
                		gScreen.dispose();
                	}
                	gScreen = new GameScreen(game, tScreen);
            		game.setScreen(gScreen);
                }
                
                return true;
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, .25f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        /*//-->not used at the moment
        String MenuText = "Welcome to BitSavior\n\n"
        		+ "Press space to play";
        Color color = new Color(0,1,0,1);
        GlyphLayout layout = new GlyphLayout(game.font, MenuText);
        */
        
        game.batch.begin();
        game.fontTitle.getData().setScale(5f); // <--- scale Title
        game.fontText1.getData().setScale(3f);
        //draw Title
        game.fontTitle.draw(
        	game.batch, //batch
        	"Welcom to BitSavior", //text
        	Gdx.graphics.getWidth() / 2f, //x
        	Gdx.graphics.getHeight() * 0.75f, //y
        	0f, //i dont know
        	Align.center, //center text
        	false //i dont know
        	);
        //draw Key descriptions
        if(gScreen==null)
        game.fontText1.draw(
        	game.batch,
        	"Press NUM_1 to play Level 1\nPress esc to return to the Menu",
        	Gdx.graphics.getWidth() / 2f,
        	Gdx.graphics.getHeight() * 0.5f, //y <--- adjust *float to go up/down
        	0f,
        	Align.center, false);
        else
        game.fontText1.draw(
           	game.batch,
           	"Press NUM_1 to play Level 1\nPress esc to return to the Menu\nPress SPACE to continue paused level",
           	Gdx.graphics.getWidth() / 2f,
           	Gdx.graphics.getHeight() * 0.5f, //y <--- adjust *float to go up/down
           	0f,
           	Align.center, false);
        game.batch.end();
    }

    @Override
    public void hide(){
        Gdx.input.setInputProcessor(null);
    }
}
