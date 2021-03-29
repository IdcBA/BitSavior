package com.bitsavior.game;

import com.badlogic.gdx.audio.Sound;
import com.bitsavior.screens.AppPreferences;

/**
 * a sound effect. Soundeffects can be played, looped and stopped
 * Notice that every sound should be disposed after used
 */
public class SoundEffect {
	/**
	 * the current sound
	 */
	private final Sound sound;
	/**
	 * constructor
	 * @param sound : the sound should be in .wav format, 16bit sample rate and not larger then 1MB
	 */
	public SoundEffect(Sound sound) { this.sound = sound; }
	/**
	 * plays the sound using the current sound settings
	 */
	public void play()
	{
		sound.play((float)AppPreferences.getSoundVolume() / 100);

	}
	/**
	 * loops the sound
	 */
	public void loop() {
		sound.loop() ;
	}
	/**
	 * stops the sound
	 */
	public void stop(){ sound.stop(); }
	/**
	 * dispose the sound
	 */
	public void dispose() {
		sound.dispose();
	}
}

