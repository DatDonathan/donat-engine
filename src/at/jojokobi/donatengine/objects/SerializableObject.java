package at.jojokobi.donatengine.objects;

import java.util.Map;

/**
 * 
 * Must have a method:
 * 		public static GameObject deserialize (Level level, double x, double y, double z, Map <String, String> args);
 * 
 * @author jojokobi
 *
 */
public interface SerializableObject {

	public Map<String, String> serialize ();
	
	public double getX ();
	
	public double getY ();
	
	public double getZ ();
	
}
