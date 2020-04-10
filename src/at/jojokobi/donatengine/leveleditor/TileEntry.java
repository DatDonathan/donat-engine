package at.jojokobi.donatengine.leveleditor;

import at.jojokobi.donatengine.serialization.structured.SerializedData;
import at.jojokobi.donatengine.serialization.structured.StructuredSerializable;
import at.jojokobi.donatengine.tiles.Tile;
import at.jojokobi.donatengine.tiles.TilePosition;

public class TileEntry implements StructuredSerializable{
	
	private static final String POSITION_KEY = "position";
	private static final String TILE_KEY = "tile";
	
	private TilePosition pos;
	private Tile tile;
	
	public TileEntry() {
		
	}
	
	public TileEntry(TilePosition pos, Tile tile) {
		super();
		this.pos = pos;
		this.tile = tile;
	}

	public TilePosition getPos() {
		return pos;
	}

	public void setPos(TilePosition pos) {
		this.pos = pos;
	}

	public Tile getTile() {
		return tile;
	}

	public void setTile(Tile tile) {
		this.tile = tile;
	}

	@Override
	public void serialize(SerializedData data) {
		data.put(POSITION_KEY, pos);
		data.put(TILE_KEY, tile);
	}

	@Override
	public void deserialize(SerializedData data) {
		pos = data.getObject(POSITION_KEY, TilePosition.class);
		tile = data.getObject(TILE_KEY, Tile.class);
	}

}
