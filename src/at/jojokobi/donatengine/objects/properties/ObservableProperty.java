package at.jojokobi.donatengine.objects.properties;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import at.jojokobi.donatengine.serialization.SerializationWrapper;

public interface ObservableProperty<T> {
	
	@FunctionalInterface
	public interface Listener<T> {
		
		public void onChange (ObservableProperty<? extends T> prop, T oldValue, T newValue);
		
	}
	
	public class ListenerManager<T> {
		
		private List<Listener<T>> listeners = new ArrayList<>();
		
		public void addListener (Listener<T> listener) {
			listeners.add(listener);
		}
		
		public void removeListener (Listener<T> listener) {
			listeners.remove(listener);
		}
		
		public void notifyListeners (ObservableProperty<? extends T> prop, T oldValue, T newValue) {
			listeners.forEach(l -> l.onChange(prop, oldValue, newValue));
		}
		
	}
	
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
	
	public void writeChanges (DataOutput out, SerializationWrapper serialization) throws IOException;
	
	public void readChanges (DataInput in, SerializationWrapper serialization) throws IOException;
	
	public void writeValue (DataOutput buffer, SerializationWrapper serialization) throws IOException;
	
	public void readValue (DataInput buffer, SerializationWrapper serialization) throws IOException;
	
	public List<ObservableProperty<?>> observableProperties ();
	
	public void addListener (Listener<T> listener);
	
	public void removeListener (Listener<T> listener);

}
