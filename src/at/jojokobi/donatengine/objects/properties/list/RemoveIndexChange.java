package at.jojokobi.donatengine.objects.properties.list;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.List;

import at.jojokobi.donatengine.objects.properties.ListChange;
import at.jojokobi.donatengine.serialization.SerializationWrapper;

public class RemoveIndexChange implements ListChange {
	
	private int index;
	
	public RemoveIndexChange(int index) {
		super();
		this.index = index;
	}

	public RemoveIndexChange() {
		
	}

	@Override
	public void serialize(DataOutput buffer, SerializationWrapper serialization) throws IOException {
		buffer.writeInt(index);
	}

	@Override
	public void deserialize(DataInput buffer, SerializationWrapper serialization) throws IOException {
		index = buffer.readInt();
	}

	@Override
	public <E> void apply(List<E> list, SerializationWrapper serialization) {
		list.remove(index);
	}
	
}
