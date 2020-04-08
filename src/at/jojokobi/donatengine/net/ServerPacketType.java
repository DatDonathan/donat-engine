package at.jojokobi.donatengine.net;

import java.util.Arrays;
import java.util.List;

import at.jojokobi.donatengine.gui.GUI;
import at.jojokobi.donatengine.gui.GUISystem;
import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.level.LevelArea;
import at.jojokobi.donatengine.objects.GameObject;
import at.jojokobi.donatengine.serialization.binary.SerializationWrapper;
import at.jojokobi.donatengine.tiles.Tile;

public interface ServerPacketType {
	
	public List<ServerPacket> onSpawn (Level level, GameObject object, long id);
	
	public List<ServerPacket> onDelete (Level level, GameObject object, long id);

	public List<ServerPacket> onUpdate (Level level, GameObject object, long id, SerializationWrapper serialization);
	
	public default List<ServerPacket> update (Level level, SerializationWrapper serialization) {
		return Arrays.asList();
	}
	
	public default List<ServerPacket> onAddArea (Level level, LevelArea area, String id) {
		return Arrays.asList();
	}
	
	public default List<ServerPacket> onAddGUI (GUISystem system, GUI gui, long id) {
		return Arrays.asList();
	}
	
	public default List<ServerPacket> onRemoveGUI (GUISystem system, GUI gui, long id) {
		return Arrays.asList();
	}
	
	public default List<ServerPacket> onPlaceTile (Tile tile, int tileX, int tileY, int tileZ, String area) {
		return Arrays.asList();
	}
	
	public default List<ServerPacket> onRemoveTile (int tileX, int tileY, int tileZ, String area) {
		return Arrays.asList();
	}
	
	public List<ServerPacket> recreatePackets (Level level);
	
}
