package at.jojokobi.donatengine.tiles;

import java.util.Map;
import java.util.Set;

public class ConnectedTileModelMap implements ConnectedTileModelSet{
	
	private String defaultModel;
	private Map<Set<Direction>, String> models;
	
	public ConnectedTileModelMap(String defaultModel, Map<Set<Direction>, String> models) {
		super();
		this.defaultModel = defaultModel;
		this.models = models;
	}
	
	public String getModel (Set<Direction> sides) {
		return models.getOrDefault(sides, defaultModel);
	}

}
