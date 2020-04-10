package at.jojokobi.donatengine.serialization.structured;

import java.util.List;

/**
 * 
 * Objects are allowed to be serialized as soon as they are put() so get()-ing them may return another instance.
 * 
 * @author jojo0
 *
 */
public interface SerializedData {
	
	public Object getObject (String key);
	
	public <T> T getObject (String key, Class<T> clazz);
	
	public String getString (String key);
	
	public short getShort (String key);
	
	public byte geByte (String key);
	
	public int getInt (String key);
	
	public double getDouble (String key);
	
	public float getFloat (String key);
	
	public boolean getBoolean (String key);
	
	public long getLong (String key);
	
	public char getCharacter (String key);
	
	public <T> List<T> getList (String key, Class<T> clazz);
	
	public <T extends Enum<T>> T getEnum (String key, Class<T> clazz);
	
	public void put (String string, Object object);

}
