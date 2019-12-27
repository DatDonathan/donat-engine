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

public class ButtonPacket implements ClientPacket {
	
	public static final ClientPacketType PACKET_TYPE = new ClientPacketType() {
		@Override
		public List<ClientPacket> onUpdate(Level level, LevelHandler handler) {
			List<ClientPacket> packets = new ArrayList<>();
			Map<String, Boolean> changes = handler.getInput().fetchChangedButtons();
			for (var e : changes.entrySet()) {
				packets.add(new ButtonPacket(e.getKey (), e.getValue()));
			}
			return packets;
		}
		
		@Override
		public List<ClientPacket> onProcessGUIAction(long id, GUIAction action) {
			return Arrays.asList();
		}
	};
	
	private String code;
	private boolean pressed;

	public ButtonPacket(String code, boolean pressed) {
		super();
		this.code = code;
		this.pressed = pressed;
	}
	
	public ButtonPacket() {
		
	}

	@Override
	public void serialize(DataOutput buffer) throws IOException {
		buffer.writeUTF(code);
		buffer.writeBoolean(pressed);
	}

	@Override
	public void deserialize(DataInput buffer) throws IOException {
		code = buffer.readUTF();
		pressed = buffer.readBoolean();
	}

	@Override
	public void apply(Level level, LevelHandler handler, long client) {
		handler.getInput(client).setButton(code, pressed);
	}

}
