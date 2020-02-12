package at.jojokobi.donatengine.engine;

import at.jojokobi.donatengine.audio.AudioSystem;
import at.jojokobi.donatengine.input.Input;
import at.jojokobi.donatengine.rendering.GameView;

public interface IGameEngine {
	
	public FontSystem getFontSystem ();
	
	public AudioSystem createAudioSystem ();
	
	public GameView createView ();
	
	public Input createInput ();

}
