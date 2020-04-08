package at.jojokobi.donatengine.objects.properties;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import at.jojokobi.donatengine.serialization.binary.SerializationWrapper;

public class StringProperty implements ObservableProperty<String>{
	
	private String value;
	private boolean changed = false;
	
	private ListenerManager<String> manager = new ListenerManager<>();

	public StringProperty(String value) {
		super();
		this.value = value;
	}

	@Override
	public void set(String t) {
		String old = value;
		value = t;
		if (!old.equals(value)) {
			changed = true;
		}
		manager.notifyListeners(this, old, value);
	}

	@Override
	public String get() {
		return value;
	}

	@Override
	public boolean fetchChanged() {
		boolean temp = changed;
		changed = false;
		return temp;
	}

	@Override
	public boolean stateChanged() {
		return false;
	}

	@Override
	public void writeChanges(DataOutput out, SerializationWrapper serialization) throws IOException {
		
	}

	@Override
	public void readChanges(DataInput in, SerializationWrapper serialization) throws IOException {
		
	}

	@Override
	public void writeValue(DataOutput buffer, SerializationWrapper serialization) throws IOException {
		buffer.writeUTF(value);
	}

	@Override
	public void readValue(DataInput buffer, SerializationWrapper serialization) throws IOException {
		set(buffer.readUTF());
	}

	@Override
	public List<ObservableProperty<?>> observableProperties() {
		return Arrays.asList();
	}
	
	@Override
	public void addListener(Listener<String> listener) {
		manager.addListener(listener);
	}

	@Override
	public void removeListener(Listener<String> listener) {
		manager.removeListener(listener);
	}
	
}
