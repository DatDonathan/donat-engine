package at.jojokobi.donatengine.serialization.structured;

import java.util.Map;

public interface StructuredSerializer<T> {

public Map<String, Object> serialize ();
	
	public T deserialize (SerializedData data);
	
	public void serialize (T t, SerializedData data);
	
	public default void serializeUnsafe (Object obj, SerializedData data) {
		serialize(getSerializingClass().cast(obj), data);
	}
	
	public Class<T> getSerializingClass ();
	
}
