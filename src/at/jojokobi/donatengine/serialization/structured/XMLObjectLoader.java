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

	private static final String CLASS_ATTRIBUTE = "__class__";
	
	@Override
	public void save(OutputStream out, Object obj) throws IOException {
		try (out) {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setNamespaceAware(true);
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.newDocument();
			
			//Serialize
			XMLSerializationEntry entry = new XMLSerializationEntry();
			StructuredSerialization.getInstance().serialize(obj, entry);
			
			//Create node tree
			Element element = document.createElement(obj == null ? "null" : obj.getClass().getSimpleName().toLowerCase());
			toElement(element, document, entry);
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
		}
	}
	
	public void toElement (Element element, Document document, XMLSerializationEntry entry) {
		for (var e : entry.getData().getPrimitives().entrySet()) {
			Element elem = document.createElement(e.getKey());
			elem.setTextContent(e.getValue());
			element.appendChild(elem);
		}
		for (var e : entry.getData().getObjects().entrySet()) {
			Element elem = document.createElement(e.getKey());
			toElement(elem, document, e.getValue());
			element.appendChild(elem);
		}
	}

	@Override
	public <T> T load(InputStream in, Class<T> clazz) throws IOException {
		try (in) {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setNamespaceAware(true);
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(in);
			document.getDocumentElement().normalize();
			
			//Build tree
			XMLSerializationEntry entry = load(document.getDocumentElement());
			return StructuredSerialization.getInstance().deserialize(clazz, entry);
		} catch (ParserConfigurationException | SAXException | ClassNotFoundException e) {
			e.printStackTrace();
			throw new IOException(e);
		}
	}
	
	private XMLSerializationEntry load (Element element) throws ClassNotFoundException {
		XMLSerializationEntry entry = new XMLSerializationEntry();
		entry.setSerializedClass(Class.forName(element.getAttribute(CLASS_ATTRIBUTE)));
		NodeList list = element.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			Node node = list.item(0);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				if (((Element) node).getChildNodes().getLength() > 0) {
					entry.getData().putEntry(node.getNodeName(), load((Element) node));
				} else {
					entry.getData().put(node.getNodeName(), node.getNodeValue());
				}
			}
		}
		return entry;
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
	
	private Map<String, String> primitives = new HashMap<String, String>();
	private Map<String, XMLSerializationEntry> objects = new HashMap<String, XMLSerializationEntry>();
	private Map<String, List<?>> lists = new HashMap<>();

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
			XMLSerializationEntry entry = new XMLSerializationEntry();
			StructuredSerialization.getInstance().serialize(object, entry);
			objects.put(key, entry);
		}
	}
	
	void putEntry (String key, XMLSerializationEntry entry) {
		objects.put(key, entry);
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
	
	@Override
	public <T> List<T> getList(String key, Class<T> clazz) {
		List<T> list = new ArrayList<>();
		for (Object obj : lists.get(key)) {
			list.add(clazz.cast(obj));
		}
		return list;
	}

	Map<String, String> getPrimitives() {
		return primitives;
	}

	Map<String, XMLSerializationEntry> getObjects() {
		return objects;
	}
	
}


