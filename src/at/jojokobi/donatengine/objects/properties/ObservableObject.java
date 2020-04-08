package at.jojokobi.donatengine.objects.properties;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.List;

import at.jojokobi.donatengine.serialization.binary.SerializationWrapper;

public interface ObservableObject {
	
	public List<ObservableProperty<?>> observerableProperties ();
	
	public void writeChanges (DataOutput out, SerializationWrapper serialization) throws IOException;
	
	public void readChanges (DataInput in, SerializationWrapper serialization) throws IOException;
	
	public boolean stateChanged ();

}
