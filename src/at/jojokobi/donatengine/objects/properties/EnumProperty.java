package at.jojokobi.donatengine.objects.properties;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import at.jojokobi.donatengine.serialization.SerializationWrapper;

public class EnumProperty<T extends Enum<T>> implements ObservableProperty<T> {

	private T value;
	private boolean changed = false;
	private Class<T> clazz;
	
	private ListenerManager<T> manager = new ListenerManager<>();

	public EnumProperty(T value, Class<T> clazz) {
		super();
		this.value = value;
		this.clazz = clazz;
	}

	@Override
	public void set(T t) {
		T old = value;
		value = t;
		changed = true;
		manager.notifyListeners(this, old, t);
	}

	@Override
	public T get() {
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
		buffer.writeInt(value.ordinal());
	}

	@Override
	public void readValue(DataInput buffer, SerializationWrapper serialization) throws IOException {
		set(clazz.getEnumConstants()[buffer.readInt()]);
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
