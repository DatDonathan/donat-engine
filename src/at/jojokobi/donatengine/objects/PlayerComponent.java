package at.jojokobi.donatengine.objects;

import java.util.Arrays;
import java.util.List;

import at.jojokobi.donatengine.event.UpdateEvent;
import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.objects.properties.LongProperty;
import at.jojokobi.donatengine.objects.properties.ObservableProperty;
import at.jojokobi.donatengine.rendering.RenderData;

public class PlayerComponent implements ObjectComponent{
	
	private LongProperty client = new LongProperty(0);
	
	public PlayerComponent(long client) {
		super();
		this.client.set(client);
	}

	@Override
	public void hostUpdate(GameObject object, Level level, UpdateEvent event) {
		
	}

	public long getClient() {
		return client.get();
	}
	
	@Override
	public List<ObservableProperty<?>> observableProperties() {
		return Arrays.asList(client);
	}

	@Override
	public void update(GameObject object, Level level, UpdateEvent event) {
		
	}

	@Override
	public void clientUpdate(GameObject object, Level level, UpdateEvent event) {
		
	}

	@Override
	public void renderBefore(GameObject object, List<RenderData> data, Camera cam, Level level) {
		
	}

	@Override
	public void renderAfter(GameObject object, List<RenderData> data, Camera cam, Level level) {
		
	}

}
