package at.jojokobi.donatengine.level;

import java.util.List;

import at.jojokobi.donatengine.event.StartEvent;
import at.jojokobi.donatengine.event.UpdateEvent;
import at.jojokobi.donatengine.objects.Camera;
import at.jojokobi.donatengine.objects.properties.ObservableProperty;
import at.jojokobi.donatengine.rendering.RenderData;

public interface LevelComponent {
	
	public default void init (Level level, StartEvent event) {
		
	};
	
	public default void hostUpdate (Level level, UpdateEvent event) {
		
	};
	
	public default void update (Level level, UpdateEvent event) {
		
	};
	
	public default void clientUpdate (Level level, UpdateEvent event) {
		
	};
	
	public default void renderBefore (List<RenderData> data, Camera cam, Level level) {
		
	};
	
	public default void renderAfter (List<RenderData> data, Camera cam, Level level) {
		
	};
	
	public default void onConnectPlayer (Camera cam, Level level, long id) {
		
	}
	
	public List<ObservableProperty<?>> observableProperties ();

}
