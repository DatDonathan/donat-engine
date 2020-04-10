package at.jojokobi.donatengine.serialization.structured;

import java.io.IOException;
import java.io.OutputStream;

/**
 * 
 * Contains the loaded data bound to an ObjectLoader and constructs an object of type T out of it when needed (may still throw exceptions)
 * 
 * @author jojo0
 *
 * @param <T>
 */
public interface PreloadedObject<T> {

	public T create ();
	
	public void save (OutputStream out) throws IOException;
	
}
