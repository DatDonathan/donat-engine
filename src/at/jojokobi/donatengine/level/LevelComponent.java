package at.jojokobi.donatengine.level;

import java.util.List;

import at.jojokobi.donatengine.objects.Camera;
import at.jojokobi.donatengine.objects.properties.ObservableProperty;
import javafx.scene.canvas.GraphicsContext;

public interface LevelComponent {
	
	public void update (Level level, double delta);
	
	public void renderBefore (GraphicsContext ctx, Camera cam, Level level);
	
	public void renderAfter (GraphicsContext ctx, Camera cam, Level level);
	
	public default void onConnectPlayer (Camera cam, Level level, long id) {
		
	}
	
	public List<ObservableProperty<?>> observableProperties ();

}
