package at.jojokobi.donatengine.leveleditor;

import at.jojokobi.donatengine.Game;
import at.jojokobi.donatengine.GameLogic;
import at.jojokobi.donatengine.event.UpdateEvent;
import at.jojokobi.donatengine.gui.DynamicGUIFactory;
import at.jojokobi.donatengine.gui.GUISystem;
import at.jojokobi.donatengine.gui.SimpleGUISystem;
import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.rendering.RenderScene;

//TODO Remove some possible side effects
public class LevelEditorLogic implements GameLogic {
	
	private GUISystem guiSystem;
	private Level level;

	public LevelEditorLogic(Level level) {
		super();
		this.level = level;
	}

	@Override
	public void start(Game game) {
		DynamicGUIFactory guiFactory = new DynamicGUIFactory();
		guiSystem = new SimpleGUISystem(guiFactory);
		level.init();
	}

	@Override
	public void update(double delta, Game game) {
		UpdateEvent event = new UpdateEvent(delta, c -> game.getLocalInput(), game);
		level.getCamera().update(event, level);
		//Process Actions
		for (var action : guiSystem.update(level, level.getCamera().getViewWidth(), level.getCamera().getViewHeight(), event)) {
			action.getValue().perform(level, game, action.getKey(), guiSystem);
		}
	}

	@Override
	public void render(RenderScene scene) {
		level.render(scene.getData(), true);
		scene.setCamera(level.getCamera());
		scene.setTileSystem(level.getTileSystem());
		scene.setAnimationTimer(level.getTimer());
	}

	@Override
	public void stop(Game game) {
		
	}

}
