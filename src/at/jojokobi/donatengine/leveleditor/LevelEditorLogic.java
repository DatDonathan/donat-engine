package at.jojokobi.donatengine.leveleditor;

import java.util.List;

import at.jojokobi.donatengine.Game;
import at.jojokobi.donatengine.GameLogic;
import at.jojokobi.donatengine.event.UpdateEvent;
import at.jojokobi.donatengine.gui.DynamicGUIFactory;
import at.jojokobi.donatengine.gui.GUISystem;
import at.jojokobi.donatengine.gui.SimpleGUI;
import at.jojokobi.donatengine.gui.SimpleGUISystem;
import at.jojokobi.donatengine.gui.SimpleGUIType;
import at.jojokobi.donatengine.gui.nodes.HFlowBox;
import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.rendering.RenderScene;

//TODO Remove some possible side effects
public class LevelEditorLogic implements GameLogic {
	
	private static final String LEVEL_EDITOR_GUI = "level_editor_gui";
	
	private GUISystem guiSystem;
	private Level level;
	private double animationTimer = 0;
	private List<LevelEditorEntry> entries;

	public LevelEditorLogic(Level level, List<LevelEditorEntry> entries) {
		super();
		this.level = level;
		this.entries = entries;
	}

	@Override
	public void start(Game game) {
		DynamicGUIFactory guiFactory = new DynamicGUIFactory();
		guiFactory.registerGUI(LEVEL_EDITOR_GUI, new SimpleGUIType<Object> (Object.class, (data, client) -> {
			HFlowBox box = new HFlowBox();
			
			for (LevelEditorEntry entry : entries) {
				//TODO create buttons
				entry.toString();
			}
			
			return new SimpleGUI(box, LEVEL_EDITOR_GUI, data, client);
		}));
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
		animationTimer += delta;
	}

	@Override
	public void render(RenderScene scene) {
		level.render(scene.getData(), true);
		scene.setCamera(level.getCamera());
		scene.setTileSystem(level.getTileSystem());
		scene.setAnimationTimer(animationTimer);
	}

	@Override
	public void stop(Game game) {
		
	}

}
