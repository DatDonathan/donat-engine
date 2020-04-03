package at.jojokobi.donatengine.cutscene;

import java.util.HashMap;
import java.util.Map;

public class CutsceneHandler { 

	private Map<String, Cutscene> map = new HashMap<String, Cutscene>();
	
	public Cutscene getCutscene(String key) {
		return map.get(key);
	}
	
	public void addCutscene(String key, Cutscene cutscene) {
		map.put(key, cutscene);
	}

}
