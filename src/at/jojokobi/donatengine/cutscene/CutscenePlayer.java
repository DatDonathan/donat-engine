package at.jojokobi.donatengine.cutscene;

import at.jojokobi.donatengine.level.Level;

public class CutscenePlayer {
	
	private Cutscene currectCutscene;
	
	public void play (Cutscene cutscene, Level level) {
		if (this.currectCutscene == null) {
			currectCutscene = cutscene;
			playAction(cutscene, level, 0);
		}
	}
	
	private void playAction (Cutscene cutscene, Level level, int index) {
		if (index < cutscene.getActions().size()) {
			cutscene.getActions().get(index).execute(level).setOnFinish(() -> playAction(cutscene, level, index + 1));
		}
		else {
			currectCutscene = null;
		}
	}

}
