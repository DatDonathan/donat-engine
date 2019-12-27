package at.jojokobi.donatengine.objects;

import java.util.List;

import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.level.LevelHandler;
import at.jojokobi.donatengine.objects.properties.ObservableProperty;
import javafx.scene.canvas.GraphicsContext;

public interface ObjectComponent {
	
	public default void onSpawn (GameObject object, Level level) {
		
	}
	
	public void update (GameObject object, Level level, LevelHandler handler, Camera camera, double delta);

	public void clientUpdate (GameObject object, Level level, LevelHandler handler, Camera camera, double delta);

	public void hostUpdate (GameObject object, Level level, LevelHandler handler, Camera camera, double delta);
	
	public void renderBefore (GameObject object, GraphicsContext ctx, Camera cam, Level level);
	
	public void renderAfter (GameObject object, GraphicsContext ctx, Camera cam, Level level);
	
	public List<ObservableProperty<?>> observableProperties ();
	
}
