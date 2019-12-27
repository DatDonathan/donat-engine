package at.jojokobi.donatengine.objects.properties;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.List;

public class ClearChange implements ListChange{

	@Override
	public void serialize(DataOutput buffer) throws IOException {
		
	}

	@Override
	public void deserialize(DataInput buffer) throws IOException {
		
	}

	@Override
	public <E> void apply(List<E> list) {
		list.clear();
	}

}
