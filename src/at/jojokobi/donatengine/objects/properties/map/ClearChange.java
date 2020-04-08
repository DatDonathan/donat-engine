package at.jojokobi.donatengine.objects.properties.map;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Map;

import at.jojokobi.donatengine.serialization.binary.SerializationWrapper;

public class ClearChange implements MapChange{

	@Override
	public void serialize(DataOutput buffer, SerializationWrapper serialization) throws IOException {
		
	}

	@Override
	public void deserialize(DataInput buffer, SerializationWrapper serialization) throws IOException {
		
	}

	@Override
	public <K, V> void apply(Map<K, V> map, SerializationWrapper serialization) {
		map.clear();
	}
	
}
