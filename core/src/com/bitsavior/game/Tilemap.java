/*
 * orientiert an
 * --> http://www.pixnbgames.com/blog/libgdx/how-to-use-libgdx-tiled-drawing-with-libgdx/
 * teilweise auch: https://gamefromscratch.com/libgdx-tutorial-11-tiled-maps-part-1-simple-orthogonal-maps/
 */

package com.bitsavior.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

/**
 * represents the map of the game and all related data and renderer
 */
public class Tilemap {

	//private Members
	private TiledMap tiledMap;
	private OrthogonalTiledMapRenderer renderer;

	//public Methods

	/**
	 * Constructor()
	 * @param tiledMap : reference to the asset
	 * @param camera : actual camera
	 */
	public Tilemap(TiledMap tiledMap, OrthographicCamera camera) {
		this.tiledMap = new TiledMap();
		this.tiledMap = tiledMap;

		renderer = new OrthogonalTiledMapRenderer(tiledMap);

		renderer.setView(camera);


	}

	/**
	 * - loads map properties
	 */
	public void create() { }

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

	public MapLayer getLayer(int LayerID)
	{
		return tiledMap.getLayers().get(LayerID);

	}
}
