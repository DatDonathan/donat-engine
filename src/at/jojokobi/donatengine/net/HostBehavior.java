package at.jojokobi.donatengine.net;

import java.util.ArrayList;
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

public class HostBehavior implements MultiplayerBehavior {
	
	private List<ServerPacketType> packetTypes = Arrays.asList(SpawnPacket.PACKET_TYPE, DeletePacket.PACKET_TYPE, MovePacket.PACKET_TYPE, MotionPacket.PACKET_TYPE, PropertyChangedPacket.PACKET_TYPE, PropertyStateChangedPacket.PACKET_TYPE, LevelPropertyChangedPacket.PACKET_TYPE, LevelPropertyStateChangedPacket.PACKET_TYPE, AddAreaPacket.PACKET_TYPE, AddGUIPacket.PACKET_TYPE, RemoveGUIPacket.PACKET_TYPE);
	private List<ServerPacket> packets = new ArrayList<>();
	
	private boolean client;
	
//	private Server server;
//	private List<GameObjectWithId> spawned;
//	private List<GameObjectWithId> deleted;

//	public HostBehavior(Server server) {
//		super();
//		this.server = server;
//	}

	public HostBehavior(boolean client) {
		super();
		this.client = client;
	}

	@Override
	public boolean isClient() {
		return client;
	}

	@Override
	public boolean isHost() {
		return true;
	}

	@Override
	public void onUpdate(Level level, GameObject obj, long id, LevelHandler handler) {
		for (ServerPacketType type : packetTypes) {
			packets.addAll(type.onUpdate(level, obj, id));
		}
	}

	@Override
	public void onSpawn(Level level, GameObject obj, long id) {
//		spawned.add(new GameObjectWithId(id, obj));
		for (ServerPacketType type : packetTypes) {
			packets.addAll(type.onSpawn(level, obj, id));
		}
	}

	@Override
	public void onDelete(Level level, GameObject obj, long id) {
//		deleted.add(new GameObjectWithId(id, obj));
		for (ServerPacketType type : packetTypes) {
			packets.addAll(type.onDelete(level, obj, id));
		}
	}

	@Override
	public List<BinarySerializable> fetchPackets() {
		List<BinarySerializable> packets = new ArrayList<>();
		for (ServerPacket packet : this.packets) {
			packets.add(packet);
		}
		this.packets.clear();
		return packets;
	}

	@Override
	public List<BinarySerializable> recreateLevelPackets(Level level) {
		List<BinarySerializable> packets = new ArrayList<>();
		for (ServerPacketType type : packetTypes) {
			for (ServerPacket packet : type.recreatePackets(level)) {
				packets.add(packet);
			}
		}
		return packets;
	}

	@Override
	public void update(Level level, LevelHandler handler) {
		for (ServerPacketType type : packetTypes) {
			packets.addAll(type.update(level));
		}
	}

	@Override
	public void onAddArea(Level level, LevelArea area, String id) {
		for (ServerPacketType type : packetTypes) {
			packets.addAll(type.onAddArea(level, area, id));
		}
	}

	@Override
	public void processGUIAction(Level level, LevelHandler handler, Camera camera, long id, GUIAction action) {
		action.perform(level, handler, id, level.getGuiSystem(), camera);
	}

	@Override
	public void onAddGUI(GUISystem guiSystem, GUI gui, long id) {
		for (ServerPacketType type : packetTypes) {
			packets.addAll(type.onAddGUI(guiSystem, gui, id));
		}
	}

	@Override
	public void onRemoveGUI(GUISystem guiSystem, GUI gui, long id) {
		for (ServerPacketType type : packetTypes) {
			packets.addAll(type.onRemoveGUI(guiSystem, gui, id));
		}
	}

}
//
//class GameObjectWithId {
//	
//	private long id;
//	private GameObject obj;
//	
//	public GameObjectWithId(long id, GameObject obj) {
//		super();
//		this.id = id;
//		this.obj = obj;
//	}
//	public long getId() {
//		return id;
//	}
//	public GameObject getObj() {
//		return obj;
//	}
//	public void setId(long id) {
//		this.id = id;
//	}
//	public void setObj(GameObject obj) {
//		this.obj = obj;
//	}
//	
//}
