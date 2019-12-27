package at.jojokobi.donatengine.leveleditor;

import java.util.HashMap;
import java.util.Map;

import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.objects.GameObject;

public interface LevelEditorEntry {
	
	public String getName ();
	
	public GameObject createInstance (double x, double y, double z, Level level, Map<String, String> attributes);
	
	public default GameObject createInstance (double x, double y, double z, Level level) {
		return createInstance(x, y, z, level, new HashMap<String, String> ());
	}
	
}
