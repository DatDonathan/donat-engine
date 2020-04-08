package at.jojokobi.donatengine.objects.properties.map;

import java.util.Map;

import at.jojokobi.donatengine.serialization.binary.BinarySerializable;
import at.jojokobi.donatengine.serialization.binary.SerializationWrapper;

public interface MapChange extends BinarySerializable {
	
	public <K, V> void apply (Map<K, V> map, SerializationWrapper serialization);

}
