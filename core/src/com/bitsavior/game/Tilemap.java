/*
 * orientiert an
 * --> http://www.pixnbgames.com/blog/libgdx/how-to-use-libgdx-tiled-drawing-with-libgdx/
 * teilweise auch: https://gamefromscratch.com/libgdx-tutorial-11-tiled-maps-part-1-simple-orthogonal-maps/
 */

package com.bitsavior.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

/**
 * represents the map of the game and all related data and renderer
 */
public class Tilemap {

	//private Members
	private TiledMap tiledMap;
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
	 * Constructor()
	 * @param tiledMap : reference to the asset
	 * @param unitScale : relation of pixels per unit for the renderer
	 * @param camera : actual camera
	 */
	public Tilemap(TiledMap tiledMap, float unitScale, OrthographicCamera camera) {
		this.tiledMap = new TiledMap();
		this.tiledMap = tiledMap;

		renderer = new OrthogonalTiledMapRenderer(tiledMap, unitScale);

		renderer.setView(camera);

	}

	/**
	 * - loads map properties
	 */
	public void create() {

		//Read map properties
		MapProperties properties = tiledMap.getProperties();
		tileWidth = properties.get("tilewidth", Integer.class);
		tileHeight = properties.get("tileheight", Integer.class);
		mapWidthInTiles = properties.get("width", Integer.class);
		mapHeightInTiles = properties.get("height", Integer.class);
		mapWidthInPixels = mapWidthInTiles * tileWidth;
		mapHeightInPixels = mapHeightInTiles * tileHeight;


	}

	/**
	 * does nothing yet
	 */
	public void update() {

	}

	/**
	 * render the tiled map
	 */
	public void render() {
		renderer.render();
	}
}
