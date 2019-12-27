package at.jojokobi.donatengine.gui;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class DynamicGUIFactory implements GUIFactory {

	private Map<String, Supplier<GUI>> map = new HashMap<>();

	@Override
	public GUI createGUI(String id) {
		return map.get(id).get();
	}
	
	public void registerGUI (String id, Supplier<GUI> supplier) {
		map.put(id, supplier);
	}
	
}
