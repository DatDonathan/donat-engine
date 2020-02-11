package at.jojokobi.donatengine.net;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import at.jojokobi.donatengine.Game;
import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.objects.GameObject;
import at.jojokobi.donatengine.objects.properties.ObservableProperty;
import at.jojokobi.donatengine.serialization.SerializationWrapper;

public class LevelPropertyChangedPacket implements ServerPacket {

	public static final ServerPacketType PACKET_TYPE = new ServerPacketType() {

		@Override
		public List<ServerPacket> update(Level level, SerializationWrapper serialization) {
			ArrayList<ServerPacket> packets = new ArrayList<>();
			int i = 0;
			for (ObservableProperty<?> property : level.observableProperties()) {
				if (property.fetchChanged()) {
					packets.add(new LevelPropertyChangedPacket(i, property.get()));
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
				packets.add(new LevelPropertyChangedPacket(i, property.get()));
				i++;
			}
			return packets;
		}

		@Override
		public List<ServerPacket> onUpdate(Level level, GameObject object, long id, SerializationWrapper serialization) {
			return Arrays.asList();
		}
	};

	private int property;
	private Object value;

	public LevelPropertyChangedPacket(int property, Object value) {
		super();
		this.property = property;
		this.value = value;
	}

	public LevelPropertyChangedPacket() {

	}

	@Override
	public void serialize(DataOutput buffer, SerializationWrapper serialization) throws IOException {
		buffer.writeInt(property);
		serialization.serialize(value, buffer);
	}

	@Override
	public void deserialize(DataInput buffer, SerializationWrapper serialization) throws IOException {
		property = buffer.readInt();
		value = serialization.deserialize(Object.class, buffer);
	}

	@Override
	public void apply(Level level, Game game, SerializationWrapper serialization) {
		if (level.observableProperties().size() > property) {
			ObservableProperty<?> property = level.observableProperties().get(this.property);
			property.setUnsafe(value);
		} else {
			throw new IllegalStateException("The level has no property with the id " + property
					+ "! Seems like the server and the client are asynchronous!");
		}
	}
	
}
