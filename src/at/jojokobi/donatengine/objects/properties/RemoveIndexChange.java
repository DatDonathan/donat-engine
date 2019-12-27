package at.jojokobi.donatengine.objects.properties;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.List;

public class RemoveIndexChange implements ListChange {
	
	private int index;
	
	public RemoveIndexChange(int index) {
		super();
		this.index = index;
	}

	public RemoveIndexChange() {
		
	}

	@Override
	public void serialize(DataOutput buffer) throws IOException {
		buffer.writeInt(index);
	}

	@Override
	public void deserialize(DataInput buffer) throws IOException {
		index = buffer.readInt();
	}

	@Override
	public <E> void apply(List<E> list) {
		list.remove(index);
	}
	
}
