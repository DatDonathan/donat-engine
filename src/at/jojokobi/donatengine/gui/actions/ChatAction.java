package at.jojokobi.donatengine.gui.actions;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import at.jojokobi.donatengine.Game;
import at.jojokobi.donatengine.gui.GUISystem;
import at.jojokobi.donatengine.level.ChatComponent;
import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.serialization.SerializationWrapper;

public class ChatAction implements GUIAction {
	
	private String message;

	public ChatAction(String message) {
		super();
		this.message = message;
	}
	
	public ChatAction() {
		this("");
	}

	@Override
	public void serialize(DataOutput buffer, SerializationWrapper serialization) throws IOException {
		buffer.writeUTF(message);
	}

	@Override
	public void deserialize(DataInput buffer, SerializationWrapper serialization) throws IOException {
		message = buffer.readUTF();
	}

	@Override
	public void perform(Level level, Game game, long id, GUISystem system) {
		level.getComponent(ChatComponent.class).postMessage(message);
	}

	@Override
	public boolean executeOnClient() {
		return false;
	}
	
}
