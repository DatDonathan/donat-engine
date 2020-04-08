package at.jojokobi.donatengine.objects.properties;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import at.jojokobi.donatengine.serialization.binary.SerializationWrapper;

public class ObservableObjectProperty<T extends ObservableObject> extends ObjectProperty<T> {

	public ObservableObjectProperty(T value) {
		super(value);
	}
	
	@Override
	public List<ObservableProperty<?>> observableProperties() {
		return get() == null ? Arrays.asList() : get().observerableProperties();
	}
	
	@Override
	public boolean stateChanged() {
		return get() == null ? false : get().stateChanged();
	}
	
	@Override
	public void readChanges(DataInput in, SerializationWrapper serialization) throws IOException {
		super.readChanges(in, serialization);
		if (get() != null) {
			get().readChanges(in, serialization);
		}
	}
	
	@Override
	public void writeChanges(DataOutput out, SerializationWrapper serialization) throws IOException {
		super.writeChanges(out, serialization);
		if (get() != null) {
			get().writeChanges(out, serialization);
		}
	}

}
