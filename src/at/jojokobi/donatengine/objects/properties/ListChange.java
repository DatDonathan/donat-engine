package at.jojokobi.donatengine.objects.properties;

import java.util.List;

import at.jojokobi.donatengine.serialization.BinarySerializable;
import at.jojokobi.donatengine.serialization.SerializationWrapper;

public interface ListChange extends BinarySerializable{
	
	public <E> void apply (List<E> list, SerializationWrapper serialization);

}
