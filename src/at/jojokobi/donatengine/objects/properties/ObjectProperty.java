package at.jojokobi.donatengine.objects.properties;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import at.jojokobi.donatengine.serialization.BinarySerialization;

public class ObjectProperty<T> implements ObservableProperty<T>{
	
	private T value;
	private boolean changed = false;
	
	private ListenerManager<T> manager = new ListenerManager<>();


	public ObjectProperty(T value) {
		super();
		this.value = value;
	}

	@Override
	public T get() {
		return value;
	}
	
	public void writeValue (DataOutput buffer) throws IOException{
		BinarySerialization.getInstance().serialize(value, buffer);
	}
	
	public void readValue (DataInput buffer) throws IOException{
		setUnsafe(BinarySerialization.getInstance().deserialize(Object.class, buffer));
	}

	@Override
	public void set(T value) {
		T old = this.value;
		this.value = value;
		changed = true;
		manager.notifyListeners(this, old, value);
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
	public void writeChanges(DataOutput out) throws IOException {
		
	}

	@Override
	public void readChanges(DataInput in) throws IOException {
		
	}

	@Override
	public List<ObservableProperty<?>> observableProperties() {
		return Arrays.asList();
	}

	@Override
	public void addListener(Listener<T> listener) {
		manager.addListener(listener);
	}

	@Override
	public void removeListener(Listener<T> listener) {
		manager.removeListener(listener);
	}

}
