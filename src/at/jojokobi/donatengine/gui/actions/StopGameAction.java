package at.jojokobi.donatengine.gui.actions;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import at.jojokobi.donatengine.Game;
import at.jojokobi.donatengine.gui.GUISystem;
import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.serialization.binary.SerializationWrapper;

public class StopGameAction implements GUIAction{

	@Override
	public void serialize(DataOutput buffer, SerializationWrapper serialization) throws IOException {
		
	}

	@Override
	public void deserialize(DataInput buffer, SerializationWrapper serialization) throws IOException {
		
	}

	@Override
	public void perform(Level level, Game game, long id, GUISystem system) {
		game.stop();
	}

	@Override
	public boolean executeOnClient() {
		return true;
	}
	
	
	
}
