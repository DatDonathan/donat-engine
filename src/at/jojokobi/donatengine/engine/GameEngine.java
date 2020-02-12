package at.jojokobi.donatengine.engine;

import at.jojokobi.donatengine.audio.AudioSystem;
import at.jojokobi.donatengine.input.Input;
import at.jojokobi.donatengine.rendering.GameView;

public class GameEngine {
	
	private static IGameEngine instance;
	
	public static void initialize (IGameEngine engine) {
		if (instance == null) {
			instance = engine;
		}
	}
	
	public static FontSystem getFontSystem () {
		return instance.getFontSystem();
	}
	
	public static AudioSystem createAudioSystem () {
		return instance.createAudioSystem();
	}
	
	public static GameView createView () {
		return instance.createView();
	}
	
	public static Input createInput () {
		return instance.createInput();
	}

}
