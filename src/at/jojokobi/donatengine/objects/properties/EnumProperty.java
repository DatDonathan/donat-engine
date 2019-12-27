package at.jojokobi.donatengine.objects.properties;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class EnumProperty<T extends Enum<T>> implements ObservableProperty<T> {

	private T value;
	private boolean changed = false;
	private Class<T> clazz;

	public EnumProperty(T value, Class<T> clazz) {
		super();
		this.value = value;
		this.clazz = clazz;
	}

	@Override
	public void set(T t) {
		value = t;
		changed = true;
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
	public void writeChanges(DataOutput out) throws IOException {

	}

	@Override
	public void readChanges(DataInput in) throws IOException {

	}

	@Override
	public void writeValue(DataOutput buffer) throws IOException {
		buffer.writeInt(value.ordinal());
	}

	@Override
	public void readValue(DataInput buffer) throws IOException {
		value = clazz.getEnumConstants()[buffer.readInt()];
	}

	@Override
	public List<ObservableProperty<?>> observableProperties() {
		return Arrays.asList();
	}

}
