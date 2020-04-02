package at.jojokobi.donatengine.objects.path;

import java.util.Iterator;

import at.jojokobi.donatengine.util.Vector3D;

public class PathMovement {
	
	private Iterator<Vector3D> path;
	private Vector3D goal;
	
	public PathMovement(Path path) {
		this.path = path.getVelocities().iterator();
	}
	
	public void next (Vector3D pos) {
		if (path.hasNext()) {
			goal = pos.clone().add(path.next());
		}
		else {
			goal = null;
		}
	}
	
	public Vector3D getGoal () {
		return goal;
	}
	
	public boolean isFinished () {
		return path == null;
	}

}
