package at.jojokobi.donatengine.net;

import java.util.List;

import at.jojokobi.donatengine.Game;
import at.jojokobi.donatengine.event.UpdateEvent;
import at.jojokobi.donatengine.gui.GUISystem;
import at.jojokobi.donatengine.gui.actions.GUIAction;
import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.level.LevelArea;
import at.jojokobi.donatengine.objects.GameObject;
import at.jojokobi.donatengine.serialization.BinarySerializable;
import at.jojokobi.donatengine.tiles.TileSystem;

public interface MultiplayerBehavior extends GUISystem.Listener, TileSystem.Listener {
	
	public boolean isClient ();
	
	public boolean isHost ();
	
	public void update (Level level, UpdateEvent event);
	
	public void onUpdate (Level level, GameObject obj, long id, UpdateEvent event);
	
	public void onSpawn (Level level, GameObject obj, long id);
	
	public void onDelete (Level level, GameObject obj, long id);
	
	public void onAddArea (Level level, LevelArea area, String id);
	
	public void processGUIAction (Level level, Game game, long id, GUIAction action);
	
	public List<BinarySerializable> fetchPackets ();
	
	public List<BinarySerializable> recreateLevelPackets (Level level);

}
