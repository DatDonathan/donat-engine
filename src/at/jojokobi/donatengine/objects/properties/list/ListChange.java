package at.jojokobi.donatengine.objects.properties.list;

import java.util.List;

import at.jojokobi.donatengine.serialization.SerializationWrapper;
import at.jojokobi.donatengine.serialization.binary.BinarySerializable;

public interface ListChange extends BinarySerializable{
	
	public <E> void apply (List<E> list, SerializationWrapper serialization);

}
