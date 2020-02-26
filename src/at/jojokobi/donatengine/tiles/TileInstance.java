package at.jojokobi.donatengine.tiles;

public class TileInstance {
	
	private TilePosition position;
	private Tile tile;
	
	public TileInstance(TilePosition position, Tile tile) {
		super();
		this.position = position;
		this.tile = tile;
	}

	public TilePosition getPosition() {
		return position;
	}

	public Tile getTile() {
		return tile;
	}
}
