package at.jojokobi.donatengine.rendering;

import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.objects.Camera;
import at.jojokobi.donatengine.objects.GameObject;

public interface CameraHandler {
	
	public void doCameraFollow (GameObject follow, Level level, Camera cam, double maxBorderDst);

}
