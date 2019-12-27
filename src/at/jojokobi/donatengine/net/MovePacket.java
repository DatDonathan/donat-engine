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

public class MovePacket implements ServerPacket{
	
	public static final ServerPacketType PACKET_TYPE = new ServerPacketType() {
		
		@Override
		public List<ServerPacket> onUpdate(Level level, GameObject object, long id) {
			return object.fetchMoved() ? Arrays.asList(new MovePacket(id, object.getX(), object.getY(), object.getZ(), object.getArea())) : new ArrayList<>();
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
	private double x;
	private double y;
	private double z;
	private String area;
	
	public MovePacket(long id, double x, double y, double z, String area) {
		super();
		this.id = id;
		this.x = x;
		this.y = y;
		this.z = z;
		this.area = area;
	}

	public MovePacket() {
		
	}

	@Override
	public void serialize(DataOutput buffer) throws IOException {
		buffer.writeLong (id);
		buffer.writeDouble(x);
		buffer.writeDouble(y);
		buffer.writeDouble(z);
		buffer.writeUTF(area);
	}

	@Override
	public void deserialize(DataInput buffer) throws IOException {
		id = buffer.readLong();
		x = buffer.readDouble();
		y = buffer.readDouble();
		z = buffer.readDouble();
		area = buffer.readUTF();
	}

	@Override
	public void apply(Level level, LevelHandler handler) {
		GameObject obj = level.getObjectById(id);
		if (obj != null) {
			obj.setX(x);
			obj.setY(y);
			obj.setZ(z);
			obj.setArea(area);
		}
		else {
			throw new IllegalStateException("The object with id " + id + " does not exist! Seems like the server and the client are asynchronous!");
		}
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MovePacket [id=");
		builder.append(id);
		builder.append(", x=");
		builder.append(x);
		builder.append(", y=");
		builder.append(y);
		builder.append(", z=");
		builder.append(z);
		builder.append(", area=");
		builder.append(area);
		builder.append("]");
		return builder.toString();
	}
	
}
