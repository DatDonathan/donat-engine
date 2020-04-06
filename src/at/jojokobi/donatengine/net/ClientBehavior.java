package at.jojokobi.donatengine.net;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import at.jojokobi.donatengine.Game;
import at.jojokobi.donatengine.event.UpdateEvent;
import at.jojokobi.donatengine.gui.GUI;
import at.jojokobi.donatengine.gui.GUISystem;
import at.jojokobi.donatengine.gui.actions.GUIAction;
import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.level.LevelArea;
import at.jojokobi.donatengine.objects.GameObject;
import at.jojokobi.donatengine.serialization.binary.BinarySerializable;
import at.jojokobi.donatengine.tiles.Tile;

public class ClientBehavior implements MultiplayerBehavior {
	
	private List<ClientPacketType> packetTypes = Arrays.asList(ButtonPacket.PACKET_TYPE, AxisPacket.PACKET_TYPE, GUIActionPacket.PACKET_TYPE);
	private List<ClientPacket> packets = new ArrayList<>();

	@Override
	public boolean isClient() {
		return true;
	}

	@Override
	public boolean isHost() {
		return false;
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
		List<BinarySerializable> packets = new ArrayList<BinarySerializable>(this.packets);
		this.packets.clear();
		return packets;
	}

	@Override
	public List<BinarySerializable> recreateLevelPackets(Level level) {
		return Arrays.asList();
	}

	@Override
	public void update(Level level, UpdateEvent event) {
		for (ClientPacketType type : packetTypes) {
			packets.addAll(type.onUpdate(level, event));
		}
	}

	@Override
	public void onAddArea(Level level, LevelArea area, String id) {
		
	}

	@Override
	public void processGUIAction(Level level, Game game, long id, GUIAction action) {
		for (ClientPacketType type : packetTypes) {
			packets.addAll(type.onProcessGUIAction(id, action));
		}
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
