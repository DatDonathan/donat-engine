package at.jojokobi.donatengine.tiles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MapTileSystem implements TileSystem {

	private double tileSize;
	private List<Listener> listeners = new ArrayList<>();
	private Map<TilePosition, Tile> tiles = new HashMap<> ();
	
	
	public MapTileSystem(double tileSize) {
		super();
		this.tileSize = tileSize;
	}

	@Override
	public void place(Tile tile, int tileX, int tileY, int tileZ, String area) {
		tiles.put(new TilePosition(tileX, tileY, tileZ, area), tile);
		listeners.forEach(l -> l.onPlace(tile, tileX, tileY, tileZ, area));
	}

	@Override
	public void remove(int tileX, int tileY, int tileZ, String area) {
		tiles.remove(new TilePosition(tileX, tileY, tileZ, area));
		listeners.forEach(l -> l.onRemove(tileX, tileY, tileZ, area));
	}

	@Override
	public Tile getTile(int tileX, int tileY, int tileZ, String area) {
		return tiles.get(new TilePosition(tileX, tileY, tileZ, area));
	}

	@Override
	public <T extends Tile> T getTile(Class<T> clazz, int tileX, int tileY, int tileZ, String area) {
		Tile tile = getTile(tileX, tileY, tileZ, area);
		return clazz.isInstance(tile) ? clazz.cast(tile) : null;
	}

	@Override
	public List<TileInstance> getTilesInArea(int tileX, int tileY, int tileZ, int width, int height, int length,
			String area) {
		List<TileInstance> instances = new ArrayList<>();
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				for (int z = 0; z < length; z++) {
					Tile tile = getTile(tileX, tileY, tileZ, area);
					if (tile != null) {
						instances.add(new TileInstance(this, new TilePosition(tileX, tileY, tileZ, area), tile));
					}
				}
			}
		}
		return instances;
	}

	@Override
	public List<TileInstance> getTiles() {
		List<TileInstance> instances = new ArrayList<>();
		for (var e : tiles.entrySet()) {
			instances.add(new TileInstance(this, e.getKey(), e.getValue()));
		}
		return instances;
	}

	@Override
	public void addListener(Listener listener) {
		listeners.add(listener);
	}

	@Override
	public void removeListener(Listener listener) {
		listeners.remove(listener);
	}

	@Override
	public List<TileInstance> getTilesInAbsoluteArea(double x, double y, double z, double width, double height, double length,
			String area) {
		return getTilesInArea((int) (x/tileSize), (int) (y/tileSize), (int) (z/tileSize), (int) Math.ceil(width/tileSize), (int) Math.ceil(height/tileSize), (int) Math.ceil(length/tileSize), area);
	}

	@Override
	public double getTileSize() {
		return tileSize;
	}

}
