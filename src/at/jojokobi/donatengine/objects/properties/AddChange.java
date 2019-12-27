package at.jojokobi.donatengine.objects.properties;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.List;

import at.jojokobi.donatengine.serialization.BinarySerialization;

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
	public void serialize(DataOutput buffer) throws IOException {
		BinarySerialization.getInstance().serialize(obj, buffer);
	}

	@Override
	public void deserialize(DataInput buffer) throws IOException {
		obj = BinarySerialization.getInstance().deserialize(Object.class, buffer);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E> void apply(List<E> list) {
		list.add(index, (E) obj);
	}

}
