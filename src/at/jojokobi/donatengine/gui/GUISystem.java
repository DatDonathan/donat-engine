package at.jojokobi.donatengine.gui;

import java.util.List;
import java.util.Map;

import at.jojokobi.donatengine.event.UpdateEvent;
import at.jojokobi.donatengine.gui.actions.GUIAction;
import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.rendering.RenderData;
import at.jojokobi.donatengine.util.Pair;

public interface GUISystem {
	
	public interface Listener {
		
		public void onAddGUI (GUISystem guiSystem, GUI gui, long id);
		
		public void onRemoveGUI (GUISystem guiSystem, GUI gui, long id);
		
	}

	/**
	 * 
	 * @param gui	The GUI - Type to be shown
	 * @return		The ID of the gui
	 */
	public void showGUI (String type, Object data, long client);
	
	/**
	 * 
	 * WARNING: Do not use this function. For internal use only
	 * 
	 * @param gui	The GUI - Type to be shown
	 * @param id	The ID of the gui
	 */
	public void showGUI (String type, Object data, long id, long client);
	
	/**
	 * 
	 * @param id	The id of the GUI to be removed
	 */
	public void removeGUI (long id);
	
	public Long getID (GUI gui);
	
	public GUI getGUI (long id);
	
	public Map<Long, GUI> getGUIs ();
	
	public void render (long clientId, List<RenderData> data, double width, double height);
	
	public List<Pair<Long, GUIAction>> update (Level level, double width, double height, UpdateEvent event);
	
	public void addListener (Listener listener);
	
	public void removeListener (Listener listener);
	
	public void clear ();
	
}
