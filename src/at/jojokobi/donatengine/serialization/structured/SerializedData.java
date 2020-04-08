package at.jojokobi.donatengine.serialization.structured;

/**
 * 
 * Objects are allowed to be serialized as soon as they are put() so get()-ing them may return another instance.
 * 
 * @author jojo0
 *
 */
public interface SerializedData {
	
	public Object get (String key);
	
	public <T> T get (String key, Class<T> clazz);
	
	public int getInt (String key);
	
	public String getString (String key);
	
	public double getDouble (String key);
	
	public boolean getBoolean (String key);
	
	public long getLong (String key);
	
	public void put (Object object);

}
