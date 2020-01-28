package at.jojokobi.donatengine.objects.properties.list;

import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import at.jojokobi.donatengine.objects.properties.ObservableObject;
import at.jojokobi.donatengine.objects.properties.ObservableProperty;
import at.jojokobi.donatengine.serialization.BinarySerializable;
import at.jojokobi.donatengine.serialization.SerializationWrapper;

public class ObservableList<E> implements ObservableObject, List<E>, BinarySerializable {
	
	private List<E> list;
	private List<ListChange> changes = new ArrayList<>();
	
	public ObservableList(List<E> list) {
		super();
		this.list = list;
	}

	public ObservableList() {
		this(new ArrayList<>());
	}

	@Override
	public int size() {
		return list.size();
	}

	@Override
	public boolean isEmpty() {
		return list.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return list.contains(o);
	}

	@Override
	public Iterator<E> iterator() {
		return list.iterator();
	}

	@Override
	public Object[] toArray() {
		return list.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return list.toArray(a);
	}

	@Override
	public boolean add(E e) {
		changes.add(new AddChange(list.size(), e));
		return list.add(e);
	}

	@Override
	public boolean remove(Object o) {
		changes.add(new RemoveChange(o));
		return list.remove(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return list.containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		int index = list.size();
		for (E e : c) {
			changes.add(new AddChange(index, e));
			index++;
		}
		return list.addAll(c);
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		for (E e : c) {
			changes.add(new AddChange(index, e));
			index++;
		}
		return list.addAll(index - c.size(), c);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		for (Object e : c) {
			changes.add(new RemoveChange(e));
		}
		return removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		for (E e : list) {
			if (!c.contains(e)) {
				changes.add(new RemoveChange(e));
			}
		}
		return retainAll(c);
	}

	@Override
	public void clear() {
		changes.add(new ClearChange());
		list.clear();
	}

	@Override
	public E get(int index) {
		return list.get(index);
	}

	@Override
	public E set(int index, E element) {
		changes.add(new SetChange(index, element));
		return list.set(index, element);
	}

	@Override
	public void add(int index, E element) {
		changes.add(new AddChange(index, element));
		list.add(index, element);
	}

	@Override
	public E remove(int index) {
		changes.add(new RemoveIndexChange(index));
		return list.remove(index);
	}

	@Override
	public int indexOf(Object o) {
		return list.indexOf(o);
	}

	@Override
	public int lastIndexOf(Object o) {
		return list.lastIndexOf(o);
	}

	@Override
	public ListIterator<E> listIterator() {
		//TODO Make iterator change safe
		return list.listIterator();
	}

	@Override
	public ListIterator<E> listIterator(int index) {
		//TODO Make iterator change safe
		return list.listIterator(index);
	}

	@Override
	public List<E> subList(int fromIndex, int toIndex) {
		// TODO Make sub list change safe
		return list.subList(fromIndex, toIndex);
	}

	@Override
	public List<ObservableProperty<?>> observerableProperties() {
		return Arrays.asList();
	}

	@Override
	public void writeChanges(DataOutput out, SerializationWrapper serialization) throws IOException {
		//Get changes
		int i = 0;
		for (E e : list) {
			if (e instanceof ObservableObject) {
				ObservableObject o = (ObservableObject) e;
				int index = 0;
				for (ObservableProperty<?> prop : o.observerableProperties()) {
					//State changed
					if (prop.stateChanged()) {
						try (ByteArrayOutputStream arr = new ByteArrayOutputStream();
								DataOutputStream stream = new DataOutputStream(arr)){
							prop.writeChanges(stream, serialization);
							changes.add(new EntryPropertyStateChange(i, index, arr.toByteArray()));
						} catch (IOException e1) {
							e1.printStackTrace();
							throw new RuntimeException(e1);
						}
					}
					index++;
				}
				if (o.stateChanged()) {
					try (ByteArrayOutputStream arr = new ByteArrayOutputStream();
							DataOutputStream stream = new DataOutputStream(arr)){
						o.writeChanges(stream, serialization);
						changes.add(new EntryStateChange(i, arr.toByteArray()));
					} catch (IOException e1) {
						e1.printStackTrace();
						throw new RuntimeException(e1);
					}
				}
			}
			i++;
		}
		out.writeInt(changes.size());
		for (ListChange change : changes) {
			serialization.serialize(change, out);
		}
		changes.clear();
	}

	@Override
	public boolean stateChanged() {
		int i = 0;
		boolean stateChanged = false;
		for (E e : list) {
			if (e instanceof ObservableObject) {
				ObservableObject o = (ObservableObject) e;
				int index = 0;
				for (ObservableProperty<?> prop : o.observerableProperties()) {
					//Value changed
					if (prop.fetchChanged()) {
						changes.add(new EntryPropertyChange(i, index, prop.get()));
					}
					//State changed
					if (prop.stateChanged()) {
						stateChanged = true;
					}
					index++;
				}
				if (o.stateChanged()) {
					stateChanged = true;
				}
			}
			i++;
		}
		return stateChanged || !changes.isEmpty();
	}

	@Override
	public void readChanges(DataInput in, SerializationWrapper serialization) throws IOException {
		int length = in.readInt();
		for (int i = 0; i < length; i++) {
			ListChange change = serialization.deserialize(ListChange.class, in);
			change.apply(list, serialization);
		}
	}

	@Override
	public void serialize(DataOutput buffer, SerializationWrapper serialization) throws IOException {
		buffer.writeInt (list.size());
		for (E e : list) {
			serialization.serialize(e, buffer);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void deserialize(DataInput buffer, SerializationWrapper serialization) throws IOException {
		int size = buffer.readInt();
		list.clear();
		for (int i = 0; i < size; i++) {
			list.add((E) serialization.deserialize(Object.class, buffer));
		}
	}
}

//class ListChange implements BinarySerializable {
//	
//	private int id;
//	private Object value;
//	
//	public ListChange(int id, Object value) {
//		super();
//		this.id = id;
//		this.value = value;
//	}
//	public int getId() {
//		return id;
//	}
//	public Object getValue() {
//		return value;
//	}
//
//	@Override
//	public void serialize(DataOutput buffer) throws IOException {
//		buffer.writeInt(id);
//		BinarySerialization.getInstance().serialize(value, buffer);
//	}
//	@Override
//	public void deserialize(DataInput buffer) throws IOException {
//		id = buffer.readInt();
//		value = BinarySerialization.getInstance().deserialize(Object.class, buffer);
//	}
//	
//}
