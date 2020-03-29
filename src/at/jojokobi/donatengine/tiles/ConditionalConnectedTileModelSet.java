package at.jojokobi.donatengine.tiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import at.jojokobi.donatengine.util.Pair;

public class ConditionalConnectedTileModelSet implements ConnectedTileModelSet {
	
	private String model;
	private List<Pair<Predicate<Set<Direction>>, String>> models = new ArrayList<>();
	
	@SafeVarargs
	public ConditionalConnectedTileModelSet(String model, Pair<Predicate<Set<Direction>>, String>... models) {
		this.model = model;
		for (Pair<Predicate<Set<Direction>>, String> pair : models) {
			this.models.add(pair);
		}
	}

	@Override
	public String getModel(Set<Direction> sides) {
		for (Pair<Predicate<Set<Direction>>, String> cond : models) {
			if (cond.getKey().test(sides)) {
				return cond.getValue();
			}
		}
		return model;
	}

}
