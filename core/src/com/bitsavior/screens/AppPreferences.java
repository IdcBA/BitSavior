package com.bitsavior.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * "static" class that gives access to the settings made by the user
 */
public class AppPreferences {
	//statics to save setting
	/**
	 * saves the music Volume
	 * <p>Format: integer 0 to 100
	 */
	private static final String PREF_MUSIC_VOLUME = "musicVolume";
	/**
	 * saves the sound volume
	 * <p>Format: integer 0 to 100
	 */
	private static final String PREF_SOUND_VOLUME = "soundVolume";
	/**
	 * pref name
	 */
	private static final String PREFS_NAME = "b2dtut_v2";
    //methods to edit settings
    /**
	 * for volume settings
	 * @return : the current preferences
	 */
    private static Preferences getPrefs() {
    	return Gdx.app.getPreferences(PREFS_NAME);
    }
    /**
	 * gets the music volume
	 * @return the volume of the music [0,100] as integer
	 */
    public static int getMusicVolume() {
    	return getPrefs().getInteger(PREF_MUSIC_VOLUME, 10);
    }
    /**
	 * saves the music volume
     * @param volume 0(silent) to 100(loud)
     * @return true if volume was 0 to 100 and got accepted
     */
    public static boolean setMusicVolume(int volume) {
    	if (0<=volume && volume<=100) {
			getPrefs().putInteger(PREF_MUSIC_VOLUME, volume);
			getPrefs().flush();
			return true;
		}
    	else return false;
    }
    /**
	 * gets the sound volume
	 * @return the volume of the sound [0,100] as integer
	 */
    public static int getSoundVolume() {
    	return getPrefs().getInteger(PREF_SOUND_VOLUME, 10);
    }
    
    /**
	 * saves the sound volume
     * @param volume 0(silent) to 100(loud)
     * @return true if volume was 0 to 100 and got accepted
     */
    public static boolean setSoundVolume(int volume) {
    	if (0<=volume && volume<=100) {
	    	getPrefs().putInteger(PREF_SOUND_VOLUME, volume);
	    	getPrefs().flush();
	    	return true;
    	}
    	else return false;
    }
}
