package at.jojokobi.donatengine.serialization;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public interface BinarySerializer<T> {

	public void serialize (T t, DataOutput buffer) throws IOException;
	
	public T deserialize (DataInput buffer) throws IOException;
	
	public default void serializeUnsafe (Object obj, DataOutput buffer) throws IOException {
		serialize(getSerializingClass().cast(obj), buffer);
	}
	
	public Class<T> getSerializingClass ();
	
}
