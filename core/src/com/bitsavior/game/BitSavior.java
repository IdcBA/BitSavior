package com.bitsavior.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class BitSavior extends Game {
	SpriteBatch batch;
	BitmapFont fontTitle;
	BitmapFont fontText1;

	@Override
	public void create() {
		batch = new SpriteBatch();
		fontTitle = new BitmapFont();
		fontText1 = new BitmapFont();
		setScreen(new TitleScreen(this));
	}

	@Override
	public void dispose() {
		batch.dispose();
		fontTitle.dispose();
		fontText1.dispose();
	}

}
