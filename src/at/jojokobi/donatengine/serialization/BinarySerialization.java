package at.jojokobi.donatengine.serialization;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public final class BinarySerialization {
	
	private static BinarySerialization instance;
	
	private static final String NULL = "null";
	
	public static BinarySerialization getInstance () {
		if (instance == null) {
			instance = new BinarySerialization();
		}
		return instance;
	}
	
	private Map<Class<?>, BinarySerializer<?>> serializers = new HashMap<>();
	
	private BinarySerialization () {
		registerSerializer(String.class, new StringSerializer());
		registerSerializer(Long.class, new LongSerializer());
	}
	
	public <T> T deserialize (Class<T> clazz, DataInput input) throws IOException {
		String className = input.readUTF();
		T t = null;
		if (!NULL.equals(className)) {
			try {
				Class<?> objClazz = Class.forName(className);
				if (serializers.containsKey(objClazz)) {
					BinarySerializer<?> serializer = serializers.get(objClazz);
					t = clazz.cast(serializer.deserialize(input));
				}
				else {
					Object obj = objClazz.getConstructor().newInstance();
					
					if (clazz.isInstance(obj)) {
						t = clazz.cast(obj);
						if (t instanceof BinarySerializable) {
							((BinarySerializable) t).deserialize(input);
						}
						else {
							throw new IllegalArgumentException("The serialized class is not an instance of BinarySerializeable and no BinarySerializer is defined for the class");
						}
					}
					else {
						throw new ClassCastException("The serialized class in not an instance of the given class " + clazz);
					}
				}
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
				throw new RuntimeException(e.getMessage(), e);
			}
		}
		return t;
	}
	
	public void serialize (Object obj, DataOutput output) throws IOException {
		if (obj == null) {
			output.writeUTF (NULL);
		}
		else {
			output.writeUTF(obj.getClass().getName());
			if (serializers.containsKey(obj.getClass())) {
				BinarySerializer<?> serializer = serializers.get(obj.getClass());
				serializer.serializeUnsafe(obj, output);
			}
			else if (obj instanceof BinarySerializable) {
				((BinarySerializable) obj).serialize(output);
			}
			else {
				throw new IllegalArgumentException("The class is not an instance of BinarySerializeable and no BinarySerializer is defined for the class");
			}
		}
	}
	
	public <T> void registerSerializer (Class<T> clazz, BinarySerializer<T> serializer) {
		serializers.put(clazz, serializer);
	}

}
