package at.jojokobi.donatengine.objects;

import java.util.Arrays;
import java.util.List;

import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.level.LevelHandler;
import at.jojokobi.donatengine.objects.properties.LongProperty;
import at.jojokobi.donatengine.objects.properties.ObservableProperty;
import javafx.scene.canvas.GraphicsContext;

public class PlayerComponent implements ObjectComponent{
	
	private LongProperty client = new LongProperty(0);
	
	public PlayerComponent(long client) {
		super();
		this.client.set(client);
	}

	@Override
	public void hostUpdate(GameObject object, Level level, LevelHandler handler, Camera camera, double delta) {
		
	}

	public long getClient() {
		return client.get();
	}
	
	@Override
	public List<ObservableProperty<?>> observableProperties() {
		return Arrays.asList(client);
	}

	@Override
	public void renderBefore(GameObject object, GraphicsContext ctx, Camera cam, Level level) {
		
	}

	@Override
	public void renderAfter(GameObject object, GraphicsContext ctx, Camera cam, Level level) {
		
	}

	@Override
	public void update(GameObject object, Level level, LevelHandler handler, Camera camera, double delta) {
		
	}

	@Override
	public void clientUpdate(GameObject object, Level level, LevelHandler handler, Camera camera, double delta) {
		
	}

}
