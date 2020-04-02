package at.jojokobi.donatengine.cutscene;

import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.objects.GameObject;

public class MovePathAction implements CutsceneAction {
	
	private String tag;

	@Override
	public ActionProgress execute(Level level) {
		GameObject obj = level.getObjectWithTag(tag);
		if (obj != null) {
			
		}
		ActionProgress prog = new ActionProgress() {
			
			private Runnable runnable;
			
			@Override
			public void setOnFinish(Runnable runnable) {
				this.runnable = runnable;
			}
			
			@Override
			public boolean isFinished() {
				return false;
			}
		};
		
		return prog;
	}

}
