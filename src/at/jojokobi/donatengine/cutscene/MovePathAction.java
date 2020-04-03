package at.jojokobi.donatengine.cutscene;


import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.objects.GameObject;
import at.jojokobi.donatengine.objects.path.Path;
import at.jojokobi.donatengine.objects.path.PathmoverComponent;

public class MovePathAction implements CutsceneAction {
	
	private String tag;
	private Path path;

	public MovePathAction(String tag, Path path) {
		super();
		this.tag = tag;
		this.path = path;
	}

	@Override
	public ActionProgress execute(Level level) {
		GameObject obj = level.getObjectWithTag(tag);
		PathmoverComponent comp;
		ActionProgressImpl prog = new ActionProgressImpl();
		if (obj != null && (comp = obj.getComponent(PathmoverComponent.class)) != null) {
			comp.move(obj, path).addListener(() -> prog.finish());
		}
		else {
			prog.finish();
		}
		
		return prog;
	}

}
