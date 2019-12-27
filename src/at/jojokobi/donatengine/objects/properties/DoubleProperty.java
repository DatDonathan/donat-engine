package at.jojokobi.donatengine.objects.properties;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class DoubleProperty implements ObservableProperty<Double>{
	
	private double value;
	private boolean changed = false;

	public DoubleProperty(long value) {
		super();
		this.value = value;
	}

	@Override
	public void set(Double t) {
		value = t;
		changed = true;
	}

	@Override
	public Double get() {
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
		buffer.writeDouble(value);
	}

	@Override
	public void readValue(DataInput buffer) throws IOException {
		value = buffer.readDouble();
	}

	@Override
	public List<ObservableProperty<?>> observableProperties() {
		return Arrays.asList();
	}
	
	

}
