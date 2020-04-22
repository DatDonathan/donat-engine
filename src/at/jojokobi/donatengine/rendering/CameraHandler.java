package at.jojokobi.donatengine.rendering;

import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.objects.Camera;
import at.jojokobi.donatengine.objects.GameObject;
import at.jojokobi.donatengine.util.Vector2D;
import at.jojokobi.donatengine.util.Vector3D;

public interface CameraHandler {
	
	public void doCameraFollow (GameObject follow, Level level, Camera cam, double maxBorderDst);
	
	public Vector2D getScreenPosition (Vector3D pos, Camera cam);

}
