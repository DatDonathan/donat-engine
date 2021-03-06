package at.jojokobi.donatengine.serialization.structured;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface ObjectLoader {
	
	public void save (OutputStream out, Object obj) throws IOException;
	
	public <T> PreloadedObject<T> load (InputStream in, Class<T> clazz) throws IOException;
	
	public <T> PreloadedObject<T> preload (T t, Class<T> clazz);

}
