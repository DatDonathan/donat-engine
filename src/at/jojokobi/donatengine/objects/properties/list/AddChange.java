package at.jojokobi.donatengine.objects.properties.list;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.List;

import at.jojokobi.donatengine.serialization.binary.SerializationWrapper;

public class AddChange implements ListChange {

	private int index;
	private Object obj;

	public AddChange(int index, Object obj) {
		super();
		this.index = index;
		this.obj = obj;
	}

	public AddChange() {

	}

	@Override
	public void serialize(DataOutput buffer, SerializationWrapper serialization) throws IOException {
		serialization.serialize(obj, buffer);
	}

	@Override
	public void deserialize(DataInput buffer, SerializationWrapper serialization) throws IOException {
		obj = serialization.deserialize(Object.class, buffer);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E> void apply(List<E> list, SerializationWrapper serialization) {
		list.add(index, (E) obj);
	}

}
