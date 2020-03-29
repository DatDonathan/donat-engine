package at.jojokobi.donatengine.tiles;

import java.util.HashSet;
import java.util.Set;

public abstract class ConnectedTile extends Tile {
	
	private ConnectedTileModelSet models;

	public ConnectedTile(ConnectedTileModelSet models) {
		super(models.getModel(new HashSet<>()));
		this.models = models;
	}

	@Override
	public void update(TileSystem system, int tileX, int tileY, int tileZ, String area) {
		super.update(system, tileX, tileY, tileZ, area);
		Set<Direction> faces = new HashSet<Direction>();
		if (isCompatible(system.getTile(tileX - 1, tileY, tileZ, area))) {
			faces.add(Direction.LEFT);
		}
		if (isCompatible(system.getTile(tileX + 1, tileY, tileZ, area))) {
			faces.add(Direction.RIGHT);
		}
		if (isCompatible(system.getTile(tileX, tileY - 1, tileZ, area))) {
			faces.add(Direction.UP);
		}
		if (isCompatible(system.getTile(tileX, tileY + 1, tileZ, area))) {
			faces.add(Direction.DOWN);
		}
		if (isCompatible(system.getTile(tileX, tileY, tileZ - 1, area))) {
			faces.add(Direction.BACK);
		}
		if (isCompatible(system.getTile(tileX, tileY, tileZ + 1, area))) {
			faces.add(Direction.FORWARD);
		}
		setModel(models.getModel(faces));
	}
	
	public boolean isCompatible (Tile tile) {
		return getClass().isInstance(tile);
	}

}
