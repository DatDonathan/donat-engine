package at.jojokobi.donatengine;

import at.jojokobi.fxengine.audio.AudioSystem;
import at.jojokobi.fxengine.screens.Screen;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

/**
 * 
 * A game application with a dynamic game-loop
 * 
 * @author jojokobi
 *
 */
@Deprecated
public abstract class Game extends Application{
	
	private int fps = 30;
	private int frameDelay = 1000000000/fps;
	
	private int fpsCount = 0;
	private int currentFps = 0;
	private long lastTime = System.nanoTime();
	private long lastUpdate = System.nanoTime();
	
	private Stage stage;
	private Screen screen;
	private boolean running = true;
	
	private AudioSystem audioSystem = new AudioSystem();
	
	public Game() {
		
	}

	@Override
	public final void start(Stage stage) throws Exception {
		this.stage = stage;
		setTitle("A Game");
		Screen screen = start();
		setScreen(screen);
		stage.show();
		startLoop();
	}
	
	private void startLoop () {
		Thread loop = new Thread(new Runnable() {
			@Override
			public void run() {
				lastTime = System.nanoTime();
				lastUpdate = System.nanoTime();
				while (running) {
					//Calc Delta
					long time = System.nanoTime();
					double delta = (time - lastUpdate)/1000000000.0;
					lastUpdate = time;
					//Update
					calcFPS();
					update(delta);
					//Render
//					Platform.runLater(new Runnable() {
//						@Override
//						public void run() {
//							render();
//						}
//					});
					long delay = frameDelay - (System.nanoTime() - time);
					//Sleep
					try {
						Thread.sleep(Math.max(delay/1000000,0), Math.max((int)(delay%1000000),0));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		loop.start();
		AnimationTimer timer = new AnimationTimer() {
			@Override
			public void handle(long arg0) {
				render ();
			}
		};
		timer.start();
	}
	
	@Override
	public final void stop() throws Exception {
		super.stop();
		Platform.exit();
		running = false;
		System.out.println("Stop");
	}
	
	private void calcFPS () {
		if (System.nanoTime() >= lastTime + 1000000000) {
			currentFps = fpsCount;
			fpsCount = 0;
			lastTime = System.nanoTime();
		}
		else {
			fpsCount++;
		}
	}
	
	/**
	 * 
	 * Called when the game is updated.
	 * Calls screen.update(delta);
	 * 
	 * @param delta		The time since the last update in seconds
	 */
	protected void update (double delta) {
		screen.update(delta);
	}
	
	/**
	 * 
	 * Called when the screen is rendered.
	 * Calls screen.render();
	 * 
	 */
	protected void render () {
		screen.render();
	}
	
	/**
	 * 
	 * @return	The FPS - Cap the game should have
	 */
	public int getFPS () {
		return fps;
	}
	
	/**
	 * 
	 * Sets the FPS - Cap of the game
	 * 
	 * @param fps	The FPS - Cap
	 */
	public void setFPS (int fps) {
		this.fps = fps;
		this.frameDelay = 1000000000/fps;
	}
	
	/**
	 * 
	 * @return	The FPS in the last second
	 */
	public int getCurrentFPS () {
		return currentFps;
	}
	
	/**
	 * 
	 * Sets the title of the game window
	 * 
	 * @param title	The title to set
	 */
	public void setTitle (String title) {
		stage.setTitle(title);
	}
	
	/**
	 * 
	 * @return	The actual title of the game window
	 */
	public String getTitle () {
		return stage.getTitle();
	}
	
	/**
	 * 
	 * Called when the game is started do initialization logic here.
	 * 
	 * @return	The initial screen of the game
	 */
	public abstract Screen start ();

	
	/**
	 * 
	 * @return	The current screen of the game
	 */
	public Screen getScreen() {
		return screen;
	}

	/**
	 * 
	 * Sets the screen of the game
	 * 
	 * @param screen
	 */
	public void setScreen(Screen screen) {
		if (this.screen != null) {
			this.screen.end();
		}
		this.screen = screen;
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				stage.setScene(screen.getScene());
				screen.start();
			}
		});
	}

	/**
	 * 
	 * @return	The running state of the game
	 */
	public boolean isRunning() {
		return running;
	}

	/**
	 * 
	 * Sets the running state of the game
	 * 
	 * @param running	Sets the running state of the game
	 */
	public void setRunning(boolean running) {
		this.running = running;
	}

	/**
	 * 
	 * @return	The AudioSystem of the game
	 */
	public AudioSystem getAudioSystem() {
		return audioSystem;
	}

	public Stage getStage() {
		return stage;
	}

}
