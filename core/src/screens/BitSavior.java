package screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BitSavior extends Game {
	public ScreenManager manager;

	@Override
	public void create() {
		setScreen(new TitleScreen(this));
		
		//with ScreenManager
		manager = new ScreenManager(this);
		manager.showScreen(Screens.TITLE);
	}

	@Override
	public void dispose() {
		manager.dispose();
	}

}
