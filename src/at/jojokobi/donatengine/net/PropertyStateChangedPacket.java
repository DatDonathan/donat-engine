package at.jojokobi.donatengine.net;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.level.LevelHandler;
import at.jojokobi.donatengine.objects.GameObject;
import at.jojokobi.donatengine.objects.properties.ObservableProperty;

public class PropertyStateChangedPacket implements ServerPacket{
	
	public static final ServerPacketType PACKET_TYPE = new ServerPacketType() {
		
		@Override
		public List<ServerPacket> onUpdate(Level level, GameObject object, long id) {
			ArrayList<ServerPacket> packets = new ArrayList<>();
			int i = 0;
			for (ObservableProperty<?> property : object.observableProperties()) {
				if (property.stateChanged()) {
					try (ByteArrayOutputStream buffer = new ByteArrayOutputStream();
							DataOutputStream out = new DataOutputStream(buffer)){
						property.writeChanges(out);
						out.flush();
						buffer.flush();
						byte[] value = buffer.toByteArray();
						packets.add(new PropertyStateChangedPacket(id, i, value));
					}
					catch (IOException e) {
						e.printStackTrace();
					}
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
	private byte[] changes;

	public PropertyStateChangedPacket(long id, int property, byte[] changes) {
		super();
		this.id = id;
		this.property = property;
		this.changes = changes;
	}
	
	public PropertyStateChangedPacket() {
		
	}

	@Override
	public void serialize(DataOutput buffer) throws IOException {
		buffer.writeLong(id);
		buffer.writeInt(property);
		buffer.writeInt(changes.length);
		for (byte b : changes) {
			buffer.writeByte(b);
		}
	}

	@Override
	public void deserialize(DataInput buffer) throws IOException {
		id = buffer.readLong();
		property = buffer.readInt();
		int length = buffer.readInt();
		changes = new byte[length];
		for (int i = 0; i < changes.length; i++) {
			changes[i] = buffer.readByte();
		}
	}

	@Override
	public void apply(Level level, LevelHandler handler) {
		GameObject obj = level.getObjectById(id);
		
		if (obj != null) {
			if (obj.observableProperties().size() > property) {
				ObservableProperty<?> property = obj.observableProperties().get (this.property);
				try (DataInputStream out = new DataInputStream(new ByteArrayInputStream(changes))) {
					property.readChanges(out);
				} catch (IOException e) {
					e.printStackTrace();
				}
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
