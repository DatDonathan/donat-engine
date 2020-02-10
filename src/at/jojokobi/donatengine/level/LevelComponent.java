package at.jojokobi.donatengine.level;

import java.util.List;

import at.jojokobi.donatengine.objects.Camera;
import at.jojokobi.donatengine.objects.properties.ObservableProperty;
import at.jojokobi.donatengine.rendering.RenderData;

public interface LevelComponent {
	
	public void init (Level level, LevelHandler handler);
	
	public void hostUpdate (Level level, LevelHandler handler, Camera cam, double delta);
	
	public void update (Level level, LevelHandler handler, Camera cam, double delta);
	
	public void clientUpdate (Level level, LevelHandler handler, Camera cam, double delta);
	
	public void renderBefore (List<RenderData> data, Camera cam, Level level);
	
	public void renderAfter (List<RenderData> data, Camera cam, Level level);
	
	public default void onConnectPlayer (Camera cam, Level level, long id) {
		
	}
	
	public List<ObservableProperty<?>> observableProperties ();

}
