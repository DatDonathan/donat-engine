package at.jojokobi.donatengine.gui;

public interface GUIType<T> {
	
	public GUI createGUI (T data);
	
	public Class<T> getDataClass ();
	
	public default GUI createGUIUnsafe (Object data) {
		return createGUI(getDataClass().cast(data));
	}

}
