package at.jojokobi.donatengine.objects.path;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import at.jojokobi.donatengine.util.Vector3D;

public class Path {
	
	private List<Vector3D> velocities = new ArrayList<>();
	
	

	public Path(Vector3D... velocities) {
		super();
		this.velocities.addAll(Arrays.asList(velocities));
	}



	public List<Vector3D> getVelocities() {
		return velocities;
	}

}
