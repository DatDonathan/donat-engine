package at.jojokobi.donatengine;

import at.jojokobi.donatengine.event.StartEvent;
import at.jojokobi.donatengine.event.StopEvent;
import at.jojokobi.donatengine.event.UpdateEvent;
import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.rendering.RenderScene;

public class SimpleGameLogic implements GameLogic{
	
	private Level level;

	public SimpleGameLogic(Level level) {
		super();
		this.level = level;
	}

	@Override
	public void start(Game game) {
		level.clear();
		level.init();
		level.start(new StartEvent(c -> game.getLocalInput(), game));
		game.getGameView().bind(level.getTileSystem());
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
	public void render(RenderScene scene) {
		level.render(scene.getData(), false);
		scene.setCamera(level.getCamera());
		scene.setTileSystem(level.getTileSystem());
		scene.setAnimationTimer(level.getTimer());
	}
	
}
