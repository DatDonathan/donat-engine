package at.jojokobi.donatengine.objects.properties.list;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.List;

import at.jojokobi.donatengine.serialization.binary.SerializationWrapper;

public class SetChange implements ListChange {
	
	private int index;
	private Object object;
	
	public SetChange(int index, Object object) {
		super();
		this.index = index;
		this.object = object;
	}
	
	public SetChange() {
		
	}
	
	@Override
	public void serialize(DataOutput buffer, SerializationWrapper serialization) throws IOException {
		buffer.writeInt(index);
		serialization.serialize(object, buffer);
	}

	@Override
	public void deserialize(DataInput buffer, SerializationWrapper serialization) throws IOException {
		index = buffer.readInt();
		object = serialization.deserialize(Object.class, buffer);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E> void apply(List<E> list, SerializationWrapper serialization) {
		list.set(index, (E) object);
	}

}
