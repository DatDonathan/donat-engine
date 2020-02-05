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
import at.jojokobi.donatengine.serialization.SerializationWrapper;

public class LevelPropertyStateChangedPacket implements ServerPacket {

	public static final ServerPacketType PACKET_TYPE = new ServerPacketType() {

		@Override
		public List<ServerPacket> update(Level level, SerializationWrapper serialization) {
			ArrayList<ServerPacket> packets = new ArrayList<>();
			int i = 0;
			for (ObservableProperty<?> property : level.observableProperties()) {
				if (property.stateChanged()) {
					try (ByteArrayOutputStream buffer = new ByteArrayOutputStream();
							DataOutputStream out = new DataOutputStream(buffer)) {
						property.writeChanges(out, serialization);
						out.flush();
						buffer.flush();
						byte[] value = buffer.toByteArray();
						packets.add(new LevelPropertyStateChangedPacket(i, value));
					} catch (IOException e) {
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
			return Arrays.asList();
		}

		@Override
		public List<ServerPacket> onUpdate(Level level, GameObject object, long id, SerializationWrapper serialization) {
			return Arrays.asList();
		}
	};

	private int property;
	private byte[] changes;

	public LevelPropertyStateChangedPacket(int property, byte[] changes) {
		super();
		this.property = property;
		this.changes = changes;
	}
	
	public LevelPropertyStateChangedPacket() {
		
	}

	@Override
	public void serialize(DataOutput buffer, SerializationWrapper serialization) throws IOException {
		buffer.writeInt(property);
		buffer.writeInt(changes.length);
		for (byte b : changes) {
			buffer.writeByte(b);
		}
	}

	@Override
	public void deserialize(DataInput buffer, SerializationWrapper serialization) throws IOException {
		property = buffer.readInt();
		int length = buffer.readInt();
		changes = new byte[length];
		for (int i = 0; i < changes.length; i++) {
			changes[i] = buffer.readByte();
		}
	}

	@Override
	public void apply(Level level, LevelHandler handler, SerializationWrapper serialization) {
		if (level.observableProperties().size() > property) {
			ObservableProperty<?> property = level.observableProperties().get(this.property);
			try (DataInputStream out = new DataInputStream(new ByteArrayInputStream(changes))) {
				property.readChanges(out, serialization);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			throw new IllegalStateException("The level has no property with the id " + property
					+ "! Seems like the server and the client are asynchronous!");
		}
	}

}
