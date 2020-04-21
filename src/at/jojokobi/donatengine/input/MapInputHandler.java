package at.jojokobi.donatengine.input;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import at.jojokobi.donatengine.util.Pair;

public class MapInputHandler implements InputHandler {

	private Input local;
	private Map<Long, Input> map;


	public MapInputHandler(Input local, Map<Long, Input> map) {
		super();
		this.local = local;
		this.map = map;
	}

	@Override
	public Input getInput(long clientId) {
		return clientId == SCENE_INPUT ? local : map.get(clientId);
	}
	
	@Override
	public List<Pair<Long, Input>> getInputs() {
		List<Pair<Long, Input>> inputs = new ArrayList<Pair<Long,Input>>();
		inputs.add(new Pair<Long, Input>(SCENE_INPUT, local));
		for (var e : map.entrySet()) {
			if (!e.getKey().equals(SCENE_INPUT)) {
				inputs.add(new Pair<>(e.getKey(), e.getValue()));
			}
		}
		return InputHandler.super.getInputs();
	}
	
}
