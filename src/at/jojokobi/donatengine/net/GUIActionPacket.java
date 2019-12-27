package at.jojokobi.donatengine.net;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import at.jojokobi.donatengine.gui.actions.GUIAction;
import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.level.LevelHandler;
import at.jojokobi.donatengine.serialization.BinarySerialization;

public class GUIActionPacket implements ClientPacket{
	
	public static final ClientPacketType PACKET_TYPE = new ClientPacketType() {
		@Override
		public List<ClientPacket> onUpdate(Level level, LevelHandler handler) {
			return Arrays.asList();
		}
		
		@Override
		public List<ClientPacket> onProcessGUIAction(long id, GUIAction action) {
			return Arrays.asList(new GUIActionPacket(id, action));
		}
	};
	
	private long id;
	private GUIAction action;
	
	public GUIActionPacket() {

	}
	
	public GUIActionPacket(long id, GUIAction action) {
		super();
		this.id = id;
		this.action = action;
	}

	@Override
	public void serialize(DataOutput buffer) throws IOException {
		BinarySerialization.getInstance().serialize(action, buffer);
	}

	@Override
	public void deserialize(DataInput buffer) throws IOException {
		action = BinarySerialization.getInstance().deserialize(GUIAction.class, buffer);
	}

	@Override
	public void apply(Level level, LevelHandler handler, long client) {
		action.perform(level, handler, id, level.getGuiSystem(), null);
	}

}
