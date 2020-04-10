package at.jojokobi.donatengine.leveleditor;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import at.jojokobi.donatengine.level.Level;

public interface LevelLoader {

	public void load (InputStream stream, Level level) throws IOException, InvalidLevelFileException;
	
	public void save (OutputStream stream, Level level) throws IOException;
	
}
