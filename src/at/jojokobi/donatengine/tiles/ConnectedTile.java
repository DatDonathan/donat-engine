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
		if (system.getTile(getClass(), tileX - 1, tileY, tileZ, area) != null) {
			faces.add(Direction.LEFT);
		}
		if (system.getTile(getClass(), tileX + 1, tileY, tileZ, area) != null) {
			faces.add(Direction.RIGHT);
		}
		if (system.getTile(getClass(), tileX, tileY - 1, tileZ, area) != null) {
			faces.add(Direction.UP);
		}
		if (system.getTile(getClass(), tileX, tileY + 1, tileZ, area) != null) {
			faces.add(Direction.DOWN);
		}
		if (system.getTile(getClass(), tileX, tileY, tileZ - 1, area) != null) {
			faces.add(Direction.BACK);
		}
		if (system.getTile(getClass(), tileX, tileY, tileZ + 1, area) != null) {
			faces.add(Direction.FORWARD);
		}
		setModel(models.getModel(faces));
	}

}
