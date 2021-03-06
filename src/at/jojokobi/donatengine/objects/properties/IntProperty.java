package at.jojokobi.donatengine.objects.properties;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import at.jojokobi.donatengine.serialization.binary.SerializationWrapper;


public class IntProperty implements ObservableProperty<Integer>{
	
	private int value;
	private boolean changed = false;
	
	private ListenerManager<Integer> manager = new ListenerManager<>();
	

	public IntProperty(int value) {
		super();
		this.value = value;
	}

	@Override
	public void set(Integer t) {
		int old = value;
		value = t;
		if (old != value) {
			changed = true;
		}
		manager.notifyListeners(this, old, t);
	}

	@Override
	public Integer get() {
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
		buffer.writeInt(value);
	}

	@Override
	public void readValue(DataInput buffer, SerializationWrapper serialization) throws IOException {
		set(buffer.readInt());
	}

	@Override
	public List<ObservableProperty<?>> observableProperties() {
		return Arrays.asList();
	}

	public void increment(int i) {
		set(get() + i);
	}
	

	@Override
	public void addListener(Listener<Integer> listener) {
		manager.addListener(listener);
	}

	@Override
	public void removeListener(Listener<Integer> listener) {
		manager.removeListener(listener);
	}

}
