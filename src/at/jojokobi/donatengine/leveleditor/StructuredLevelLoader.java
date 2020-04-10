package at.jojokobi.donatengine.leveleditor;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.serialization.structured.ObjectLoader;
import at.jojokobi.donatengine.serialization.structured.PreloadedObject;
import at.jojokobi.donatengine.serialization.structured.XMLObjectLoader;

public class StructuredLevelLoader implements LevelLoader {
	
	private ObjectLoader loader = new XMLObjectLoader();
	private PreloadedObject<SerializedLevel> map;

	@Override
	public void save(OutputStream stream) throws IOException {
		
	}

	@Override
	public void load(InputStream stream) throws IOException, InvalidLevelFileException {
		
	}

	@Override
	public void apply(Level level) {
		
	}

	@Override
	public void copy(Level level) {
		
	}
	
}
