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

public class MotionPacket implements ServerPacket{
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MotionPacket [id=");
		builder.append(id);
		builder.append(", x=");
		builder.append(x);
		builder.append(", y=");
		builder.append(y);
		builder.append(", z=");
		builder.append(z);
		builder.append(", gravity=");
		builder.append(gravity);
		builder.append("]");
		return builder.toString();
	}

	public static final ServerPacketType PACKET_TYPE = new ServerPacketType() {
		
		@Override
		public List<ServerPacket> onUpdate(Level level, GameObject object, long id, SerializationWrapper serialization) {
			return object.fetchChangedMotion() ? Arrays.asList(new MovePacket(id, object.getX(), object.getY(),object.getZ(), object.getArea()), new MotionPacket(id, object.getxMotion(), object.getyMotion(), object.getzMotion(), object.getGravity())) : new ArrayList<>();
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
	private double gravity;

	public MotionPacket(long id, double x, double y, double z, double gravity) {
		super();
		this.id = id;
		this.x = x;
		this.y = y;
		this.z = z;
		this.gravity = gravity;
	}

	public MotionPacket() {
		
	}

	@Override
	public void serialize(DataOutput buffer, SerializationWrapper serialization) throws IOException {
		buffer.writeLong (id);
		buffer.writeDouble(x);
		buffer.writeDouble(y);
		buffer.writeDouble(z);
		buffer.writeDouble(gravity);
	}

	@Override
	public void deserialize(DataInput buffer, SerializationWrapper serialization) throws IOException {
		id = buffer.readLong();
		x = buffer.readDouble();
		y = buffer.readDouble();
		z = buffer.readDouble();
		gravity = buffer.readDouble();
	}

	@Override
	public void apply(Level level, LevelHandler handler, SerializationWrapper serialization) {
		GameObject obj = level.getObjectById(id);
		if (obj != null) {
			obj.setxMotion(x);
			obj.setyMotion(y);
			obj.setzMotion(z);
			obj.setGravity(gravity);
		}
		else {
			throw new IllegalStateException("The object with id " + id + " does not exist! Seems like the server and the client are asynchronous!");
		}
	}
	
}
