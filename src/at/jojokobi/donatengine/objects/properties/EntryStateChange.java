package at.jojokobi.donatengine.objects.properties;

import java.io.ByteArrayInputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.IOException;
import java.util.List;

public class EntryStateChange implements ListChange{
	
	private int entry;
	private byte[] changes;
	
	public EntryStateChange(int entry, byte[] changes) {
		super();
		this.entry = entry;
		this.changes = changes;
	}
	
	public EntryStateChange() {
		
	}

	@Override
	public void serialize(DataOutput buffer) throws IOException {
		buffer.writeInt(entry);
		buffer.writeInt(changes.length);
		for (byte b : changes) {
			buffer.writeByte(b);
		}
	}

	@Override
	public void deserialize(DataInput buffer) throws IOException {
		entry = buffer.readInt();
		changes = new byte[buffer.readInt()];
		for (int i = 0; i < changes.length; i++) {
			changes[i] = buffer.readByte();
		}
	}

	@Override
	public <E> void apply(List<E> list) {
		ObservableObject obj = (ObservableObject) list.get(entry);
		try (DataInputStream in = new DataInputStream(new ByteArrayInputStream(changes))) {
			obj.readChanges(in);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
}
