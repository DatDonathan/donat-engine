package at.jojokobi.donatengine.net;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import at.jojokobi.donatengine.gui.actions.GUIAction;
import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.level.LevelHandler;
import at.jojokobi.donatengine.serialization.SerializationWrapper;
import at.jojokobi.donatengine.util.Vector2D;

public class AxisPacket implements ClientPacket {
	
	public static final ClientPacketType PACKET_TYPE = new ClientPacketType() {
		@Override
		public List<ClientPacket> onUpdate(Level level, LevelHandler handler) {
			List<ClientPacket> packets = new ArrayList<>();
			Map<String, Vector2D> changes = handler.getInput().fetchChangedAxis();
			for (var e : changes.entrySet()) {
				packets.add(new AxisPacket(e.getKey (), e.getValue()));
			}
			return packets;
		}

		@Override
		public List<ClientPacket> onProcessGUIAction(long id, GUIAction action) {
			return Arrays.asList();
		}
	};
	
	private String axis;
	private Vector2D vector;

	public AxisPacket(String axis, Vector2D vector) {
		super();
		this.axis = axis;
		this.vector = vector;
	}
	
	public AxisPacket() {
		
	}

	@Override
	public void serialize(DataOutput buffer, SerializationWrapper serialization) throws IOException {
		buffer.writeUTF(axis);
		serialization.serialize(vector, buffer);
	}

	@Override
	public void deserialize(DataInput buffer, SerializationWrapper serialization) throws IOException {
		axis = buffer.readUTF();
		vector = serialization.deserialize(Vector2D.class, buffer);
	}

	@Override
	public void apply(Level level, LevelHandler handler, long client) {
		handler.getInput(client).setAxis(axis, vector);
	}

}
