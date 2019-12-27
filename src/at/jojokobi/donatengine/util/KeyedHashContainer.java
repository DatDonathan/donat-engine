package at.jojokobi.donatengine.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public class KeyedHashContainer<K, V> implements KeyedContainer<K, V>{
	
	private Map<K, V> map = new HashMap<>();
	private List<V> values = new ArrayList<>();
	private Supplier<K> keySupplier;

	public KeyedHashContainer(Supplier<K> keySupplier) {
		super();
		this.keySupplier = keySupplier;
	}

	@Override
	public K add(V v) {
		K key = keySupplier.get();
		return add(v, key) ? key : null;
	}

	@Override
	public boolean add(V v, K k) {
		boolean added = false;
		if (!containsKey(k) && v != null && k != null) {
			map.put(k, v);
			values.add(v);
			added = true;
		}
		return added;
	}

	@Override
	public V remove(K k) {
		V v = map.get(k);
		values.remove (v);
		map.remove(k);
		return v;
	}

	@Override
	public K removeValue(V v) {
		K k = getKey(v);
		if (k != null) {
			map.remove(k);
			values.remove(v);
		}
		return k;
	}

	@Override
	public K getKey(V v) {
		K key = null;
		for (K k : map.keySet()) {
			if (v.equals(map.get(k))) {
				key = k;
			}
		}
		return key;
	}

	@Override
	public boolean containsKey(K k) {
		return map.containsKey(k);
	}

	@Override
	public boolean containsValue(V v) {
		return map.containsValue(v);
	}

	@Override
	public List<V> asList() {
		return new ArrayList<>(values);
	}

	@Override
	public Iterator<V> iterator() {
		Iterator<V> iter = asList().iterator();
		return new Iterator<V>() {

			@Override
			public boolean hasNext() {
				return iter.hasNext();
			}

			@Override
			public V next() {
				return iter.next();
			}
		};
	}

	@Override
	public V get(K k) {
		return map.get(k);
	}

	@Override
	public Set<K> keySet() {
		return new HashSet<>(map.keySet());
	}

	@Override
	public Map<K, V> asMap() {
		return new HashMap<>(map);
	}

}
