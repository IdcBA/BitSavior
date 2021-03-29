package com.bitsavior.asset;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

/**
 * manages all the assets with easy access due to AssetDescriptors
 */
public class Assets
{
    /**
     * holds and manages all the assets
     */
    public AssetManager holder;

    // add all needed asset descriptors
    public static final AssetDescriptor<Texture> enemy =
            new AssetDescriptor<Texture>("spritesheet_enemy.png", Texture.class);
    public static final AssetDescriptor<Texture> antibug =
            new AssetDescriptor<Texture>("spritesheet_antibug.png", Texture.class);
    public static final AssetDescriptor<TiledMap> currentMap =
            new AssetDescriptor<TiledMap>("map_1_extended.tmx", TiledMap.class);
    public static final AssetDescriptor<Texture> player =
            new AssetDescriptor<Texture>("spritesheet_test3.png", Texture.class);
    public static final AssetDescriptor<Texture> explosion =
            new AssetDescriptor<Texture>("firework.png", Texture.class);
    public static final AssetDescriptor<Texture> pickUp =
            new AssetDescriptor<Texture>("memory-leaks.jpg", Texture.class);
    public static final AssetDescriptor<Texture> breakpoint =
            new AssetDescriptor<Texture>("metallic_light.png", Texture.class);
    public static final AssetDescriptor<Texture> light =
            new AssetDescriptor<Texture>("lightcone_imp.png", Texture.class);
    public static final AssetDescriptor<Texture> redLight =
            new AssetDescriptor<Texture>("redLight_1.png", Texture.class);
    public static final AssetDescriptor<Texture> uiRect =
            new AssetDescriptor<Texture>("ui_rect_2.png", Texture.class);
    public static final AssetDescriptor<Music> background =
            new AssetDescriptor<Music>("background_alt.mp3", Music.class);
    public static final AssetDescriptor<Sound> winMusic =
            new AssetDescriptor<Sound>("win_sound.wav", Sound.class);
    public static final AssetDescriptor<Sound> blop =
    		new AssetDescriptor<>("blop.wav", Sound.class);
    public static final AssetDescriptor<Sound> lose =
    		new AssetDescriptor<>("lose.wav", Sound.class);
    public static final AssetDescriptor<Sound> sirene =
            new AssetDescriptor<>("sirene.wav", Sound.class);
    public static final AssetDescriptor<Sound> save =
    		new AssetDescriptor<>("save.wav", Sound.class);
    /**
     * constructor
     * create new AssetManager and add a loader for tmx files
     */
    public Assets()
    {
        holder = new AssetManager();
        // add additional loader for tmx maps
        holder.setLoader(TiledMap.class, new TmxMapLoader());
    }
    /**
     * load all described assets into the assetholder
     */
    public void load()
    {
            holder.load(enemy);
            holder.load(antibug);
            holder.load(player);
            holder.load(pickUp);
            holder.load(light);
            holder.load(explosion);
            holder.load(redLight);
            holder.load(currentMap);
            holder.load(breakpoint);
            holder.load(uiRect);
            holder.load(background);
            holder.load(blop);
            holder.load(lose);
            holder.load(sirene);
            holder.load(save);
            holder.load(winMusic);

            holder.finishLoading();
    }
    /**
     * dispose all resources
     */
    public void dispose()
    {
        holder.dispose();
    }
}
