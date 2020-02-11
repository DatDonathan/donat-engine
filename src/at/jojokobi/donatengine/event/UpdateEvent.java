package at.jojokobi.donatengine.event;

import at.jojokobi.donatengine.Game;
import at.jojokobi.donatengine.input.InputHandler;

public class UpdateEvent {
	
	private double delta;
	private InputHandler input;
	private Game game;
	
	
	public UpdateEvent(double delta, InputHandler input, Game game) {
		super();
		this.delta = delta;
		this.input = input;
		this.game = game;
	}
	public double getDelta() {
		return delta;
	}
	public InputHandler getInput() {
		return input;
	}
	public Game getGame() {
		return game;
	}

}
