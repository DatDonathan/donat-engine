package at.jojokobi.donatengine.serialization;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public interface ClassFactory {
	
	/**
	 * 
	 * @param in
	 * @return The class of the serialized object or null if a null value was serialized
	 * @throws IOException
	 */
	public Class<?> readClass (DataInput in) throws IOException;
	
	/**
	 * 
	 * @param clazz
	 * @param out
	 * @throws IOException
	 */
	public void writeClass (Class<?> clazz, DataOutput out) throws IOException;

}
