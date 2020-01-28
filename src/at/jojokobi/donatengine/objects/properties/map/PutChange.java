package at.jojokobi.donatengine.objects.properties.map;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Map;

import at.jojokobi.donatengine.serialization.SerializationWrapper;

public class PutChange implements MapChange {
	
	private Object key;
	private Object value;

	public PutChange(Object key, Object value) {
		super();
		this.key = key;
		this.value = value;
	}
	
	public PutChange() {
		this (null, null);
	}

	@Override
	public void serialize(DataOutput buffer, SerializationWrapper serialization) throws IOException {
		serialization.serialize(key, buffer);
		serialization.serialize(value, buffer);
	}

	@Override
	public void deserialize(DataInput buffer, SerializationWrapper serialization) throws IOException {
		key = serialization.deserialize(Object.class, buffer);
		value = serialization.deserialize(Object.class, buffer);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <K, V> void apply(Map<K, V> map, SerializationWrapper serialization) {
		map.put((K) key, (V) value);
	}
	
}
