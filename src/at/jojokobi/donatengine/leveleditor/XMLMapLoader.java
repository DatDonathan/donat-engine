package at.jojokobi.donatengine.leveleditor;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
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

import at.jojokobi.fxengine.level.Level;
import at.jojokobi.fxengine.objects.GameObject;
import at.jojokobi.fxengine.objects.SerializableObject;

public class XMLMapLoader implements MapLoader {

	public static final String LEVEL_TAG = "level";
	public static final String OBJECTS_TAG = "objects";
	public static final String OBJECT_TAG = "object";
	public static final String X_TAG = "x";
	public static final String Y_TAG = "y";
	public static final String Z_TAG = "z";
	public static final String CLASS_TAG = "class";
	
	public XMLMapLoader() {
		
	}

	@Override
	public void load(InputStream stream, Level level) throws InvalidLevelFileException{
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setNamespaceAware(true);
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(stream);
			document.getDocumentElement().normalize();
			
			Node objectsNode = document.getElementsByTagName(OBJECTS_TAG).item(0);
			if (objectsNode != null && objectsNode.getNodeType() == Node.ELEMENT_NODE) {
				NodeList objects = ((Element) objectsNode).getElementsByTagName(OBJECT_TAG);
				for (int i = 0; i < objects.getLength(); i++) {
					if (objects.item(i).getNodeType() == Node.ELEMENT_NODE) {
						Element objElem = (Element) objects.item(i);
						double x = 0;
						double y = 0;
						double z = 0;
						//Position
						try {
							x = Double.parseDouble(objElem.getAttribute(X_TAG));
							y = Double.parseDouble(objElem.getAttribute(Y_TAG));
							z = Double.parseDouble(objElem.getAttribute(Z_TAG));
						}
						catch (NumberFormatException e) {
							throw new InvalidLevelFileException("Coordinates are no valid doubles", e);
						}
						//Attributes
						Map<String, String> attributes = new HashMap<>();
						NodeList attributeNodes = objElem.getChildNodes();
						for (int j = 0; j < attributeNodes.getLength(); j++) {
							attributes.put(attributeNodes.item(0).getNodeName(), attributeNodes.item(0).getTextContent());
						}
						//Class
						String className = objElem.getAttribute(CLASS_TAG);
						Object obj = null;
						try {
							Class<?> clazz = Class.forName(className);
							try {
								Method method = clazz.getMethod("deserialize", Double.TYPE, Double.TYPE, Double.TYPE, Map.class);
								obj = method.invoke(null, x, y, z, level, attributes);
							} catch (NoSuchMethodException e) {
								//Use constructor
								try {
									Constructor<?> constructor = clazz.getConstructor(Double.TYPE, Double.TYPE, Double.TYPE);
									obj = constructor.newInstance(x, y, z, level);
								} catch (NoSuchMethodException | InvocationTargetException | InstantiationException e1) {
									throw new InvalidLevelFileException(e1);
								} catch (SecurityException | IllegalAccessException e1) {
									e1.printStackTrace();
								}
							} catch (SecurityException | IllegalAccessException e) {
								e.printStackTrace();
							} catch (InvocationTargetException e) {
								throw new InvalidLevelFileException(e);
							}
						} catch (ClassNotFoundException e) {
							throw new InvalidLevelFileException(e);
						}
						//Add object
						if (obj instanceof GameObject) {
							level.spawn((GameObject) obj);
						}
						else {
							throw new InvalidLevelFileException("Serialized object is no GameObject: " + obj);
						}
					}
				}
			}
			else {
				throw new InvalidLevelFileException("No \"objects\" Element could be found!");
			}
		} catch (SAXException | IOException | ParserConfigurationException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void save(OutputStream stream, Level level) {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setNamespaceAware(true);
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.newDocument();
			
			Element levelElement = document.createElement(LEVEL_TAG);
			Element objectsElement = document.createElement(OBJECTS_TAG);
			//Iterate over objects
			for (GameObject obj : level.getObjects()) {				
				if (obj instanceof SerializableObject) {
					Element objElem = document.createElement(OBJECT_TAG);
					objElem.setAttribute(X_TAG, obj.getX() + "");
					objElem.setAttribute(Y_TAG, obj.getY() + "");
					objElem.setAttribute(Z_TAG, obj.getZ() + "");
					objElem.setAttribute(CLASS_TAG, obj.getClass().getName());
					
					Map<String, String> attributes = ((SerializableObject) obj).serialize();
					for (String key : attributes.keySet()) {
						Element element = document.createElement(key);
						element.setTextContent(attributes.get(key));
						objElem.appendChild(element);
					}
					
					objectsElement.appendChild(objElem);
				}
			}
			
			levelElement.appendChild(objectsElement);
			document.appendChild(levelElement);
			//Save to file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(document);
			StreamResult result = new StreamResult(stream);
			transformer.transform(source, result);
		} catch (ParserConfigurationException | TransformerException e) {
			e.printStackTrace();
		}
	}

}
