package at.jojokobi.donatengine.level;

import java.util.Arrays;
import java.util.List;

import at.jojokobi.donatengine.Game;
import at.jojokobi.donatengine.event.UpdateEvent;
import at.jojokobi.donatengine.gui.GUI;
import at.jojokobi.donatengine.gui.GUISystem;
import at.jojokobi.donatengine.gui.actions.GUIAction;
import at.jojokobi.donatengine.objects.GameObject;
import at.jojokobi.donatengine.serialization.binary.BinarySerializable;
import at.jojokobi.donatengine.tiles.Tile;

public class SingleplayerBehavior implements LevelBehavior {

	@Override
	public boolean isClient() {
		return true;
	}

	@Override
	public boolean isHost() {
		return true;
	}

	@Override
	public void onUpdate(Level level, GameObject obj, long id, UpdateEvent event) {
		
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
	public void update(Level level, UpdateEvent event) {
		
	}

	@Override
	public void onAddArea(Level level, LevelArea area, String id) {
		
	}

	@Override
	public void processGUIAction(Level level, Game game, long id, GUIAction action) {
		action.perform(level, game, id, level.getGuiSystem());
	}

	@Override
	public void onAddGUI(GUISystem guiSystem, GUI gui, long id) {
		
	}

	@Override
	public void onRemoveGUI(GUISystem guiSystem, GUI gui, long id) {
		
	}

	@Override
	public void onPlace(Tile tile, int tileX, int tileY, int tileZ, String area) {
		
	}

	@Override
	public void onRemove(int tileX, int tileY, int tileZ, String area) {
		
	}

}
