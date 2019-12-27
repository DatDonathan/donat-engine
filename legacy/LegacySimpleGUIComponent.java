package at.jojokobi.donatengine.gui;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;

@Deprecated
public interface LegacySimpleGUIComponent {
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param button
	 * @return	Whether the gui should be evaluted
	 */
	public boolean onClick (double x, double y);
	
	/**
	 * 
	 * @param selected
	 * @param key
	 * @return	Whether the gui should be evaluated
	 */
	public boolean onKeyPress (boolean selected, KeyCode key);
	
	public LegacyDynamicRect getRect ();
	
	public void render (GraphicsContext ctx, boolean selected, boolean hovered, boolean clicked, double totalWidth, double totalHeight);
	
	public String getValue ();

}
