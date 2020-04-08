package at.jojokobi.donatengine.serialization.structured;

/**
 * 
 * All implementing classes must have a default constructor
 * 
 * @author jojo0
 *
 */
public interface StructuredSerializable {
	
	public void serialize (SerializedData data);
	
	public void deserialize (SerializedData data);

}
