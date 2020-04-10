package at.jojokobi.donatengine.leveleditor;

import java.util.ArrayList;
import java.util.List;

import at.jojokobi.donatengine.objects.GameObject;
import at.jojokobi.donatengine.serialization.structured.SerializedData;
import at.jojokobi.donatengine.serialization.structured.StructuredSerializable;

public class SerializedLevel implements StructuredSerializable{
	
	private static final String OBJECTS_KEY = "objects";
	private static final String TILES_KEY = "tiles";
	
	private List<GameObject> objects = new ArrayList<>();
	private List<TileEntry> tiles = new ArrayList<>();

	public List<GameObject> getObjects() {
		return objects;
	}

	public void setObjects(List<GameObject> objects) {
		this.objects = objects;
	}

	public List<TileEntry> getTiles() {
		return tiles;
	}

	public void setTiles(List<TileEntry> tiles) {
		this.tiles = tiles;
	}

	@Override
	public void serialize(SerializedData data) {
		data.put(OBJECTS_KEY, objects);
		data.put(TILES_KEY, objects);
	}

	@Override
	public void deserialize(SerializedData data) {
		
	}

}
