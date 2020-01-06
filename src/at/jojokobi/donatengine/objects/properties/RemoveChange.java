package at.jojokobi.donatengine.objects.properties;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.List;

import at.jojokobi.donatengine.serialization.SerializationWrapper;

public class RemoveChange implements ListChange {
	
	private Object obj;

	public RemoveChange(Object obj) {
		super();
		this.obj = obj;
	}
	
	public RemoveChange() {
		
	}

	@Override
	public void serialize(DataOutput buffer, SerializationWrapper serialization) throws IOException {
		serialization.serialize(obj, buffer);
	}

	@Override
	public void deserialize(DataInput buffer, SerializationWrapper serialization) throws IOException {
		obj = serialization.deserialize(Object.class, buffer);
	}

	@Override
	public <E> void apply(List<E> list, SerializationWrapper serialization) {
		list.remove(obj);
	}
	
}
