package at.jojokobi.donatengine.input;

import java.util.List;
import java.util.Map;

import at.jojokobi.donatengine.util.Vector2D;

public interface Input {
	
	public static String CURSOR_AXIS = "cursor";
	public static String PRIMARY_BUTTON = "primary";
	public static String SECONDARY_BUTTON = "secondary";
	public static String SUBMIT_BUTTON = "submit";
	
	public boolean getButton (String code);
	
	/**
	 * 
	 * OPTIONAL
	 * 
	 * @param code
	 * @param pressed
	 * @return
	 */
	public boolean setButton (String code, boolean pressed);
	
	public Vector2D getAxis (String axis);
	
	/**
	 * 
	 * OPTIONAL
	 * 
	 * @param axis
	 * @param vector
	 * @return
	 */
	public boolean setAxis (String axis, Vector2D vector);
	
	public void reset ();
	
	
	public default boolean getPrimary () {
		return getButton(PRIMARY_BUTTON);
	}
	
	public default boolean getSecondary () {
		return getButton(SECONDARY_BUTTON);
	}
	
	public default boolean getSubmit () {
		return getButton(SUBMIT_BUTTON);
	}
	
	public default double getCursorX () {
		return getAxis(CURSOR_AXIS).getX();
	}
	
	public default double getCursorY () {
		return getAxis(CURSOR_AXIS).getY();
	}
	
	public Map<String, Boolean> fetchChangedButtons ();
	
	public Map<String, Vector2D> fetchChangedAxis ();
	
	/**
	 * OPTIONAL
	 * 
	 * @return	Returns all typed characters since the last clear.
	 */
	public List<String> getTypedChars ();
	
	/**
	 * OPTIONAL
	 * 
	 * Clears the typed chars buffer
	 */
	public void updateBuffers ();
	
	/**
	 * 
	 * @param button	The button to be checked
	 * @return			Returns true if the button was pressed down in this frame
	 */
	public boolean isPressed (String button);

}
