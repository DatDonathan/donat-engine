package at.jojokobi.donatengine.screens;

import at.jojokobi.fxengine.Game;
import at.jojokobi.fxengine.input.Input;
import at.jojokobi.fxengine.input.SceneInput;
import javafx.scene.Scene;

@Deprecated
public abstract class Screen {
	
	private Game game;
	private Scene scene;
	private SceneInput input = new SceneInput();
	
	public Screen(Game game) {
		this.game = game;
	}
	
	public abstract void update (double delta);
	
	public abstract void render ();

	public void start () {
		
	}
	
	public void end () {
		input.reset();
	}
	
	public Scene getScene() {
		return scene;
	}

	protected void setScene(Scene scene) {
		if (this.scene != null) {
			this.scene.setOnKeyPressed(null);
			this.scene.setOnKeyReleased(null);
		}
		this.scene = scene;
		input.registerEvents(scene);
	}

	public Game getGame() {
		return game;
	}

	public Input getInput() {
		return input;
	}

}
