package at.jojokobi.donatengine.event;

import at.jojokobi.donatengine.Game;
import at.jojokobi.donatengine.input.InputHandler;

public class StopEvent {
	
	private InputHandler input;
	private Game game;
	
	public StopEvent(InputHandler input, Game game) {
		super();
		this.input = input;
		this.game = game;
	}
	public InputHandler getInput() {
		return input;
	}
	public Game getGame() {
		return game;
	}

}
