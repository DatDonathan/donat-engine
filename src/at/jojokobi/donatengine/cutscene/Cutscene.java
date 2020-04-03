package at.jojokobi.donatengine.cutscene;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Cutscene {
	
	private List<CutsceneAction> actions = new ArrayList<CutsceneAction>();
	
	public Cutscene(CutsceneAction... actions) {
		this.actions.addAll(Arrays.asList(actions));
	}

	public List<CutsceneAction> getActions() {
		return actions;
	}

}
