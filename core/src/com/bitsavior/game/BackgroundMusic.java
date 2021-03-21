package com.bitsavior.game;

import com.badlogic.gdx.audio.Music;

public class BackgroundMusic{
	// private methods
	private Music music;
	
	
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
	
	public void setVolume (float volume) {
		music.setVolume(volume);
	}
	
	public void pause () {
		music.pause();
	}
	
}

