package at.jojokobi.donatengine.objects.properties.map;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import at.jojokobi.donatengine.objects.properties.ObservableObject;
import at.jojokobi.donatengine.objects.properties.ObservableProperty;
import at.jojokobi.donatengine.serialization.SerializationWrapper;
import at.jojokobi.donatengine.serialization.binary.BinarySerializable;

public class ObservableMap<K, V> implements ObservableObject, BinarySerializable, Map<K, V>{
	
	private Map<K, V> map;
	private List<MapChange> changes = new ArrayList<>();

	public ObservableMap(Map<K, V> map) {
		super();
		this.map = map;
	}
	
	public ObservableMap() {
		this(new HashMap<>());
	}

	@Override
	public int size() {
		return map.size();
	}

	@Override
	public boolean isEmpty() {
		return map.isEmpty();
	}

	@Override
	public boolean containsKey(Object key) {
		return map.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return map.containsValue(value);
	}

	@Override
	public V get(Object key) {
		return map.get(key);
	}

	@Override
	public V put(K key, V value) {
		V v = map.put(key, value);
		changes.add(new PutChange(key, value));
		return v;
	}

	@Override
	public V remove(Object key) {
		V v = map.remove(key);
		changes.add(new RemoveChange(key));
		return v;
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		map.putAll(m);
		for (var e : m.entrySet()) {
			changes.add(new PutChange(e.getKey(), e.getValue()));
		}
	}

	@Override
	public void clear() {
		map.clear();
		changes.add(new ClearChange());
	}

	@Override
	public Set<K> keySet() {
		return map.keySet();
	}

	@Override
	public Collection<V> values() {
		return map.values();
	}

	@Override
	public Set<Entry<K, V>> entrySet() {
		//TODO: Make entry changes save
		return map.entrySet();
	}

	@Override
	public List<ObservableProperty<?>> observerableProperties() {
		return Arrays.asList();
	}

	@Override
	public void writeChanges(DataOutput out, SerializationWrapper serialization) throws IOException {
		// TODO Observables in map
		out.writeInt(changes.size());
		for (MapChange mapChange : changes) {
			serialization.serialize(mapChange, out);
		}
		changes.clear();
	}

	@Override
	public void readChanges(DataInput in, SerializationWrapper serialization) throws IOException {
		// TODO Observables in map
		int size = in.readInt();
		for (int i = 0; i < size; i++) {
			MapChange change = serialization.deserialize(MapChange.class, in);
			change.apply(map, serialization);
		}
	}

	@Override
	public boolean stateChanged() {
		// TODO Observables in map
		return !changes.isEmpty();
	}

	@Override
	public void serialize(DataOutput buffer, SerializationWrapper serialization) throws IOException {
		buffer.writeInt(map.size());
		for (var e : map.entrySet()) {
			serialization.serialize(e.getKey(), buffer);
			serialization.serialize(e.getValue(), buffer);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void deserialize(DataInput buffer, SerializationWrapper serialization) throws IOException {
		int size = buffer.readInt();
		for (int i = 0; i < size; i++) {
			map.put((K) serialization.deserialize(Object.class, buffer), (V) serialization.deserialize(Object.class, buffer));
		}
	}

}
