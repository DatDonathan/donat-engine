package at.jojokobi.donatengine.objects.properties;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import at.jojokobi.donatengine.serialization.SerializationWrapper;


public class LongProperty implements ObservableProperty<Long>{
	
	private long value;
	private boolean changed = false;
	
	private ListenerManager<Long> manager = new ListenerManager<>();

	public LongProperty(long value) {
		super();
		this.value = value;
	}

	@Override
	public void set(Long t) {
		long old = value;
		value = t;
		changed = true;
		manager.notifyListeners(this, old, value);
	}

	@Override
	public Long get() {
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
		buffer.writeLong(value);
	}

	@Override
	public void readValue(DataInput buffer, SerializationWrapper serialization) throws IOException {
		set(buffer.readLong());
	}

	@Override
	public List<ObservableProperty<?>> observableProperties() {
		return Arrays.asList();
	}
	
	@Override
	public void addListener(Listener<Long> listener) {
		manager.addListener(listener);
	}

	@Override
	public void removeListener(Listener<Long> listener) {
		manager.removeListener(listener);
	}

}
