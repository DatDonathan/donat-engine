package at.jojokobi.donatengine.gui.actions;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import at.jojokobi.donatengine.gui.GUISystem;
import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.level.LevelHandler;
import at.jojokobi.donatengine.objects.Camera;

public class StopGameAction implements GUIAction{

	@Override
	public void serialize(DataOutput buffer) throws IOException {
		
	}

	@Override
	public void deserialize(DataInput buffer) throws IOException {
		
	}

	@Override
	public void perform(Level level, LevelHandler handler, long id, GUISystem system, Camera camera) {
		handler.stop();
	}

	@Override
	public boolean executeOnClient() {
		return true;
	}
	
	
	
}
