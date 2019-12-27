package at.jojokobi.donatengine.objects.properties;

import java.util.List;

import at.jojokobi.donatengine.serialization.BinarySerializable;

public interface ListChange extends BinarySerializable{
	
	public <E> void apply (List<E> list);

}
