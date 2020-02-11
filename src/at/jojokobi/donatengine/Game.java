package at.jojokobi.donatengine;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import at.jojokobi.donatengine.audio.AudioSystem;
import at.jojokobi.donatengine.input.Input;
import at.jojokobi.donatengine.presence.GamePresenceHandler;
import at.jojokobi.donatengine.rendering.GameView;
import at.jojokobi.donatengine.rendering.RenderData;
import at.jojokobi.donatengine.serialization.SerializationWrapper;

public class Game implements Loopable {
	
	private AtomicBoolean running;
	private GameLogic logic;
	private Input localInput;
	private AudioSystem audioSystem;
	private GamePresenceHandler gamePresenceHandler = new GamePresenceHandler();
	private SerializationWrapper serialization;
	private GameView gameView;
	
	
	public Game(boolean running, GameLogic logic, Input localInput, AudioSystem audioSystem,
			GamePresenceHandler gamePresenceHandler, SerializationWrapper serialization, GameView gameView) {
		super();
		this.running = new AtomicBoolean(running);
		this.logic = logic;
		this.localInput = localInput;
		this.audioSystem = audioSystem;
		this.gamePresenceHandler = gamePresenceHandler;
		this.serialization = serialization;
		this.gameView = gameView;
	}

	@Override
	public void start() {
		gamePresenceHandler.init();
		logic.start(this);
	}

	@Override
	public void update(double delta) {
		try {
			logic.update(delta, this);
			localInput.updateBuffers();
			List<RenderData> data = new LinkedList<>();
			gameView.render(data);
		}
		catch (Exception e) {
			e.printStackTrace();
			stopGame();
		}
	}

	@Override
	public void stop() {
		System.out.println("Stopping game");
		logic.stop(this);
		gamePresenceHandler.end();
	}
	
	public void changeLogic (GameLogic logic) {
		this.logic.stop(this);
		this.logic = logic;
		logic.start(this);
	}
	
	public void stopGame () {
		running.set(false);
	}

	@Override
	public boolean isRunning() {
		return running.get();
	}

	public GamePresenceHandler getGamePresenceHandler() {
		return gamePresenceHandler;
	}

	public SerializationWrapper getSerialization() {
		return serialization;
	}

	public Input getLocalInput() {
		return localInput;
	}

	public AudioSystem getAudioSystem() {
		return audioSystem;
	}

	public GameView getGameView() {
		return gameView;
	}
	
}
