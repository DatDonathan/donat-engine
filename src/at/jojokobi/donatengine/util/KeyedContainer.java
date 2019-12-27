package at.jojokobi.donatengine.util;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface KeyedContainer<K, V> extends Iterable<V>{
	
	/**
	 * 
	 * Adds an element to the container. Null and duplicate entries are not allowed
	 * 
	 * @param v		The element to be added
	 * @return		The key of the added element or null if it couldn't be added
	 */
	public K add (V v);
	
	/**
	 * 
	 * Adds an element to the container. Null and duplicate entries are not allowed.
	 * 
	 * @param v		The element to be added
	 * @param k		The key of the entry
	 */
	public boolean add (V v, K k);
	
	/**
	 * 
	 * Gets the element assigned to the key
	 * 
	 * @param k		The key of the element
	 * @return		The assigned element or null if no element is present
	 */
	public V get (K k);
	
	/**
	 * 
	 * Removes a key
	 * 
	 * @param k		The key to be removed
	 * @return		The value assigned to the key or null if no value was assigned
	 */
	public V remove (K k);
	
	/**
	 * 
	 * Removes a value
	 * 
	 * @param v		The value to be removed
	 * @return		The key assigned to the value or null if no key was assigned
	 */
	public K removeValue (V v);
	
	/**
	 * 
	 * Gets the key for the value
	 * 
	 * @param v		The value
	 * @return		The key of the value or null if the value is not present in this container
	 */
	public K getKey (V v);
	
	/**
	 * 
	 * Checks whether a key has a value assigned or not
	 * 
	 * @param k		The key to be checked
	 * @return		True if the key has a value assigned, false otherwise
	 */
	public boolean containsKey (K k);
	
	/**
	 * 
	 * Checks whether the container contains a specific value
	 * 
	 * @param v		The value to be checked
	 * @return		True if the value is present, false otherwise
	 */
	public boolean containsValue (V v);
	
	/**
	 * 
	 * @return		A List that contains the values of this container
	 */
	public List<V> asList ();
	
	/**
	 * 
	 * @return		A Map that contains the key-value-pairs of this container
	 */
	public Map<K,V> asMap ();
	
	/**
	 * 
	 * @return A Set htat contain all the keys of this container
	 */
	public Set<K> keySet();

}
