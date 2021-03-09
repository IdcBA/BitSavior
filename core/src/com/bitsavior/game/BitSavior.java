package com.bitsavior.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BitSavior extends Game {
	SpriteBatch batch;
	BitmapFont font;

	@Override
	public void create() {
		batch = new SpriteBatch();
		font = new BitmapFont(Gdx.files.internal("s64verdana_blue.fnt"));
		setScreen(new TitleScreen(this));
	}

	@Override
	public void dispose() {
		batch.dispose();
		font.dispose();
	}

}
