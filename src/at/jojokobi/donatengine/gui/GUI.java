package at.jojokobi.donatengine.gui;

import java.util.List;

import at.jojokobi.donatengine.gui.actions.GUIAction;
import at.jojokobi.donatengine.input.Input;
import javafx.scene.canvas.GraphicsContext;

public interface GUI {
	
	public void render (long clientId, GraphicsContext ctx, double width, double height);
	
	public void update (long clientId, GUISystem system, Input input, double width, double height, double delta);
	
	public String getType ();
	
	public Object getData ();
	
	public List<GUIAction> fetchActions ();

}
