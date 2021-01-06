/*
 * orientiert an
 * --> http://www.pixnbgames.com/blog/libgdx/how-to-use-libgdx-tiled-drawing-with-libgdx/
 * teilweise auch: https://gamefromscratch.com/libgdx-tutorial-11-tiled-maps-part-1-simple-orthogonal-maps/
 */

package com.bitsavior.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class TiledLayer {
	
	//private Members
	private TiledMap tiledMap;
	private AssetManager tiledMapManager;
	private OrthogonalTiledMapRenderer renderer;
	
	//map properties
	private int tileWidth;
	private int tileHeight;
	private int mapWidthInTiles;
	private int mapHeightInTiles;
	@SuppressWarnings("unused")
	private int mapWidthInPixels;
	@SuppressWarnings("unused")
	private int mapHeightInPixels;

	//public Methods
	
		/**
		 * Constructor:
		 * contains new TiledMap, AssetManager, OrthogonalTiledMapRenderer
		 */
	public TiledLayer() {
		tiledMap = new TiledMap();
		tiledMapManager = new AssetManager();
		renderer = new OrthogonalTiledMapRenderer(tiledMap);
	}
	
		/**
		 * - loads .tmx into manager
		 * - loads map properties
		 */
	public void create() {
		//setup manager
		tiledMapManager.setLoader(TiledMap.class, new TmxMapLoader());
		tiledMapManager.load("desert.tmx", TiledMap.class); //<-- zu Mapname ändern "xyz.tmx"
		tiledMapManager.finishLoading();
		tiledMap = tiledMapManager.get("desert.tmx",TiledMap.class);
		
		//Read map properties
		MapProperties properties = tiledMap.getProperties();
		tileWidth			= properties.get("tilewidth", Integer.class);
		tileHeight			= properties.get("tileheight", Integer.class);
		mapWidthInTiles		= properties.get("width", Integer.class);
		mapHeightInTiles	= properties.get("height", Integer.class);
		mapWidthInPixels	= mapWidthInTiles * tileWidth;
		mapHeightInPixels	= mapHeightInTiles * tileHeight;		
	}
	
		/**
		 * does nothing yet
		 */
	public void update() {
		
	}
	
		/**
		 * triggers OrthogonalTiledMapRenderer
		 */
	public void render() {
        renderer.render();
	}
	
		/**
		 * disposes manager
		 */
	public void dispose() {
		tiledMapManager.dispose(); //weiß nicht ob .unload("xyz") besser wäre
	}
	
}
