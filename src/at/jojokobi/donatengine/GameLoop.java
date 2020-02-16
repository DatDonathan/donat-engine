package at.jojokobi.donatengine;

public class GameLoop implements Runnable {

	private int fps = 60;
	private Loopable loopable;
	private int currentFps = 0;

	public GameLoop(int fps, Loopable loopable) {
		super();
		this.fps = fps;
		this.loopable = loopable;
	}

	public void start() {
		loopable.start();
		int frameDelay = 1000000000 / fps;
		long lastTime = System.nanoTime();
		long lastUpdate = System.nanoTime();
		int fpsCount = 0;
		while (loopable.isRunning()) {
			// Calc Delta
			long time = System.nanoTime();
			double delta = (time - lastUpdate) / 1000000000.0;
			lastUpdate = time;
			// Calc FPS
			if (System.nanoTime() >= lastTime + 1000000000) {
				currentFps = fpsCount;
				System.out.println(currentFps);
				fpsCount = 0;
				lastTime = System.nanoTime();
			} else {
				fpsCount++;
			}
			// Update
			loopable.update(delta);
			long delay = frameDelay - (System.nanoTime() - time);
			// Sleep
			try {
				Thread.sleep(Math.max(delay / 1000000, 0), Math.max((int) (delay % 1000000), 0));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		loopable.stop();
	}

	public int getFps() {
		return fps;
	}

	public int getCurrentFps() {
		return currentFps;
	}

	@Override
	public void run() {
		start();
	}

}
