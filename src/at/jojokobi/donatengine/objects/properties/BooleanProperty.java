package at.jojokobi.donatengine.objects.properties;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import at.jojokobi.donatengine.serialization.SerializationWrapper;

public class BooleanProperty implements ObservableProperty<Boolean>{
	
	private boolean value;
	private boolean changed = false;
	
	private ListenerManager<Boolean> manager = new ListenerManager<>();

	public BooleanProperty(boolean value) {
		super();
		this.value = value;
	}

	@Override
	public void set(Boolean t) {
		Boolean old = value;
		value = t;
		if (old != value) {
			changed = true;
		}
		manager.notifyListeners(this, old, t);
	}

	@Override
	public Boolean get() {
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
		buffer.writeBoolean(value);
	}

	@Override
	public void readValue(DataInput buffer, SerializationWrapper serialization) throws IOException {
		set(buffer.readBoolean());
	}

	@Override
	public List<ObservableProperty<?>> observableProperties() {
		return Arrays.asList();
	}

	@Override
	public void addListener(Listener<Boolean> listener) {
		manager.addListener(listener);
	}

	@Override
	public void removeListener(Listener<Boolean> listener) {
		manager.removeListener(listener);
	}
	
}
