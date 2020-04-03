package at.jojokobi.donatengine.objects.path;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import at.jojokobi.donatengine.util.Vector3D;

public class PathMovement {
	
	private Iterator<Vector3D> path;
	private Vector3D start;
	private Vector3D goal;
	private List<Runnable> finishListeners = new ArrayList<>();
	
	public PathMovement(Path path) {
		this.path = path.getVelocities().iterator();
	}
	
	public void next (Vector3D pos) {
		if (path.hasNext()) {
			start = pos;
			goal = pos.clone().add(path.next());
		}
		else {
			start = null;
			goal = null;
		}
	}
	
	public Vector3D getGoal () {
		return goal;
	}
	
	public Vector3D getStart() {
		return start;
	}

	public boolean isFinished () {
		return goal == null;
	}
	
	public void addListener (Runnable runnable) {
		finishListeners.add(runnable);
	}

}
