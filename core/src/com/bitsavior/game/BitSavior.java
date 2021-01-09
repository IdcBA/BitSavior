package com.bitsavior.game;

import com.badlogic.gdx.ApplicationAdapter;

public class BitSavior extends ApplicationAdapter
{

	Game game;

	@Override
	public void create () {	
		game = new Game();
		game.create();
	}

	@Override
	public void render () {	
		game.update();
		game.render();
	}
	
	@Override
	public void dispose () {
		game.dispose();
	}
}
