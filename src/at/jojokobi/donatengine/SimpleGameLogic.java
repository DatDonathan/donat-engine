package at.jojokobi.donatengine;

import java.util.List;

import at.jojokobi.donatengine.event.StartEvent;
import at.jojokobi.donatengine.event.StopEvent;
import at.jojokobi.donatengine.event.UpdateEvent;
import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.objects.Camera;
import at.jojokobi.donatengine.rendering.RenderData;
public class SimpleGameLogic implements GameLogic{
	
	private Level level;

	public SimpleGameLogic(Level level) {
		super();
		this.level = level;
	}

	@Override
	public void start(Game game) {
		level.clear();
		level.start(new StartEvent(c -> game.getLocalInput(), game));
	}

	@Override
	public void update(double delta, Game game) {
		level.update(new UpdateEvent(delta, c -> game.getLocalInput(), game));
	}
	
	@Override
	public void stop(Game game) {
		level.stop(new StopEvent(c -> game.getLocalInput(), game));
	}
	
	@Override
	public void render(List<RenderData> data) {
		level.render(data, false);
	}
	
	@Override
	public Camera getCamera() {
		return level.getCamera();
	}
	
}
