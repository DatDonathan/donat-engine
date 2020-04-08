package at.jojokobi.donatengine.objects.properties;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import at.jojokobi.donatengine.serialization.binary.SerializationWrapper;

public class DoubleProperty implements ObservableProperty<Double>{
	
	private double value;
	private boolean changed = false;
	
	private ListenerManager<Double> manager = new ListenerManager<>();

	public DoubleProperty(double value) {
		super();
		this.value = value;
	}

	@Override
	public void set(Double t) {
		double old = value;
		value = t;
		if (old != value) {
			changed = true;
		}
		manager.notifyListeners(this, old, t);
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
	public void writeChanges(DataOutput out, SerializationWrapper serialization) throws IOException {
		
	}

	@Override
	public void readChanges(DataInput in, SerializationWrapper serialization) throws IOException {
		
	}

	@Override
	public void writeValue(DataOutput buffer, SerializationWrapper serialization) throws IOException {
		buffer.writeDouble(value);
	}

	@Override
	public void readValue(DataInput buffer, SerializationWrapper serialization) throws IOException {
		set(buffer.readDouble());
	}

	@Override
	public List<ObservableProperty<?>> observableProperties() {
		return Arrays.asList();
	}

	@Override
	public void addListener(Listener<Double> listener) {
		manager.addListener(listener);
	}

	@Override
	public void removeListener(Listener<Double> listener) {
		manager.removeListener(listener);
	}
	
}
