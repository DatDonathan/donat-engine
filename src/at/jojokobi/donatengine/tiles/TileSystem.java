package at.jojokobi.donatengine.tiles;

import java.util.List;

import at.jojokobi.donatengine.util.Position;

public interface TileSystem {
	
	public void place (Tile tile, int tileX, int tileY, int tileZ, String area);
	
	public void remove (int tileX, int tileY, int tileZ, String area);
	
	public Tile getTile (int tileX, int tileY, int tileZ, String area);
	
	public List<TileInstance> getTilesInArea (int tileX, int tileY, int tileZ, int width, int height, int length, String area);
	
	public List<TileInstance> getTiles ();
	
	public TilePosition toTilePosition (Position position);

}
