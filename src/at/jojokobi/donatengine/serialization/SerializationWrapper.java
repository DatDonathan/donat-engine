package at.jojokobi.donatengine.serialization;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public interface SerializationWrapper {
	
	public void serialize (Object obj, DataOutput output) throws IOException;
	
	public <T> T deserialize (Class<T> clazz, DataInput input) throws IOException;

}
