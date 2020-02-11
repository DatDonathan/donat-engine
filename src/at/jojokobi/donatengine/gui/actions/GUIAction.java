package at.jojokobi.donatengine.gui.actions;

import at.jojokobi.donatengine.Game;
import at.jojokobi.donatengine.gui.GUISystem;
import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.serialization.BinarySerializable;

public interface GUIAction extends BinarySerializable{
	
	public void perform (Level level, Game game, long id, GUISystem system);
	
	public boolean executeOnClient ();

}
