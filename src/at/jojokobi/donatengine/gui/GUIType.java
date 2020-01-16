package at.jojokobi.donatengine.gui;

public interface GUIType<T> {
	
	public GUI createGUI (T data, long client);
	
	public Class<T> getDataClass ();
	
	public default GUI createGUIUnsafe (Object data, long client) {
		return createGUI(getDataClass().cast(data), client);
	}

}
