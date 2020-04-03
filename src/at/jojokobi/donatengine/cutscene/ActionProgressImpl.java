package at.jojokobi.donatengine.cutscene;

public class ActionProgressImpl implements ActionProgress {
	
	private Runnable listener;
	private boolean finished = false;

	@Override
	public boolean isFinished() {
		return finished;
	}
	
	public void finish () {
		finished = false;
		if (listener != null) {
			listener.run();
		}
	}

	@Override
	public void setOnFinish(Runnable runnable) {
		this.listener = runnable;
		if (finished && listener != null) {
			listener.run();
		}
	}

}
