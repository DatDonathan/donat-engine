package at.jojokobi.donatengine.objects.properties.map;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Map;

import at.jojokobi.donatengine.serialization.SerializationWrapper;

public class RemoveChange implements MapChange {
	
	private Object key;
	

	public RemoveChange(Object key) {
		super();
		this.key = key;
	}
	
	public RemoveChange() {
		this(null);
	}

	@Override
	public void serialize(DataOutput buffer, SerializationWrapper serialization) throws IOException {
		serialization.serialize(key, buffer);
	}

	@Override
	public void deserialize(DataInput buffer, SerializationWrapper serialization) throws IOException {
		key = serialization.deserialize(Object.class, buffer);
	}

	@Override
	public <K, V> void apply(Map<K, V> map, SerializationWrapper serialization) {
		map.remove(key);
	}

}
