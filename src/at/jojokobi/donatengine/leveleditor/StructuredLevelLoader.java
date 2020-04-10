package at.jojokobi.donatengine.leveleditor;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.objects.GameObject;
import at.jojokobi.donatengine.serialization.structured.ObjectLoader;
import at.jojokobi.donatengine.serialization.structured.PreloadedObject;
import at.jojokobi.donatengine.serialization.structured.XMLObjectLoader;
import at.jojokobi.donatengine.tiles.TileInstance;

public class StructuredLevelLoader implements LevelLoader {

	private ObjectLoader loader = new XMLObjectLoader();
	private PreloadedObject<SerializedLevel> map;

	@Override
	public void save(OutputStream stream) throws IOException {
		if (map != null) {
			map.save(stream);
		} else {
			throw new IllegalStateException("No level is loaded!");
		}
	}

	@Override
	public void load(InputStream stream) throws IOException, InvalidLevelFileException {
		map = loader.load(stream, SerializedLevel.class);
	}

	@Override
	public void apply(Level level) {
		if (map != null) {
			SerializedLevel map = this.map.create();
			//Tiles
			for (TileEntry entry : map.getTiles()) {
				level.getTileSystem().place(entry.getTile(), entry.getPos().getX(), entry.getPos().getY(), entry.getPos().getZ(), entry.getPos().getArea());
			}
			//Game objects
			for (GameObject object : map.getObjects()) {
				level.spawn(object);
			}
		} else {
			throw new IllegalStateException("No level is loaded!");
		}
	}

	@Override
	public void copy(Level level) {
		SerializedLevel map = new SerializedLevel();
		//Tile
		for (TileInstance inst : level.getTileSystem().getTiles()) {
			map.getTiles().add(new TileEntry(inst.getTilePosition(), inst.getTile()));
		}
		//Objects
		for (GameObject obj : level.getObjects()) {
			map.getObjects().add(obj);
		}
		this.map = loader.preload(map, SerializedLevel.class);
	}

}
