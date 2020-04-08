package at.jojokobi.donatengine.serialization.structured;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class XMLObjectLoader implements ObjectLoader{

	@Override
	public void save(OutputStream out, Object obj) throws IOException {
		
	}

	@Override
	public <T> T load(InputStream in, Class<T> clazz) throws IOException {
		try (in) {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setNamespaceAware(true);
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(in);
			document.getDocumentElement().normalize();
			
			
		} catch (ParserConfigurationException | SAXException e) {
			e.printStackTrace();
			throw new IOException(e);
		}
		return null;
	}

}

class XMLSerialitionEntry implements SerializationEntry {
	
	private Class<?> clazz;
	private SerializedData data = new XMLSerializationData();
	
	
	@Override
	public Class<?> getSerializedClass() {
		return clazz;
	}
	@Override
	public void setSerializedClass(Class<?> clazz) {
		this.clazz = clazz;
	}
	@Override
	public SerializedData getData() {
		return data;
	}
	
	
	
}

class XMLSerializationData implements SerializedData {
	
	private Map<String, String> primitives = new HashMap<String, String>();
	private Map<String, SerializationEntry> objects = new HashMap<String, SerializationEntry>();

	@Override
	public Object getObject(String key) {
		return objects.get(key);
	}

	@Override
	public <T> T getObject(String key, Class<T> clazz) {
		if (objects.get(key) instanceof SerializationEntry) {
			return StructuredSerialization.getInstance().deserialize(clazz, (SerializationEntry) objects.get(key));
		}
		return null;
	}

	@Override
	public int getInt(String key) {
		return Integer.parseInt(primitives.get(key) + "");
	}

	@Override
	public String getString(String key) {
		return primitives.get(key) + "";
	}

	@Override
	public double getDouble(String key) {
		return Double.parseDouble(primitives.get(key) + "");
	}

	@Override
	public boolean getBoolean(String key) {
		return Boolean.parseBoolean(primitives.get(key));
	}

	@Override
	public long getLong(String key) {
		return Long.getLong(primitives.get(key));
	}

	@Override
	public void put(String key, Object object) {
		if (object == null) {
			objects.put(key, null);
		}
		else if (object.getClass().isEnum()) {
			primitives.put(key, ((Enum<?>) object).name());
		}
		else if (object.getClass() == String.class || object.getClass() == Byte.class || object.getClass() == Short.class || object.getClass() == Integer.class || object.getClass() == Boolean.class || object.getClass() == Float.class || object.getClass() == Character.class|| object.getClass() == Double.class || object.getClass() == Long.class) {
			primitives.put(key, object + "");
		}
		else {
			SerializationEntry entry = new XMLSerialitionEntry();
			StructuredSerialization.getInstance().serialize(object, entry);
			objects.put(key, entry);
		}
	}

	@Override
	public short getShort(String key) {
		return Short.parseShort(primitives.get(key) + "");
	}

	@Override
	public byte geByte(String key) {
		return Byte.parseByte(primitives.get(key) + "");
	}

	@Override
	public float getFloat(String key) {
		return Float.parseFloat(primitives.get(key) + "");
	}

	@Override
	public char getCharacter(String key) {
		return (primitives.get(key) + "").charAt(0);
	}

	@Override
	public <T extends Enum<T>> T getEnum(String key, Class<T> clazz) {
		return Enum.valueOf(clazz, primitives.get(key) + "");
	}
	
}
