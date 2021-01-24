/*
 * orientiert an
 * --> http://www.pixnbgames.com/blog/libgdx/how-to-use-libgdx-tiled-drawing-with-libgdx/
 * teilweise auch: https://gamefromscratch.com/libgdx-tutorial-11-tiled-maps-part-1-simple-orthogonal-maps/
 */

package com.bitsavior.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
//import sun.security.provider.certpath.IndexedCollectionCertStore;

/**
 * represents the map of the game and all related data and renderer
 */
public class Tilemap
		implements ICollision
{

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


	/**
	 * check object layer of the map if a collision happened
	 * @param entity : collision to be checked with
	 * @return : returns true if a collision happened
	 */
	public boolean isCollided(Entity entity)
	{
		// get all objects out of the collision layer
		MapObjects objects = tiledMap.getLayers().get(1).getObjects();
		// get the sprites bounding rectangle
		Rectangle boundaries = new Rectangle(entity.sprite.getBoundingRectangle());

		// iterate through all objects and check for collision
		for (RectangleMapObject rectangleObject : objects.getByType(RectangleMapObject.class)) {
			if (boundaries.overlaps(rectangleObject.getRectangle()))
				return true;
		}
		return false;
	}

	/**
	 * get back the required layer
	 * @param layer : layerindex
	 * @return : required layer
	 */
	public MapLayer getLayer(int layer)
	{
		return tiledMap.getLayers().get(layer);
	}

}

