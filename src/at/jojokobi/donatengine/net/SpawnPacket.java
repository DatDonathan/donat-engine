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
import at.jojokobi.donatengine.serialization.SerializationWrapper;

public class SpawnPacket implements ServerPacket {
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SpawnPacket [id=");
		builder.append(id);
		builder.append(", object=");
		builder.append(object);
		builder.append("]");
		return builder.toString();
	}

	public static final ServerPacketType PACKET_TYPE = new ServerPacketType() {
		
		@Override
		public List<ServerPacket> onUpdate(Level level, GameObject object, long id, SerializationWrapper serialization) {
			return new ArrayList<>();
		}
		
		@Override
		public List<ServerPacket> onSpawn(Level level, GameObject object, long id) {
			return Arrays.asList(new SpawnPacket(id, object));
		}
		
		@Override
		public List<ServerPacket> onDelete(Level level, GameObject object, long id) {
			return new ArrayList<>();
		}
		
		@Override
		public List<ServerPacket> recreatePackets(Level level) {
			List<ServerPacket> packets = new ArrayList<>();
			for (GameObject obj : level.getObjects()) {
				packets.add(new SpawnPacket(level.getId(obj), obj));
			}
			return packets;
		}
	};
	
	private long id;
	private GameObject object;
	
	public SpawnPacket(long id, GameObject object) {
		super();
		this.id = id;
		this.object = object;
	}
	
	public SpawnPacket() {
		
	}

	@Override
	public void serialize(DataOutput buffer, SerializationWrapper serialization) throws IOException {
		buffer.writeLong(id);
		serialization.serialize(object, buffer);
	}

	@Override
	public void deserialize(DataInput buffer, SerializationWrapper serialization) throws IOException {
		id = buffer.readLong();
		object = serialization.deserialize(GameObject.class, buffer);
	}

	@Override
	public void apply(Level level, LevelHandler handler, SerializationWrapper serialization) {
		level.spawn(object, id);
	}

}
