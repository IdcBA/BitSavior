package com.bitsavior.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


/**
 * First visible Screen of the game
 * @author Andreas
 */
public class ScreenMainMenu implements Screen {
	
	//private Members
	/**
	 * MainMenu Camera
	 */
	OrthographicCamera MMcamera;
	
	/**
	 * MainMenu Batch
	 */
	private SpriteBatch MMbatch;
	
	/**
	 * MainMenu BitmapFont
	 */
	private BitmapFont MMfont;
	
	//Constructor
	/**
	 * Constructor:
	 * does nothing yet
	 */
	public ScreenMainMenu() {
		
	}
	
	/**
	 * sets Camera
	 */
	public void create() {
		MMbatch = new SpriteBatch();
		MMfont = new BitmapFont();
		
		MMcamera = new OrthographicCamera();
		MMcamera.setToOrtho(false, 800, 400);
	}
	
	/**
	 * NOT screen render Method
	 */
	public void render() {
		// Clear the Screen
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		//update Camera
		MMcamera.update();
		
		//set the projection matrix of the ScreenBatch to camera's size
		MMbatch.setProjectionMatrix(MMcamera.combined);

		//"form" batch
		MMbatch.begin();
		MMfont.draw(MMbatch, "Welcome to BitSavior!!!\n"
				+ "Press 1 to enter Level 1\n"
				+ "Press P to return to the Menu", 200, 300);
		MMbatch.end();

	}

	@Override //from Screen
	public void show() {
		// TODO Auto-generated method stub
		
	}
	
	@Override //from Screen
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override //from Screen
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override //from Screen
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override //from Screen
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override //from Screen
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override //from Screen
	public void render(float delta) {
		// TODO Auto-generated method stub
		
	}

}
