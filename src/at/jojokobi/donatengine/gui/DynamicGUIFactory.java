package at.jojokobi.donatengine.gui;

import java.util.HashMap;
import java.util.Map;

public class DynamicGUIFactory implements GUIFactory {

	private Map<String, GUIType<?>> map = new HashMap<>();

	@Override
	public GUI createGUI(String id, Object data, long client) {
		return map.get(id).createGUIUnsafe(data, client);
	}
	
	public void registerGUI (String id, GUIType<?> type) {
		map.put(id, type);
	}
	
}
