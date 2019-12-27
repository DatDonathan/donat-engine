package at.jojokobi.donatengine.screens;

import at.jojokobi.fxengine.Game;
import at.jojokobi.fxengine.audio.AudioSystem;
import at.jojokobi.fxengine.input.Input;
import at.jojokobi.fxengine.level.Level;
import at.jojokobi.fxengine.level.LevelHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.BorderPane;
@Deprecated
public class LevelScreen extends LevelViewScreen {
	
	public LevelScreen(Game game, double width, double height) {
		super(game);
		initScene();
	}
	
	@Override
	public void start() {
		super.start();
		getLevel().start();
	}

	@Override
	public synchronized void update(double delta) {
		super.update(delta);
		if (getLevel() != null) {
			getLevel().update(delta, new LevelHandler() {
				
				@Override
				public AudioSystem getAudioSystem(long clientId) {
					return getGame().getAudioSystem();
				}
				
				@Override
				public Input getInput(long clientId) {
					return LevelScreen.this.getInput();
				}
				
				@Override
				public void changeLevel(Level level) {
					setLevel(level);
				}
			});
		}
	}

	@Override
	protected Scene createScene(Canvas canvas) {
		Scene scene = new Scene(new BorderPane(canvas));
		return scene;
	}
	
}
