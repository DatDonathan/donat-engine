package at.jojokobi.donatengine.tiles;

import java.util.List;

import at.jojokobi.donatengine.util.Position;
import at.jojokobi.donatengine.util.Vector3D;

public interface TileSystem {
	
	public void place (Tile tile, int tileX, int tileY, int tileZ, String area);
	
	public void remove (int tileX, int tileY, int tileZ, String area);
	
	public Tile getTile (int tileX, int tileY, int tileZ, String area);
	
	public <T extends Tile> T getTile (Class<T> clazz, int tileX, int tileY, int tileZ, String area);
	
	public List<TileInstance> getTilesInArea (int tileX, int tileY, int tileZ, int width, int height, int length, String area);
	
	public List<TileInstance> getTilesInAbsoluteArea (int x, int y, int z, int width, int height, int length, String area);
	
	public List<TileInstance> getTiles ();
	
	public double getTileSize();
	
	public default TilePosition toTilePosition(Position position) {
		double tileSize = getTileSize();
		return new TilePosition((int) (position.getPosition().getX()/tileSize), (int) (position.getPosition().getY()/tileSize), (int) (position.getPosition().getZ()/tileSize), position.getArea());
	}
	
	public default Position toPosition(TilePosition tilePosition) {
		double tileSize = getTileSize();
		return new Position(new Vector3D(tilePosition.getX() * tileSize, tilePosition.getY() * tileSize, tilePosition.getZ() * tileSize), tilePosition.getArea());
	}
	
	public void addListener (Listener listener);
	
	public void removeListener (Listener listener);
	
	public interface Listener {
		
		public void onPlace(Tile tile, int tileX, int tileY, int tileZ, String area);
		
		public void onRemove (int tileX, int tileY, int tileZ, String area);
		
	}

}
