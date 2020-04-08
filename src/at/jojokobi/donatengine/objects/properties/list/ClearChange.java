package at.jojokobi.donatengine.objects.properties.list;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.List;

import at.jojokobi.donatengine.serialization.binary.SerializationWrapper;

public class ClearChange implements ListChange{

	@Override
	public void serialize(DataOutput buffer, SerializationWrapper serialization) throws IOException {
		
	}

	@Override
	public void deserialize(DataInput buffer, SerializationWrapper serialization) throws IOException {
		
	}

	@Override
	public <E> void apply(List<E> list, SerializationWrapper serialization) {
		list.clear();
	}

}
