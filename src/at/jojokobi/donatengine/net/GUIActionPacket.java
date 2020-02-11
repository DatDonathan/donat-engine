package at.jojokobi.donatengine.net;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import at.jojokobi.donatengine.Game;
import at.jojokobi.donatengine.event.UpdateEvent;
import at.jojokobi.donatengine.gui.actions.GUIAction;
import at.jojokobi.donatengine.input.InputHandler;
import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.serialization.SerializationWrapper;

public class GUIActionPacket implements ClientPacket{
	
	public static final ClientPacketType PACKET_TYPE = new ClientPacketType() {
		@Override
		public List<ClientPacket> onUpdate(Level level, UpdateEvent event) {
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
	public void serialize(DataOutput buffer, SerializationWrapper serialization) throws IOException {
		buffer.writeLong(id);
		serialization.serialize(action, buffer);
	}

	@Override
	public void deserialize(DataInput buffer, SerializationWrapper serialization) throws IOException {
		id = buffer.readLong();
		action = serialization.deserialize(GUIAction.class, buffer);
	}

	@Override
	public void apply(Level level, Game game, InputHandler input, long client) {
		if (level.getGuiSystem().getGUI(id) != null) {
			action.perform(level, game, id, level.getGuiSystem());
		}
	}

}
