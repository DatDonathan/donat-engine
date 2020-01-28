package at.jojokobi.donatengine.objects.properties.map;

import java.util.Map;

import at.jojokobi.donatengine.serialization.BinarySerializable;
import at.jojokobi.donatengine.serialization.SerializationWrapper;

public interface MapChange extends BinarySerializable {
	
	public <K, V> void apply (Map<K, V> map, SerializationWrapper serialization);

}
