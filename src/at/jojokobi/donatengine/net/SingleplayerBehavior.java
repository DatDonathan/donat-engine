package at.jojokobi.donatengine.net;

import java.util.Arrays;
import java.util.List;

import at.jojokobi.donatengine.gui.GUI;
import at.jojokobi.donatengine.gui.GUISystem;
import at.jojokobi.donatengine.gui.actions.GUIAction;
import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.level.LevelArea;
import at.jojokobi.donatengine.level.LevelHandler;
import at.jojokobi.donatengine.objects.Camera;
import at.jojokobi.donatengine.objects.GameObject;
import at.jojokobi.donatengine.serialization.BinarySerializable;

public class SingleplayerBehavior implements MultiplayerBehavior {

	@Override
	public boolean isClient() {
		return true;
	}

	@Override
	public boolean isHost() {
		return true;
	}

	@Override
	public void onUpdate(Level level, GameObject obj, long id, LevelHandler handler) {
		
	}

	@Override
	public void onSpawn(Level level, GameObject obj, long id) {
		
	}

	@Override
	public void onDelete(Level level, GameObject obj, long id) {
		
	}

	@Override
	public List<BinarySerializable> fetchPackets() {
		return Arrays.asList();
	}

	@Override
	public List<BinarySerializable> recreateLevelPackets(Level level) {
		return null;
	}

	@Override
	public void update(Level level, LevelHandler handler) {
		
	}

	@Override
	public void onAddArea(Level level, LevelArea area, String id) {
		
	}

	@Override
	public void processGUIAction(Level level, LevelHandler handler, Camera camera, long id, GUIAction action) {
		action.perform(level, handler, id, level.getGuiSystem(), camera);
	}

	@Override
	public void onAddGUI(GUISystem guiSystem, GUI gui, long id) {
		
	}

	@Override
	public void onRemoveGUI(GUISystem guiSystem, GUI gui, long id) {
		
	}

}
