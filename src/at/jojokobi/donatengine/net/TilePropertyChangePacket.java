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
import at.jojokobi.donatengine.serialization.binary.SerializationWrapper;
import at.jojokobi.donatengine.tiles.Tile;
import at.jojokobi.donatengine.tiles.TileInstance;

public class TilePropertyChangePacket implements ServerPacket {

	public static final ServerPacketType PACKET_TYPE = new ServerPacketType() {
		
		@Override
		public List<ServerPacket> onUpdate(Level level, GameObject object, long id, SerializationWrapper serialization) {
			List<ServerPacket> packets = new ArrayList<>();
			for (TileInstance inst : level.getTileSystem().getTiles()) {
				int index = 0;
				for (ObservableProperty<?> prop : inst.getTile().observableProperties()) {
					if (prop.fetchChanged()) {
						packets.add(new TilePropertyChangePacket(inst.getTilePosition().getX(), inst.getTilePosition().getY(), inst.getTilePosition().getZ(), inst.getTilePosition().getArea(), index, prop.get()));
					}
					index++;
				}
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
		public java.util.List<ServerPacket> onRemoveTile(int tileX, int tileY, int tileZ, String area) {
			return Arrays.asList();
		};
		
		@Override
		public List<ServerPacket> recreatePackets(Level level) {
			List<ServerPacket> packets = new ArrayList<>();
			return packets;
		}
	};
	
	private int tileX;
	private int tileY;
	private int tileZ;
	private String area;
	private int property;
	private Object value;


	public TilePropertyChangePacket(int tileX, int tileY, int tileZ, String area, int property, Object value) {
		super();
		this.tileX = tileX;
		this.tileY = tileY;
		this.tileZ = tileZ;
		this.area = area;
		this.property = property;
		this.value = value;
	}

	public TilePropertyChangePacket() {
		
	}

	@Override
	public void serialize(DataOutput buffer, SerializationWrapper serialization) throws IOException {
		buffer.writeInt(tileX);
		buffer.writeInt(tileY);
		buffer.writeInt(tileZ);
		buffer.writeUTF(area);
		buffer.writeInt(property);
		serialization.serialize(value, buffer);
	}

	@Override
	public void deserialize(DataInput buffer, SerializationWrapper serialization) throws IOException {
		tileX = buffer.readInt();
		tileY = buffer.readInt();
		tileZ = buffer.readInt();
		area = buffer.readUTF();
		property = buffer.readInt();
		value = serialization.deserialize(Object.class, buffer);
	}

	@Override
	public void apply(Level level, Game game, SerializationWrapper serialization) {
		Tile tile = level.getTileSystem().getTile(tileX, tileY, tileZ, area);

		if (tile != null) {
			if (tile.observableProperties().size() > property) {
				ObservableProperty<?> property = tile.observableProperties().get (this.property);
				property.setUnsafe(value);
			}
			else {
				throw new IllegalStateException("The tile at " + tileX + ", " + tileY + ", " + ", " + tileZ + ", " + area + " has no property with the id " + property + "! Seems like the server and the client are asynchronous!");
			}
		}
		else {
			throw new IllegalStateException("The tile at (" + tileX + ", " + tileY + ", " + ", " + tileZ + ", " + area + ") does not exist! Seems like the server and the client are asynchronous!");
		}
	}

}
