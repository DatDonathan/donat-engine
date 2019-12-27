package at.jojokobi.donatengine.rendering;

import at.jojokobi.donatengine.util.Vector2D;
import at.jojokobi.donatengine.util.Vector3D;

public class TwoDimensionalPerspective implements Perspective{

	@Override
	public Vector2D toScreenPosition(Vector3D pos, Vector3D rotation) {
		return (rotation.getX() % 90 > 45) ? new Vector2D(pos.getX(), pos.getZ()) : new Vector2D(pos.getX(), pos.getY());
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName();
	}

}
