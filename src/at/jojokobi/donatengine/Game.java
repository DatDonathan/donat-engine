package at.jojokobi.donatengine;

import at.jojokobi.donatengine.audio.AudioSystem;
import at.jojokobi.donatengine.input.Input;
import at.jojokobi.donatengine.objects.Camera;
import at.jojokobi.donatengine.presence.GamePresenceHandler;
import at.jojokobi.donatengine.ressources.IRessourceHandler;
import at.jojokobi.donatengine.serialization.SerializationWrapper;

public class Game implements Loopable {
	
	private boolean running = true;
	private GameLogic logic;
	private Input localInput;
	private AudioSystem audioSystem;
	private GamePresenceHandler gamePresenceHandler = new GamePresenceHandler();
	private SerializationWrapper serialization;
	private Camera camera;
	
	long lastTime = System.nanoTime();
	long lastUpdate = System.nanoTime();
	int fpsCount = 0;

	public Game(GameLogic logic, Camera camera) {
		super();
		this.logic = logic;
		this.camera = camera;
	}
	
	@Override
	public void start() {
		gamePresenceHandler.init();
		logic.start(camera, this);
	}

	@Override
	public void update(double delta) {
		try {
			logic.update(delta, camera, this);
			localInput.updateBuffers();
		}
		catch (Exception e) {
			e.printStackTrace();
			running = false;
		}
	}

	@Override
	public void stop() {
		System.out.println("Stop view");
		logic.stop(camera, this);
		gamePresenceHandler.end();
		
	}
	
	public void changeLogic (GameLogic logic) {
		this.logic.stop(camera, this);
		this.logic = logic;
		logic.start(camera, this);
	}
	
	public void stopGame () {
		running = false;
	}

	@Override
	public boolean isRunning() {
		return running;
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
	
}
