package at.jojokobi.donatengine.rendering;

import java.util.List;

import at.jojokobi.donatengine.objects.Camera;
import at.jojokobi.donatengine.util.Vector2D;

public interface GameView {
	
	public void render (List<RenderData> data, Camera cam);
	
	public void setTitle (String title);
	
	public String getTitle ();
	
	public Vector2D getSize ();
	
	public void setSize (Vector2D size);
	
	public CameraHandler getCameraHandler ();

}
