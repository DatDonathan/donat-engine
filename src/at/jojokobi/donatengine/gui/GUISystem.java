package at.jojokobi.donatengine.gui;

import java.util.List;
import java.util.Map;

import at.jojokobi.donatengine.gui.actions.GUIAction;
import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.level.LevelHandler;
import at.jojokobi.donatengine.objects.Camera;
import at.jojokobi.donatengine.util.Pair;
import javafx.scene.canvas.GraphicsContext;

public interface GUISystem {
	
	public interface Listener {
		
		public void onAddGUI (GUISystem guiSystem, GUI gui, long id);
		
		public void onRemoveGUI (GUISystem guiSystem, GUI gui, long id);
		
	}

	/**
	 * 
	 * @param gui	The GUI - Type to be shown added
	 * @return		The ID of the gui
	 */
	public void showGUI (String type);
	
	/**
	 * 
	 * WARNING: Do not use this function. For internal use only
	 * 
	 * @param gui	The GUI - Type to be shown added
	 * @param id	The ID of the gui
	 */
	public void showGUI (String type, long id);
	
	/**
	 * 
	 * @param id	The id of the GUI to be removed
	 */
	public void removeGUI (long id);
	
	public Long getID (GUI gui);
	
	public GUI getGUI (long id);
	
	public Map<Long, GUI> getGUIs ();
	
	public void render (GraphicsContext ctx, double width, double height);
	
	public List<Pair<Long, GUIAction>> update (Level level, double width, double height, LevelHandler handler, Camera camera, double delta);
	
	public void addListener (Listener listener);
	
	public void removeListener (Listener listener);
	
}
