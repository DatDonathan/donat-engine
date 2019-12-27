package at.jojokobi.donatengine.rendering;

import at.jojokobi.donatengine.util.Vector2D;
import at.jojokobi.donatengine.util.Vector3D;

public interface Perspective {

	public Vector2D toScreenPosition (Vector3D pos, Vector3D rotation);

}
