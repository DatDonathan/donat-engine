package at.jojokobi.donatengine.serialization;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * 
 * A interface that is serializable and deserializeable to/from binary data.
 * 
 * In addition to the methods provided all implementing classes should have a standard constructor.
 * 
 * @author jojokobi
 *
 */
public interface BinarySerializable {

	public void serialize (DataOutput buffer) throws IOException;
	
	public void deserialize (DataInput  buffer) throws IOException;
	
}
