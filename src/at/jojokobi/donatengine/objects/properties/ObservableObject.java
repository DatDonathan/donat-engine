package at.jojokobi.donatengine.objects.properties;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.List;

public interface ObservableObject {
	
	public List<ObservableProperty<?>> observerableProperties ();
	
	public void writeChanges (DataOutput out) throws IOException;
	
	public void readChanges (DataInput in) throws IOException;
	
	public boolean stateChanged ();

}
