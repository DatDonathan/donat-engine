package at.jojokobi.donatengine.net;

import java.util.List;

import at.jojokobi.donatengine.gui.actions.GUIAction;
import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.level.LevelHandler;

public interface ClientPacketType {
	
	public List<ClientPacket> onUpdate (Level level, LevelHandler handler);
	
	public List<ClientPacket> onProcessGUIAction (long id, GUIAction action);

}
