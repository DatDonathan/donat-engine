package at.jojokobi.donatengine.objects.properties.map;

import java.util.Map;

import at.jojokobi.donatengine.serialization.SerializationWrapper;
import at.jojokobi.donatengine.serialization.binary.BinarySerializable;

public interface MapChange extends BinarySerializable {
	
	public <K, V> void apply (Map<K, V> map, SerializationWrapper serialization);

}
