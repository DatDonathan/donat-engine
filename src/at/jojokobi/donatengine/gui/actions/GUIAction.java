package at.jojokobi.donatengine.gui.actions;

import at.jojokobi.donatengine.gui.GUISystem;
import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.level.LevelHandler;
import at.jojokobi.donatengine.objects.Camera;
import at.jojokobi.donatengine.serialization.BinarySerializable;

public interface GUIAction extends BinarySerializable{
	
	public void perform (Level level, LevelHandler handler, long id, GUISystem system, Camera camera);
	
	public boolean executeOnClient ();

}
