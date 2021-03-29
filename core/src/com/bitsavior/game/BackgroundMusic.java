package com.bitsavior.game;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.bitsavior.screens.AppPreferences;

/**
 * represents the backgroundmusic of the actual game session
 * including the ability to play, loop, pause and stop the musicstream
 * It is also possible to adjust the volume and use a stuttering effect
 */
public class BackgroundMusic{
	/**
	 * the actual music file
	 */
	private Music music;
	/**
	 * current position inside the stream
	 */
	private float currentPosition = 0.f;
	/**
	 * enable and disable the stuttering effect
	 */
	private boolean stuttering = false;
	/**
	 * the desired length of the interval that the music should
	 * run before there is a stutter in seconds
	 */
	private long effectInterval = 0L;
	/**
	 * the time since the last effect has taken place in milliseconds
	 */
	private long timeSinceLastEffect = 0L;
	/**
	 * constructor
	 * @param music : the music file
	 */
	public BackgroundMusic(Music music)
	{
		this.music = music;
	}
	/**
	 * plays the music
	 */
	public void play() {
		music.play();
	}
	/**
	 * plays the music with a certain volume
	 * @param volume : the volume of the music that should be played
	 *               must between 0.f - 100.f
	 */
	public void play(float volume) {
		this.setVolume(volume);
		music.play();
	}
	/**
	 * activate or deactivate looping for the music
	 * @param loop : true if looping is desired, false if not
	 */
	public void setloop(boolean loop) {
		music.setLooping(loop);
	}
	/**
	 * stops the playing of the music
	 */
	public void stop() {
		music.stop();
	}
	/**
	 * sets the musicstream to the desired position in seconds
	 * @param position : desired position of the musicstream in seconds
	 */
	public void setPosition(float position) { music.setPosition(position);}
	/**
	 * gets the current position of the music stream
	 * @return : the current position of the music stream in seconds
	 */
	public float getPosition(){ return music.getPosition(); }
	/**
	 * sets the volume of the music
	 * @param volume : volume of the music
	 *               must be between 0.f and 100.f
	 */
	public void setVolume (float volume) {

		volume *= ((float) AppPreferences.getMusicVolume() / 100.f);
		if(volume <= 0.f)
			music.setVolume(0.f);
		else if(volume >= 1.f)
			music.setVolume(1.f);
		else
			music.setVolume(volume);
	}

	/**
	 * gets the current music volume
	 * @return the volume of the music from (0,1) 0 = silent, 1= loud
	 */
	public float getVolume(){ return music.getVolume(); }
	/**
	 * activates the stuttering effect with a given interval
	 * @param interval : the desired length of the interval that the music should
	 * 	  run before there is a stutter in seconds
	 */
	public void setStuttering(float interval)
	{
		effectInterval = (long)(interval * 1000);
		stuttering = true;
		timeSinceLastEffect = System.currentTimeMillis();
		currentPosition = getPosition();
	}
	/**
	 * deactivates the stuttering effect
	 */
	public void disableStuttering()
	{
		stuttering = false;
	}
	/**
	 * updates the logic for the music
	 * including effects layed over the music
	 */
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
	/**
	 * pause the actual music stream and remember the position
	 */
	public void pause () {
		music.pause();
	}
	/**
	 * dispose the music
	 */
	public void dispose() {
		music.dispose();
	}
}

