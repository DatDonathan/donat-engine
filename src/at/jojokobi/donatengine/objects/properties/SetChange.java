package at.jojokobi.donatengine.objects.properties;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.List;

import at.jojokobi.donatengine.serialization.BinarySerialization;

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
	public void serialize(DataOutput buffer) throws IOException {
		buffer.writeInt(index);
		BinarySerialization.getInstance().serialize(object, buffer);
	}

	@Override
	public void deserialize(DataInput buffer) throws IOException {
		index = buffer.readInt();
		object = BinarySerialization.getInstance().deserialize(Object.class, buffer);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E> void apply(List<E> list) {
		list.set(index, (E) object);
	}

}
