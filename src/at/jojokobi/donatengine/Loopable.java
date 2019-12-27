package at.jojokobi.donatengine;

public interface Loopable {
	
	public void start ();

	public void update (double delta);
	
	public void stop ();
	
	public boolean isRunning ();
	
}
