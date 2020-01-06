package at.jojokobi.donatengine.net;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.level.LevelHandler;
import at.jojokobi.donatengine.objects.GameObject;
import at.jojokobi.donatengine.objects.properties.ObservableProperty;
import at.jojokobi.donatengine.serialization.SerializationWrapper;

public class PropertyChangedPacket implements ServerPacket {
	
	public static final ServerPacketType PACKET_TYPE = new ServerPacketType() {
		
		@Override
		public List<ServerPacket> onUpdate(Level level, GameObject object, long id, SerializationWrapper serialization) {
			ArrayList<ServerPacket> packets = new ArrayList<>();
			int i = 0;
			for (ObservableProperty<?> property : object.observableProperties()) {
				if (property.fetchChanged()) {
					packets.add(new PropertyChangedPacket(id, i, property.get()));
				}
				i++;
			}
			return packets;
		}
		
		@Override
		public List<ServerPacket> onSpawn(Level level, GameObject object, long id) {
			return new ArrayList<>();
		}
		
		@Override
		public List<ServerPacket> onDelete(Level level, GameObject object, long id) {
			return new ArrayList<>();
		}
		
		@Override
		public List<ServerPacket> recreatePackets(Level level) {
			return Arrays.asList ();
		}
	};
	
	private long id;
	private int property;
	private Object value;

	public PropertyChangedPacket(long id, int property, Object value) {
		super();
		this.id = id;
		this.property = property;
		this.value = value;
	}

	public PropertyChangedPacket() {
		
	}

	@Override
	public void serialize(DataOutput buffer, SerializationWrapper serialization) throws IOException {
		buffer.writeLong(id);
		buffer.writeInt(property);
		serialization.serialize(value, buffer);
	}

	@Override
	public void deserialize(DataInput buffer, SerializationWrapper serialization) throws IOException {
		id = buffer.readLong();
		property = buffer.readInt();
		value = serialization.deserialize(Object.class, buffer);
	}

	@Override
	public void apply(Level level, LevelHandler handler, SerializationWrapper serialization) {
		GameObject obj = level.getObjectById(id);
		
		if (obj != null) {
			if (obj.observableProperties().size() > property) {
				ObservableProperty<?> property = obj.observableProperties().get (this.property);
				property.setUnsafe(value);
			}
			else {
				throw new IllegalStateException("The object with id " + id + " has no property with the id " + property + "! Seems like the server and the client are asynchronous!");
			}
		}
		else {
			throw new IllegalStateException("The object with id " + id + " does not exist! Seems like the server and the client are asynchronous!");
		}
	}
	
}
