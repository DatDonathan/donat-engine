package at.jojokobi.donatengine.objects.properties;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class LongProperty implements ObservableProperty<Long>{
	
	private long value;
	private boolean changed = false;

	public LongProperty(long value) {
		super();
		this.value = value;
	}

	@Override
	public void set(Long t) {
		value = t;
		changed = true;
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
	public void writeChanges(DataOutput out) throws IOException {
		
	}

	@Override
	public void readChanges(DataInput in) throws IOException {
		
	}

	@Override
	public void writeValue(DataOutput buffer) throws IOException {
		buffer.writeLong(value);
	}

	@Override
	public void readValue(DataInput buffer) throws IOException {
		value = buffer.readLong();
	}

	@Override
	public List<ObservableProperty<?>> observableProperties() {
		return Arrays.asList();
	}
	
	

}
