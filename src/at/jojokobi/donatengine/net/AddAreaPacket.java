package at.jojokobi.donatengine.net;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.level.LevelArea;
import at.jojokobi.donatengine.level.LevelHandler;
import at.jojokobi.donatengine.objects.GameObject;
import at.jojokobi.donatengine.serialization.BinarySerialization;

public class AddAreaPacket implements ServerPacket {
	
	public static final ServerPacketType PACKET_TYPE = new ServerPacketType() {
		
		@Override
		public List<ServerPacket> onUpdate(Level level, GameObject object, long id) {
			return new ArrayList<>();
		}
		
		@Override
		public List<ServerPacket> onSpawn(Level level, GameObject object, long id) {
			return Arrays.asList();
		}
		
		@Override
		public List<ServerPacket> onAddArea(Level level, LevelArea area, String id) {
			return Arrays.asList(new AddAreaPacket(id, area));
		}
		
		@Override
		public List<ServerPacket> onDelete(Level level, GameObject object, long id) {
			return new ArrayList<>();
		}
		
		@Override
		public List<ServerPacket> recreatePackets(Level level) {
			List<ServerPacket> packets = new ArrayList<>();
			for (LevelArea area : level.getAreas()) {
				packets.add(new AddAreaPacket(level.getId(area), area));
			}
			return packets;
		}
	};
	
	private String id;
	private LevelArea area;
	
	public AddAreaPacket(String id, LevelArea area) {
		super();
		this.id = id;
		this.area = area;
	}
	
	public AddAreaPacket() {
		
	}

	@Override
	public void serialize(DataOutput buffer) throws IOException {
		buffer.writeUTF(id);
		BinarySerialization.getInstance().serialize(area, buffer);
	}

	@Override
	public void deserialize(DataInput buffer) throws IOException {
		id = buffer.readUTF();
		area = BinarySerialization.getInstance().deserialize(LevelArea.class, buffer);
	}

	@Override
	public void apply(Level level, LevelHandler handler) {
		level.addArea(id, area);
	}

}
