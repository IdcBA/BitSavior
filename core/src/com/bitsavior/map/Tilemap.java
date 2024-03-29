/*
 * orientiert an
 * --> http://www.pixnbgames.com/blog/libgdx/how-to-use-libgdx-tiled-drawing-with-libgdx/
 * teilweise auch: https://gamefromscratch.com/libgdx-tutorial-11-tiled-maps-part-1-simple-orthogonal-maps/
 */

package com.bitsavior.map;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.bitsavior.collision.ICollision;
import com.bitsavior.entity.Entity;

/**
 * represents the map of the game and all related data and renderer
 */
public class Tilemap implements ICollision
{
	/**
	 * holds the tiled map in .tmx format
	 */
	private TiledMap tiledMap;
	/**
	 * special tiled map renderer
	 */
	private final OrthogonalTiledMapRenderer renderer;
	/**
	 * constructor
	 * @param tiledMap : the tiled map
	 * @param camera : currently used camera
	 */
	public Tilemap(final TiledMap tiledMap, final OrthographicCamera camera) {
		this.tiledMap = new TiledMap();
		this.tiledMap = tiledMap;

		renderer = new OrthogonalTiledMapRenderer(tiledMap);
		renderer.setView(camera);
	}
	/**
	 * renders the tiled map
	 */
	public void render(){ renderer.render(); }
	/**
	 * returns the desired layer of the tiled map
	 * @param layer : layerindex
	 * @return : required layer
	 */
	public MapLayer getLayer(int layer)
	{
		try {
			return tiledMap.getLayers().get(layer);
		}
		catch(IndexOutOfBoundsException e)
		{
			System.out.println("Error: Layer: " + layer + " not available in the map!");
			return null;
		}
	}

	public boolean isCollided(final Entity entity)
	{
		// get all objects out of the collision layer
		MapObjects objects = tiledMap.getLayers().get(1).getObjects();
		// get the sprites bounding rectangle
		Rectangle boundaries = new Rectangle(entity.getBoundings());

		// iterate through all objects and check for collision
		for (RectangleMapObject rectangleObject : objects.getByType(RectangleMapObject.class)) {
			if (boundaries.overlaps(rectangleObject.getRectangle()))
				return true;
		}
		return false;
	}

}

