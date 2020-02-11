package at.jojokobi.donatengine.net;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import at.jojokobi.donatengine.Game;
import at.jojokobi.donatengine.gui.GUI;
import at.jojokobi.donatengine.gui.GUISystem;
import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.objects.GameObject;
import at.jojokobi.donatengine.serialization.SerializationWrapper;

public class RemoveGUIPacket implements ServerPacket {
	
	public static final ServerPacketType PACKET_TYPE = new ServerPacketType() {
		
		@Override
		public List<ServerPacket> onUpdate(Level level, GameObject object, long id, SerializationWrapper serialization) {
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
			return new ArrayList<>();
		}
		
		@Override
		public List<ServerPacket> onRemoveGUI(GUISystem guiSystem, GUI gui, long id) {
			return Arrays.asList(new RemoveGUIPacket(id));
		};
	};
	
	private long id;

	public RemoveGUIPacket(long id) {
		super();
		this.id = id;
	}
	
	public RemoveGUIPacket() {
		this(0);
	}

	@Override
	public void serialize(DataOutput buffer, SerializationWrapper serialization) throws IOException {
		buffer.writeLong(id);
	}

	@Override
	public void deserialize(DataInput buffer, SerializationWrapper serialization) throws IOException {
		id = buffer.readLong();
	}

	@Override
	public void apply(Level level, Game game, SerializationWrapper serialization) {
		level.getGuiSystem().removeGUI(id);
	}

}
