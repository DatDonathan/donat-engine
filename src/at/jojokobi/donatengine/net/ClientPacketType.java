package at.jojokobi.donatengine.net;

import java.util.List;

import at.jojokobi.donatengine.event.UpdateEvent;
import at.jojokobi.donatengine.gui.actions.GUIAction;
import at.jojokobi.donatengine.level.Level;

public interface ClientPacketType {
	
	public List<ClientPacket> onUpdate (Level level, UpdateEvent event);
	
	public List<ClientPacket> onProcessGUIAction (long id, GUIAction action);

}
