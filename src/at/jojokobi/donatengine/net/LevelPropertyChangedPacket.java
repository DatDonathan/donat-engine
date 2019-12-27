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

public class LevelPropertyChangedPacket implements ServerPacket {

	public static final ServerPacketType PACKET_TYPE = new ServerPacketType() {

		@Override
		public List<ServerPacket> update(Level level) {
			ArrayList<ServerPacket> packets = new ArrayList<>();
			int i = 0;
			for (ObservableProperty<?> property : level.observableProperties()) {
				if (property.fetchChanged()) {
					try (ByteArrayOutputStream buffer = new ByteArrayOutputStream();
							DataOutputStream out = new DataOutputStream(buffer)) {
						property.writeValue(out);
						out.flush();
						buffer.flush();
						byte[] value = buffer.toByteArray();
						packets.add(new LevelPropertyChangedPacket(i, value));
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
			List<ServerPacket> packets = new ArrayList<>();
			int i = 0;
			for (ObservableProperty<?> property : level.observableProperties()) {
				try (ByteArrayOutputStream buffer = new ByteArrayOutputStream();
						DataOutputStream out = new DataOutputStream(buffer)) {
					property.writeValue(out);
					out.flush();
					buffer.flush();
					byte[] value = buffer.toByteArray();
					packets.add(new LevelPropertyChangedPacket(i, value));
				} catch (IOException e) {
					e.printStackTrace();
				}
				i++;
			}
			return packets;
		}

		@Override
		public List<ServerPacket> onUpdate(Level level, GameObject object, long id) {
			return Arrays.asList();
		}
	};

	private int property;
	private byte[] value;

	public LevelPropertyChangedPacket(int property, byte[] value) {
		super();
		this.property = property;
		this.value = value;
	}

	public LevelPropertyChangedPacket() {

	}

	@Override
	public void serialize(DataOutput buffer) throws IOException {
		buffer.writeInt(property);
		buffer.writeInt(value.length);
		for (byte b : value) {
			buffer.writeByte(b);
		}
	}

	@Override
	public void deserialize(DataInput buffer) throws IOException {
		property = buffer.readInt();
		int length = buffer.readInt();
		value = new byte[length];
		for (int i = 0; i < value.length; i++) {
			value[i] = buffer.readByte();
		}
	}

	@Override
	public void apply(Level level, LevelHandler handler) {
		if (level.observableProperties().size() > property) {
			ObservableProperty<?> property = level.observableProperties().get(this.property);
			try (DataInputStream out = new DataInputStream(new ByteArrayInputStream(value))) {
				property.readValue(out);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			throw new IllegalStateException("The level has no property with the id " + property
					+ "! Seems like the server and the client are asynchronous!");
		}
	}

}
