package at.jojokobi.donatengine.serialization.structured;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLObjectLoader implements ObjectLoader{

	public static final String CLASS_ATTRIBUTE = "__class__";
	public static final String LIST_ATTRIBUTE = "__list__";
	public static final String NULL_ATTRIBUTE = "__null__";
	public static final String LIST_ENTRY_TAG = "entry";
	
	
	public void save(OutputStream out, SerializedValue value) throws IOException {
		try (out) {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setNamespaceAware(true);
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.newDocument();
			
			//Create node tree
			Element element = document.createElement(value.getRootName());
			value.toElement(document, element);
			document.appendChild(element);
			
			//Write
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			DOMSource source = new DOMSource(document);
			StreamResult result = new StreamResult(out);
			transformer.transform(source, result);
		} catch (ParserConfigurationException | TransformerException e) {
			e.printStackTrace();
			throw new IOException(e);
		}
	}
	
	@Override
	public void save(OutputStream out, Object obj) throws IOException {
			//Serialize
			XMLSerializationEntry entry = new XMLSerializationEntry();
			StructuredSerialization.getInstance().serialize(obj, entry);
			
			save(out, entry);
	}

	@Override
	public <T> PreloadedObject<T> load(InputStream in, Class<T> clazz) throws IOException {
		try (in) {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setNamespaceAware(true);
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(in);
			document.getDocumentElement().normalize();
			
			//Build tree
			SerializedValue entry = load(document.getDocumentElement());
			return preload(clazz, entry);
		} catch (ParserConfigurationException | SAXException | ClassNotFoundException e) {
			e.printStackTrace();
			throw new IOException(e);
		}
	}
	
	private SerializedValue load (Element element) throws ClassNotFoundException {
		SerializedValue value = null;
		NodeList list = element.getChildNodes();
		if (element.getAttribute(NULL_ATTRIBUTE) != null) {
			value = new SerializeNullValue();
		}
		else if (element.getAttribute(LIST_ATTRIBUTE) != null) {
			List<SerializedValue> values = new ArrayList<SerializedValue>();
			for (int i = 0; i < list.getLength(); i++) {
				Node node = list.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					values.add(load(element));
				}
			}
			value = new SerializedList(values);
		}
		else if (element.getAttribute(CLASS_ATTRIBUTE) != null) {
			XMLSerializationEntry entry = new XMLSerializationEntry();
			entry.setSerializedClass(Class.forName(element.getAttribute(CLASS_ATTRIBUTE)));
			for (int i = 0; i < list.getLength(); i++) {
				Node node = list.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					entry.getData().putValue(node.getNodeName(), load(element));
				}
			}
			value = new SerializedObject(entry);
		}
		else {
			value = new SerializedString(element.getTextContent());
		}
		return value;
	}
	
	static SerializedValue serializeValue (Object object) {
		SerializedValue value = null;
		if (object == null) {
			value = new SerializeNullValue();
		}
		else if (object.getClass().isEnum()) {
			value = new SerializedString(((Enum<?>) object).name());
		}
		else if (object.getClass() == String.class || object.getClass() == Byte.class || object.getClass() == Short.class || object.getClass() == Integer.class || object.getClass() == Boolean.class || object.getClass() == Float.class || object.getClass() == Character.class|| object.getClass() == Double.class || object.getClass() == Long.class) {
			value = new SerializedString(object + "");
		}
		else if (object instanceof List<?>) {
			List<SerializedValue> values = new ArrayList<SerializedValue>();
			for (Object obj : ((List<?>) object)) {
				values.add(serializeValue(obj));
			}
			value = new SerializedList(values);
		}
		else {
			XMLSerializationEntry entry = new XMLSerializationEntry();
			StructuredSerialization.getInstance().serialize(object, entry);
			value =  new SerializedObject(entry);
		}
		return value;
	}

	@Override
	public <T> PreloadedObject<T> preload(T t, Class<T> clazz) {
		return preload(clazz, serializeValue(t));		
	}
	
	private <T> PreloadedObject<T> preload (Class<T> clazz, SerializedValue entry) {
		return new PreloadedObject<T>() {

			@Override
			public T create() {
				return entry.get(clazz);
			}

			@Override
			public void save(OutputStream out) throws IOException {
				XMLObjectLoader.this.save(out, entry);
			}
		};
	}

}

class XMLSerializationEntry implements SerializationEntry {
	
	private Class<?> clazz;
	private XMLSerializationData data = new XMLSerializationData();
	
	
	@Override
	public Class<?> getSerializedClass() {
		return clazz;
	}
	@Override
	public void setSerializedClass(Class<?> clazz) {
		this.clazz = clazz;
	}
	@Override
	public XMLSerializationData getData() {
		return data;
	}
	
}

class XMLSerializationData implements SerializedData {
	
	private Map<String, SerializedValue> objects = new HashMap<>();

	@Override
	public Object getObject(String key) {
		return StructuredSerialization.getInstance().deserialize(Object.class, (SerializationEntry) objects.get(key));
	}

	@Override
	public <T> T getObject(String key, Class<T> clazz) {
		return StructuredSerialization.getInstance().deserialize(clazz, (SerializationEntry) objects.get(key));
	}

	@Override
	public int getInt(String key) {
		return Integer.parseInt(objects.get(key).getString());
	}

	@Override
	public String getString(String key) {
		return objects.get(key).getString();
	}

	@Override
	public double getDouble(String key) {
		return Double.parseDouble(objects.get(key).getString());
	}

	@Override
	public boolean getBoolean(String key) {
		return Boolean.parseBoolean(objects.get(key).getString());
	}

	@Override
	public long getLong(String key) {
		return Long.getLong(objects.get(key).getString());
	}

	@Override
	public void put(String key, Object object) {
		objects.put(key, XMLObjectLoader.serializeValue(object));
	}
	
	void putValue (String key, SerializedValue entry) {
		objects.put(key, entry);
	}

	@Override
	public short getShort(String key) {
		return Short.parseShort(objects.get(key).getString());
	}

	@Override
	public byte getByte(String key) {
		return Byte.parseByte(objects.get(key).getString());
	}

	@Override
	public float getFloat(String key) {
		return Float.parseFloat(objects.get(key).getString());
	}

	@Override
	public char getCharacter(String key) {
		return objects.get(key).getString().charAt(0);
	}

	@Override
	public <T extends Enum<T>> T getEnum(String key, Class<T> clazz) {
		return Enum.valueOf(clazz, objects.get(key).getString());
	}
	
	@Override
	public <T> List<T> getList(String key, Class<T> clazz) {
		return objects.get(key).getList(clazz);
	}

	Map<String, SerializedValue> getObjects() {
		return objects;
	}
	
}

interface SerializedValue {
	
	public <T> T get(Class<T> clazz);
	
	public void toElement (Document document, Element element);
	
	public String getRootName ();
	
	public default Object get() {
		return get(Object.class);
	}
	
	public default String getString() {
		return get() + "";
	}
	
	public default <T> List<T> getList (Class<T> clazz) {
		throw new UnsupportedOperationException();
	}
	
}

class SerializeNullValue implements SerializedValue {

	@Override
	public <T> T get(Class<T> clazz) {
		return null;
	}

	@Override
	public void toElement(Document document, Element element) {
		element.setAttribute(XMLObjectLoader.NULL_ATTRIBUTE, "");
	}

	@Override
	public String getRootName() {
		return "null";
	}
	
}

class SerializedString implements SerializedValue {
	
	private String string;

	public SerializedString(String string) {
		super();
		this.string = string;
	}

	@Override
	public <T> T get(Class<T> clazz) {
		return clazz.cast(string);
	}

	@Override
	public void toElement(Document document, Element element) {
		element.setTextContent(string);
	}

	@Override
	public String getRootName() {
		return "primitive";
	}
	
}

class SerializedObject implements SerializedValue {
	
	private XMLSerializationEntry entry;

	public SerializedObject(XMLSerializationEntry entry) {
		super();
		this.entry = entry;
	}

	@Override
	public <T> T get(Class<T> clazz) {
		return StructuredSerialization.getInstance().deserialize(clazz, entry);
	}

	@Override
	public void toElement(Document document, Element element) {
		for (var e : entry.getData().getObjects().entrySet()) {
			Element elem = document.createElement(e.getKey());
			e.getValue().toElement(document, elem);
			element.appendChild(elem);
		}
	}

	@Override
	public String getRootName() {
		return entry.getSerializedClass().getSimpleName();
	}

}

class SerializedList implements SerializedValue {
	
	private List<SerializedValue> list = new ArrayList<>();

	public SerializedList(List<SerializedValue> list) {
		super();
		this.list = list;
	}

	@Override
	public <T> T get(Class<T> clazz) {
		List<Object> list = getList(Object.class);
		return clazz.cast(list);
	}
	
	@Override
	public <T> List<T> getList (Class<T> clazz) {
		List<T> list = new ArrayList<T>();
		for (SerializedValue value : this.list) {
			list.add(value.get(clazz));
		}
		return list;
	}

	@Override
	public void toElement(Document document, Element element) {
		for (SerializedValue value : list) {
			Element elem = document.createElement(XMLObjectLoader.LIST_ENTRY_TAG);
			value.toElement(document, elem);
			element.appendChild(elem);
		}
	}

	@Override
	public String getRootName() {
		return "List";
	}
	
}

