package at.jojokobi.donatengine.objects.properties;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.List;

public interface ObservableProperty<T> {
	
	public void set (T t);
	
	public default void setUnchanged (T t) {
		boolean changed = fetchChanged();
		set(t);
		if (!changed) {
			fetchChanged();
		}
	}
	
	public T get ();
	
	@SuppressWarnings("unchecked")
	public default void setUnsafe (Object obj) {
		set((T) obj);
	}
	
	public boolean fetchChanged ();
	
	public boolean stateChanged ();
	
	public void writeChanges (DataOutput out) throws IOException;
	
	public void readChanges (DataInput in) throws IOException;
	
	public void writeValue (DataOutput buffer) throws IOException;
	
	public void readValue (DataInput buffer) throws IOException;
	
	public List<ObservableProperty<?>> observableProperties ();

}
