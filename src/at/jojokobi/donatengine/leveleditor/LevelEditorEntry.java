package at.jojokobi.donatengine.leveleditor;

import at.jojokobi.donatengine.level.Level;

public interface LevelEditorEntry {
	
	public String getName ();
	
	public void place (double x, double y, double z, String area, Level level);
	
}
