package at.jojokobi.donatengine.serialization.binary.structured;

import java.util.Map;

/**
 * 
 * All implementing classes must have a default constructor
 * 
 * @author jojo0
 *
 */
public interface StructuredSerializable {
	
	public Map<String, Object> serialize ();
	
	public void deserialize (Map<String, Object> data);

}
