package com.bitsavior.asset;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

/**
 * maintains the assets
 */
public class Assets
{
    public AssetManager holder;

    // add all needed asset descriptors
    public static final AssetDescriptor<Texture> enemy =
            new AssetDescriptor<Texture>("spritesheet_enemy.png", Texture.class);
    public static final AssetDescriptor<Texture> antibug =
            new AssetDescriptor<Texture>("spritesheet_antibug.png", Texture.class);

    public static final AssetDescriptor<TiledMap> currentMap =
            new AssetDescriptor<TiledMap>("map_1.tmx", TiledMap.class);

    public static final AssetDescriptor<Texture> player =
            new AssetDescriptor<Texture>("spritesheet_test3.png", Texture.class);

    public static final AssetDescriptor<Texture> pickUp =
            new AssetDescriptor<Texture>("memory-leaks.jpg", Texture.class);
    public static final AssetDescriptor<Texture> breakpoint =
            new AssetDescriptor<Texture>("breakpoint.png", Texture.class);

    public static final AssetDescriptor<Texture> light =
            new AssetDescriptor<Texture>("lightcone3.png", Texture.class);
    public static final AssetDescriptor<Texture> redLight =
            new AssetDescriptor<Texture>("redLight_1.png", Texture.class);
    public static final AssetDescriptor<Texture> uiRect =
            new AssetDescriptor<Texture>("ui_rect_2.png", Texture.class);
    public static final AssetDescriptor<Music> background =
            new AssetDescriptor<Music>("backgroundmusic.mp3", Music.class);
    public static final AssetDescriptor<Sound> blop =
    		new AssetDescriptor<>("blop.wav", Sound.class);
    public static final AssetDescriptor<Sound> lose =
    		new AssetDescriptor<>("lose.wav", Sound.class);
    public static final AssetDescriptor<Sound> save =
    		new AssetDescriptor<>("save.wav", Sound.class);



    
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
            holder.load(redLight);
            holder.load(currentMap);
            holder.load(breakpoint);
            holder.load(uiRect);

            holder.load(background);
            holder.load(blop);
            holder.load(lose);
            holder.load(save);

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
