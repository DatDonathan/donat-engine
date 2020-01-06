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

	public ChangeGUIAction(String type) {
		super();
		this.type = type;
	}

	public ChangeGUIAction() {
		this("");
	}

	@Override
	public void serialize(DataOutput buffer, SerializationWrapper serialization) throws IOException {
		buffer.writeUTF(type);
	}

	@Override
	public void deserialize(DataInput buffer, SerializationWrapper serialization) throws IOException {
		type = buffer.readUTF();
	}

	@Override
	public void perform(Level level, LevelHandler handler, long id, GUISystem system, Camera camera) {
		system.removeGUI(id);
		system.showGUI(type);
	}

	@Override
	public boolean executeOnClient() {
		return false;
	}

}
