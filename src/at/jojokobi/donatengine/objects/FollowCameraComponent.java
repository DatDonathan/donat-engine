package at.jojokobi.donatengine.objects;

import java.util.Arrays;
import java.util.List;

import at.jojokobi.donatengine.event.UpdateEvent;
import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.objects.properties.ObservableProperty;
import at.jojokobi.donatengine.rendering.RenderData;
import at.jojokobi.donatengine.util.Vector3D;

public class FollowCameraComponent implements ObjectComponent {
	
	private long clientId;
	private Vector3D offset;

	public FollowCameraComponent(Vector3D offset, long clientId) {
		super();
		this.offset = offset;
		this.clientId = clientId;
	}

	@Override
	public void update(GameObject object, Level level, UpdateEvent event) {
		if (level.getClientId() == clientId) {
			level.getCamera().setX(object.getX() + offset.getX());
			level.getCamera().setY(object.getY() + offset.getY());
			level.getCamera().setZ(object.getZ() + offset.getZ());
		}
	}

	@Override
	public void clientUpdate(GameObject object, Level level, UpdateEvent event) {
		
	}

	@Override
	public void hostUpdate(GameObject object, Level level, UpdateEvent event) {
		
	}

	@Override
	public void renderBefore(GameObject object, List<RenderData> data, Camera cam, Level level) {
		
	}

	@Override
	public void renderAfter(GameObject object, List<RenderData> data, Camera cam, Level level) {
		
	}

	@Override
	public List<ObservableProperty<?>> observableProperties() {
		return Arrays.asList();
	}

}
