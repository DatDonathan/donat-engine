package at.jojokobi.donatengine.objects.properties;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

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
	public void readChanges(DataInput in) throws IOException {
		super.readChanges(in);
		if (get() != null) {
			get().readChanges(in);
		}
	}
	
	@Override
	public void writeChanges(DataOutput out) throws IOException {
		super.writeChanges(out);
		if (get() != null) {
			get().writeChanges(out);
		}
	}

}
