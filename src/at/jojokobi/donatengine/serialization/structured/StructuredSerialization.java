package at.jojokobi.donatengine.serialization.structured;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public final class StructuredSerialization {

	private static StructuredSerialization instance;

	public static StructuredSerialization getInstance() {
		if (instance == null) {
			instance = new StructuredSerialization();
		}
		return instance;
	}

	private Map<Class<?>, StructuredSerializer<?>> serializers = new HashMap<>();

	private StructuredSerialization() {

	}

	public <T> T deserialize(Class<T> clazz, SerializationEntry entry) {
		T t = null;
		Class<?> objClazz = entry.getSerializedClass();
		SerializedData data = entry.getData();
		if (objClazz != null) {
			try {
				if (serializers.containsKey(objClazz)) {
					StructuredSerializer<?> serializer = serializers.get(objClazz);
					t = clazz.cast(serializer.deserialize(data));
				} else {
					Object obj = objClazz.getConstructor().newInstance();

					if (clazz.isInstance(obj)) {
						t = clazz.cast(obj);
						if (t instanceof StructuredSerializable) {
							((StructuredSerializable) t).deserialize(data);
						} else {
							throw new IllegalArgumentException("The serialized class " + obj.getClass()
									+ " is not an instance of StructuredSerializeable and no StructuredSerializer is defined for the class");
						}
					} else {
						throw new ClassCastException("The serialized class " + obj.getClass()
								+ " in not an instance of the given class " + clazz);
					}
				}
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
				throw new RuntimeException(e.getMessage(), e);
			}
		}

		return t;
	}

	public void serialize(Object obj, SerializationEntry entry) {
		if (obj == null) {
			entry.setSerializedClass(null);
		} else {
			entry.setSerializedClass(obj.getClass());
			if (serializers.containsKey(obj.getClass())) {
				StructuredSerializer<?> serializer = serializers.get(obj.getClass());
				serializer.serializeUnsafe(obj, entry.getData());
			} else if (obj instanceof  StructuredSerializable) {
				((StructuredSerializable) obj).serialize(entry.getData());
			} else {
				throw new IllegalArgumentException("The class " + obj.getClass()
						+ " is not an instance of StructuredSerializeable and no StructuredSerializer is defined for the class");
			}
		}
	}

	public <T> void registerSerializer(Class<T> clazz, StructuredSerializer<T> serializer) {
		serializers.put(clazz, serializer);
	}

}
