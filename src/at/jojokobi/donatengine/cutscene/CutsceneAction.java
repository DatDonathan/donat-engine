package at.jojokobi.donatengine.cutscene;

import at.jojokobi.donatengine.level.Level;

public interface CutsceneAction {
	
	public ActionProgress execute (Level level);

}
