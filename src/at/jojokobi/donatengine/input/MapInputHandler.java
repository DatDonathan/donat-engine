package at.jojokobi.donatengine.input;

import java.util.Map;

public class MapInputHandler implements InputHandler {

	private Input local;
	private Map<Long, Input> map;
	
	public MapInputHandler(Map<Long, Input> map) {
		super();
		this.map = map;
	}

	@Override
	public Input getInput(long clientId) {
		return clientId == SCENE_INPUT ? local : map.get(clientId);
	}
	
}
