package at.jojokobi.donatengine.gui.actions;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import at.jojokobi.donatengine.gui.GUISystem;
import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.level.LevelHandler;
import at.jojokobi.donatengine.objects.Camera;
import at.jojokobi.donatengine.serialization.SerializationWrapper;

public class ChangeGUIAction implements GUIAction{
	
	private String type;
	private Object data;

	public ChangeGUIAction(String type, Object data) {
		super();
		this.type = type;
		this.data = data;
	}

	public ChangeGUIAction(String type) {
		this(type, null);
	}
	
	public ChangeGUIAction() {
		this("");
	}

	@Override
	public void serialize(DataOutput buffer, SerializationWrapper serialization) throws IOException {
		buffer.writeUTF(type);
		serialization.serialize(data, buffer);
	}

	@Override
	public void deserialize(DataInput buffer, SerializationWrapper serialization) throws IOException {
		type = buffer.readUTF();
		data = serialization.deserialize(Object.class, buffer);
	}

	@Override
	public void perform(Level level, LevelHandler handler, long id, GUISystem system, Camera camera) {
		long client = system.getGUI(id).getClient();
		system.removeGUI(id);
		system.showGUI(type, data, client);
	}

	@Override
	public boolean executeOnClient() {
		return false;
	}

}
