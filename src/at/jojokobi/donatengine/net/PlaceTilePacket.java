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
import at.jojokobi.donatengine.serialization.SerializationWrapper;
import at.jojokobi.donatengine.tiles.Tile;
import at.jojokobi.donatengine.tiles.TileInstance;

public class PlaceTilePacket implements ServerPacket {

	public static final ServerPacketType PACKET_TYPE = new ServerPacketType() {
		
		@Override
		public List<ServerPacket> onUpdate(Level level, GameObject object, long id, SerializationWrapper serialization) {
			return new ArrayList<>();
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
		public java.util.List<ServerPacket> onPlaceTile(Tile tile, int tileX, int tileY, int tileZ, String area) {
			return Arrays.asList(new PlaceTilePacket(tile, tileX, tileY, tileZ, area));
		};
		
		@Override
		public List<ServerPacket> recreatePackets(Level level) {
			List<ServerPacket> packets = new ArrayList<>();
			for (TileInstance tile : level.getTileSystem().getTiles()) {
				packets.add(new PlaceTilePacket(tile.getTile(), tile.getTilePosition().getX(), tile.getTilePosition().getY(), tile.getTilePosition().getZ(), tile.getTilePosition().getArea()));
			}
			return packets;
		}
	};
	
	private Tile tile;
	private int tileX;
	private int tileY;
	private int tileZ;
	private String area;

	
	public PlaceTilePacket(Tile object, int tileX, int tileY, int tileZ, String area) {
		super();
		this.tile = object;
		this.tileX = tileX;
		this.tileY = tileY;
		this.tileZ = tileZ;
		this.area = area;
	}

	public PlaceTilePacket() {
		
	}

	@Override
	public void serialize(DataOutput buffer, SerializationWrapper serialization) throws IOException {
		serialization.serialize(tile, buffer);
		buffer.writeInt(tileX);
		buffer.writeInt(tileY);
		buffer.writeInt(tileZ);
		buffer.writeUTF(area);
	}

	@Override
	public void deserialize(DataInput buffer, SerializationWrapper serialization) throws IOException {
		tile = serialization.deserialize(Tile.class, buffer);
		tileX = buffer.readInt();
		tileY = buffer.readInt();
		tileZ = buffer.readInt();
		area = buffer.readUTF();
	}

	@Override
	public void apply(Level level, Game game, SerializationWrapper serialization) {
		level.getTileSystem().place(tile, tileX, tileY, tileZ, area);
	}

}
