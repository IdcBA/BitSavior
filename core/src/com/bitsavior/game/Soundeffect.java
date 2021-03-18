package com.bitsavior.game;

import com.badlogic.gdx.audio.Sound;

public class Soundeffect{
	// private methods
	private Sound sound;
	
	
	public Soundeffect(Sound sound) {
		this.sound = sound;
	}
	
	public void play() {
		sound.play();
	}
	
	public void loop() {
		sound.loop() ;
	}
}
