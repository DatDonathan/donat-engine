package at.jojokobi.donatengine.objects.properties;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.List;

import at.jojokobi.donatengine.serialization.SerializationWrapper;

public class EntryPropertyChange implements ListChange{
	
	private int entry;
	private int property;
	private Object changes;
	
	public EntryPropertyChange(int entry, int property, Object changes) {
		super();
		this.entry = entry;
		this.property = property;
		this.changes = changes;
	}
	
	public EntryPropertyChange() {
		
	}

	@Override
	public void serialize(DataOutput buffer, SerializationWrapper serialization) throws IOException {
		buffer.writeInt(entry);
		buffer.writeInt(property);
		serialization.serialize(changes, buffer);
	}

	@Override
	public void deserialize(DataInput buffer, SerializationWrapper serialization) throws IOException {
		entry = buffer.readInt();
		property = buffer.readInt();
		changes = serialization.deserialize(Object.class, buffer);
	}

	@Override
	public <E> void apply(List<E> list, SerializationWrapper serialization) {
		ObservableObject obj = (ObservableObject) list.get(entry);
		obj.observerableProperties().get(property).setUnsafe(changes);
	}
	
}
