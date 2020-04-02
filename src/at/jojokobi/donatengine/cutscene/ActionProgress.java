package at.jojokobi.donatengine.cutscene;

public interface ActionProgress {
	
	public boolean isFinished ();
	
	public void setOnFinish (Runnable runnable);

}
