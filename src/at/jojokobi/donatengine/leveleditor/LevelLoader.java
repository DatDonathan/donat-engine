package at.jojokobi.donatengine.leveleditor;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import at.jojokobi.donatengine.level.Level;

public interface LevelLoader {

	public void load (InputStream stream) throws IOException, InvalidLevelFileException;
	
	public void apply (Level level);
	
	public void copy (Level level);
	
	public void save (OutputStream stream) throws IOException;
	
}
