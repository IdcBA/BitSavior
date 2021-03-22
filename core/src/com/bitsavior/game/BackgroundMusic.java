package com.bitsavior.game;

import com.badlogic.gdx.audio.Music;

public class BackgroundMusic{
	// private methods
	private Music music;
	private float currentPosition = 0.f;
	private boolean stuttering = false;
	private long effectInterval = 0L;
	private long timeSinceLastEffect = 0L;
	
	
	public BackgroundMusic(Music music) {
		this.music = music;
	}
	
	public void play() {
		music.play();
	}
	
	public void play(float volume) {
		music.setVolume(volume);
		music.play();
	}
	public void setloop(boolean loop) {
		music.setLooping(loop);
	}
	
	public void stop() {
		music.stop();
	}

	public void setPosition(float position) { music.setPosition(position);}

	public float getPosition(){ return music.getPosition(); }
	
	public void setVolume (float volume) {
		music.setVolume(volume);
	}

	public void setStuttering(float interval)
	{
		effectInterval = (long)(interval * 1000);
		stuttering = true;
		timeSinceLastEffect = System.currentTimeMillis();
		currentPosition = getPosition();
	}

	public void update()
	{
		if(stuttering)
		{
			if(timeSinceLastEffect < (System.currentTimeMillis() - effectInterval))
			{
				timeSinceLastEffect = System.currentTimeMillis();
				pause();
				setPosition(currentPosition);
				play();
			}
		}
	}
	
	public void pause () {
		music.pause();
	}

	public void dispose() {
		music.dispose();		
	}
	
}

