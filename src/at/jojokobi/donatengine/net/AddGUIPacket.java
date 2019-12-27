package at.jojokobi.donatengine.net;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import at.jojokobi.donatengine.gui.GUI;
import at.jojokobi.donatengine.gui.GUISystem;
import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.level.LevelHandler;
import at.jojokobi.donatengine.objects.GameObject;

public class AddGUIPacket implements ServerPacket {
	
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
		public List<ServerPacket> onDelete(Level level, GameObject object, long id) {
			return new ArrayList<>();
		}
		
		@Override
		public List<ServerPacket> recreatePackets(Level level) {
			List<ServerPacket> packets = new ArrayList<>();
			for (Map.Entry<Long, GUI> entry : level.getGUIs().entrySet()) {
				packets.add(new AddGUIPacket(entry.getValue().getType(), entry.getKey()));
			}
			return packets;
		}
		
		@Override
		public List<ServerPacket> onAddGUI(GUISystem system, GUI gui, long id) {
			return Arrays.asList(new AddGUIPacket(gui.getType(), id));
		};
	};
	
	private String gui;
	private long id;

	public AddGUIPacket(String gui, long id) {
		super();
		this.gui = gui;
		this.id = id;
	}
	
	public AddGUIPacket() {
		this("", 0);
	}

	@Override
	public void serialize(DataOutput buffer) throws IOException {
		buffer.writeUTF(gui);
		buffer.writeLong(id);
	}

	@Override
	public void deserialize(DataInput buffer) throws IOException {
		gui = buffer.readUTF();
		id = buffer.readLong();
	}

	@Override
	public void apply(Level level, LevelHandler handler) {
		level.getGuiSystem().showGUI(gui, id);
	}

}
