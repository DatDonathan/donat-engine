package at.jojokobi.donatengine.serialization;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import at.jojokobi.donatengine.gui.actions.ChangeGUIAction;
import at.jojokobi.donatengine.gui.actions.ChangeLogicAction;
import at.jojokobi.donatengine.gui.actions.ChatAction;
import at.jojokobi.donatengine.gui.actions.StopGameAction;
import at.jojokobi.donatengine.level.LevelArea;
import at.jojokobi.donatengine.net.AddAreaPacket;
import at.jojokobi.donatengine.net.AddGUIPacket;
import at.jojokobi.donatengine.net.AxisPacket;
import at.jojokobi.donatengine.net.ButtonPacket;
import at.jojokobi.donatengine.net.DeletePacket;
import at.jojokobi.donatengine.net.GUIActionPacket;
import at.jojokobi.donatengine.net.LevelPropertyChangedPacket;
import at.jojokobi.donatengine.net.LevelPropertyStateChangedPacket;
import at.jojokobi.donatengine.net.MotionPacket;
import at.jojokobi.donatengine.net.MovePacket;
import at.jojokobi.donatengine.net.PlaceTilePacket;
import at.jojokobi.donatengine.net.PropertyChangedPacket;
import at.jojokobi.donatengine.net.PropertyStateChangedPacket;
import at.jojokobi.donatengine.net.RemoveGUIPacket;
import at.jojokobi.donatengine.net.RemoveTilePacket;
import at.jojokobi.donatengine.net.SpawnPacket;
import at.jojokobi.donatengine.net.TilePropertyChangePacket;
import at.jojokobi.donatengine.objects.properties.list.AddChange;
import at.jojokobi.donatengine.objects.properties.list.ClearChange;
import at.jojokobi.donatengine.objects.properties.list.EntryPropertyChange;
import at.jojokobi.donatengine.objects.properties.list.EntryPropertyStateChange;
import at.jojokobi.donatengine.objects.properties.list.EntryStateChange;
import at.jojokobi.donatengine.objects.properties.list.ObservableList;
import at.jojokobi.donatengine.objects.properties.list.RemoveChange;
import at.jojokobi.donatengine.objects.properties.list.RemoveIndexChange;
import at.jojokobi.donatengine.objects.properties.list.SetChange;
import at.jojokobi.donatengine.objects.properties.map.ObservableMap;
import at.jojokobi.donatengine.objects.properties.map.PutChange;
import at.jojokobi.donatengine.util.Vector2D;
import at.jojokobi.donatengine.util.Vector3D;

public final class BinarySerialization {
	
	private static BinarySerialization instance;
	
	private NameClassFactory nameClassFactory = new NameClassFactory();
	private IDClassFactory idClassFactory = new IDClassFactory();
	
	public static BinarySerialization getInstance () {
		if (instance == null) {
			instance = new BinarySerialization();
		}
		return instance;
	}
	
	private Map<Class<?>, BinarySerializer<?>> serializers = new HashMap<>();
	
	private BinarySerialization () {
		registerSerializer(Integer.class, new IntSerializer());
		registerSerializer(Long.class, new LongSerializer());
		registerSerializer(Double.class, new DoubleSerializer());
		registerSerializer(Boolean.class, new BooleanSerializer());
		registerSerializer(String.class, new StringSerializer());
		
		idClassFactory.addClass(Integer.class);
		idClassFactory.addClass(Long.class);
		idClassFactory.addClass(Double.class);
		idClassFactory.addClass(Boolean.class);
		idClassFactory.addClass(String.class);
		
		idClassFactory.addClass(ChangeGUIAction.class);
		idClassFactory.addClass(ChangeLogicAction.class);
		idClassFactory.addClass(ChatAction.class);
		idClassFactory.addClass(StopGameAction.class);
		
		idClassFactory.addClass(LevelArea.class);
		
		idClassFactory.addClass(AddAreaPacket.class);
		idClassFactory.addClass(AddGUIPacket.class);
		idClassFactory.addClass(AxisPacket.class);
		idClassFactory.addClass(ButtonPacket.class);
		idClassFactory.addClass(DeletePacket.class);
		idClassFactory.addClass(GUIActionPacket.class);
		idClassFactory.addClass(LevelPropertyChangedPacket.class);
		idClassFactory.addClass(LevelPropertyStateChangedPacket.class);
		idClassFactory.addClass(MotionPacket.class);
		idClassFactory.addClass(MovePacket.class);
		idClassFactory.addClass(PropertyChangedPacket.class);
		idClassFactory.addClass(PropertyStateChangedPacket.class);
		idClassFactory.addClass(RemoveGUIPacket.class);
		idClassFactory.addClass(SpawnPacket.class);
		idClassFactory.addClass(PlaceTilePacket.class);
		idClassFactory.addClass(RemoveTilePacket.class);
		idClassFactory.addClass(TilePropertyChangePacket.class);
		
		idClassFactory.addClass(AddChange.class);
		idClassFactory.addClass(ClearChange.class);
		idClassFactory.addClass(EntryPropertyChange.class);
		idClassFactory.addClass(EntryPropertyStateChange.class);
		idClassFactory.addClass(EntryStateChange.class);
		idClassFactory.addClass(ObservableList.class);
		idClassFactory.addClass(RemoveChange.class);
		idClassFactory.addClass(RemoveIndexChange.class);
		idClassFactory.addClass(SetChange.class);
		idClassFactory.addClass(ObservableMap.class);
		idClassFactory.addClass(at.jojokobi.donatengine.objects.properties.map.ClearChange.class);
		idClassFactory.addClass(PutChange.class);
		idClassFactory.addClass(at.jojokobi.donatengine.objects.properties.map.RemoveChange.class);
		
		idClassFactory.addClass(Vector2D.class);
		idClassFactory.addClass(Vector3D.class);
		
	}
	
	public <T> T deserialize (Class<T> clazz, DataInput input, SerializationWrapper serialization, ClassFactory classFact) throws IOException {
		Class<?> objClazz = classFact.readClass(input);
		T t = null;
		if (objClazz != null) {
			try {
				if (serializers.containsKey(objClazz)) {
					BinarySerializer<?> serializer = serializers.get(objClazz);
					t = clazz.cast(serializer.deserialize(input, serialization));
				}
				else if (objClazz.isEnum()) {
					t = clazz.cast(objClazz.getEnumConstants()[input.readInt()]);
				}
				else {
					Object obj = objClazz.getConstructor().newInstance();
					
					if (clazz.isInstance(obj)) {
						t = clazz.cast(obj);
						if (t instanceof BinarySerializable) {
							((BinarySerializable) t).deserialize(input, serialization);
						}
						else {
							throw new IllegalArgumentException("The serialized class " + obj.getClass() + " is not an instance of BinarySerializeable and no BinarySerializer is defined for the class");
						}
					}
					else {
						throw new ClassCastException("The serialized class " + obj.getClass() + " in not an instance of the given class " + clazz);
					}
				}
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
				throw new RuntimeException(e.getMessage(), e);
			}
		}
		return t;
	}
	
	public void serialize (Object obj, DataOutput output, SerializationWrapper serialization, ClassFactory classFact) throws IOException {
		if (obj == null) {
			classFact.writeClass(null, output);
		}
		else {
			classFact.writeClass(obj.getClass(), output);
			if (serializers.containsKey(obj.getClass())) {
				BinarySerializer<?> serializer = serializers.get(obj.getClass());
				serializer.serializeUnsafe(obj, output, serialization);
			}
			else if (obj.getClass().isEnum()) {
				output.writeInt(((Enum<?>) obj).ordinal());
			}
			else if (obj instanceof BinarySerializable) {
				((BinarySerializable) obj).serialize(output, serialization);
			}
			else {
				throw new IllegalArgumentException("The class " + obj.getClass() + " is not an instance of BinarySerializeable and no BinarySerializer is defined for the class");
			}
		}
	}
	
	public <T> void registerSerializer (Class<T> clazz, BinarySerializer<T> serializer) {
		serializers.put(clazz, serializer);
	}

	public NameClassFactory getNameClassFactory() {
		return nameClassFactory;
	}

	public IDClassFactory getIdClassFactory() {
		return idClassFactory;
	}

}
