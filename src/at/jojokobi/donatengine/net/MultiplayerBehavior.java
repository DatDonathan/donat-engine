package at.jojokobi.donatengine.net;

import java.util.List;

import at.jojokobi.donatengine.gui.GUISystem;
import at.jojokobi.donatengine.gui.actions.GUIAction;
import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.level.LevelArea;
import at.jojokobi.donatengine.level.LevelHandler;
import at.jojokobi.donatengine.objects.Camera;
import at.jojokobi.donatengine.objects.GameObject;
import at.jojokobi.donatengine.serialization.BinarySerializable;

public interface MultiplayerBehavior extends GUISystem.Listener {
	
	public boolean isClient ();
	
	public boolean isHost ();
	
	public void update (Level level, LevelHandler handler);
	
	public void onUpdate (Level level, GameObject obj, long id, LevelHandler handler);
	
	public void onSpawn (Level level, GameObject obj, long id);
	
	public void onDelete (Level level, GameObject obj, long id);
	
	public void onAddArea (Level level, LevelArea area, String id);
	
	public void processGUIAction (Level level, LevelHandler handler, Camera camera, long id, GUIAction action);
	
	public List<BinarySerializable> fetchPackets ();
	
	public List<BinarySerializable> recreateLevelPackets (Level level);

}
