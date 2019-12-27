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
		changed = true;
		this.value = value;
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

}
