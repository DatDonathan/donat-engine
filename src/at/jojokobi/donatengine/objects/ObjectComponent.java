package at.jojokobi.donatengine.objects;

import java.util.List;

import at.jojokobi.donatengine.event.UpdateEvent;
import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.objects.properties.ObservableProperty;
import at.jojokobi.donatengine.rendering.RenderData;

public interface ObjectComponent {
	
	public default void onSpawn (GameObject object, Level level) {
		
	}
	
	public void update (GameObject object, Level level, UpdateEvent event);

	public void clientUpdate (GameObject object, Level level, UpdateEvent event);

	public void hostUpdate (GameObject object, Level level, UpdateEvent event);
	
	public void renderBefore (GameObject object, List<RenderData> data, Camera cam, Level level);
	
	public void renderAfter (GameObject object, List<RenderData> data, Camera cam, Level level);
	
	public List<ObservableProperty<?>> observableProperties ();
	
}
