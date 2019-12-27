package at.jojokobi.donatengine.objects.properties;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class IntProperty implements ObservableProperty<Integer>{
	
	private int value;
	private boolean changed = false;

	public IntProperty(int value) {
		super();
		this.value = value;
	}

	@Override
	public void set(Integer t) {
		value = t;
		changed = true;
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
	public void writeChanges(DataOutput out) throws IOException {
		
	}

	@Override
	public void readChanges(DataInput in) throws IOException {
		
	}

	@Override
	public void writeValue(DataOutput buffer) throws IOException {
		buffer.writeInt(value);
	}

	@Override
	public void readValue(DataInput buffer) throws IOException {
		value = buffer.readInt();
	}

	@Override
	public List<ObservableProperty<?>> observableProperties() {
		return Arrays.asList();
	}

	public void increment(int i) {
		set(get() + i);
	}

}
