package at.jojokobi.donatengine;

import java.util.Map;

import at.jojokobi.donatengine.audio.AudioSystem;
import at.jojokobi.donatengine.input.Axis;
import at.jojokobi.donatengine.input.SceneInput;
import at.jojokobi.donatengine.objects.Camera;
import at.jojokobi.donatengine.presence.GamePresenceHandler;
import at.jojokobi.donatengine.ressources.IRessourceHandler;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class GameView implements Loopable {
	
	private Stage stage;
	private Canvas canvas;
	private boolean running = true;
	private GameLogic logic;
	private SceneInput input;
	private AudioSystem audioSystem = new AudioSystem();
	private IRessourceHandler ressourceHandler;
	private GamePresenceHandler gamePresenceHandler = new GamePresenceHandler();
	private Camera camera;

	public GameView(Stage stage, GameLogic logic, Camera camera, IRessourceHandler ressourceHandler, Map <String, KeyCode> keyBindings, Map<String, MouseButton> mouseButtonBindings,
			Map<String, Axis> axisBindings) {
		super();
		this.stage = stage;
		this.logic = logic;
		this.ressourceHandler = ressourceHandler;
		this.canvas = new Canvas(camera.getViewWidth(), camera.getViewHeight());
		this.camera = camera;
		this.input = new SceneInput(keyBindings, mouseButtonBindings, axisBindings);
	}

	long lastTime = System.nanoTime();
	long lastUpdate = System.nanoTime();
	int fpsCount = 0;
	
	public void setTitle (String title) {
		stage.setTitle(title);
	}
	
	@Override
	public void start() {
		Platform.runLater(() -> {
			Scene scene = new Scene(new BorderPane(canvas));
			input.registerEvents(scene);
			stage.setScene(scene);
			stage.show();
		});
		gamePresenceHandler.init();
		logic.start(camera);
		AnimationTimer timer = new AnimationTimer () {
			@Override
			public void handle(long time) {
				canvas.setWidth(stage.getWidth());
				canvas.setHeight(stage.getHeight());
				lastUpdate = time;
				// Calc FPS
				if (System.nanoTime() >= lastTime + 1000000000) {
					System.out.println("FPS: " + fpsCount);
					fpsCount = 0;
					lastTime = System.nanoTime();
				} else {
					fpsCount++;
				}
				GraphicsContext ctx = canvas.getGraphicsContext2D();
				logic.render(ctx, camera, ressourceHandler);
			}
		};
		timer.start();
	}

	@Override
	public void update(double delta) {
		try {
			if (!logic.isRunning()) {
				stopGame();
			}
			if (stage.getScene() != null) {
				camera.setViewWidth(stage.getScene().getWidth());
				camera.setViewHeight(stage.getScene().getHeight());
			}
			logic.update(delta, camera, this::changeLogic, input, id -> audioSystem, ressourceHandler, gamePresenceHandler);
			input.updateBuffers();
		}
		catch (Exception e) {
			e.printStackTrace();
			running = false;
		}
	}

	@Override
	public void stop() {
		System.out.println("Stop view");
		logic.onStop();
		gamePresenceHandler.end();
		Platform.exit();
	}
	
	public void changeLogic (GameLogic logic) {
		this.logic.onStop();
		this.logic = logic;
		logic.start(camera);
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
	
}
