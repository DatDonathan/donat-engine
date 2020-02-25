package at.jojokobi.donatengine.objects;

import java.util.Arrays;
import java.util.List;

import at.jojokobi.donatengine.event.UpdateEvent;
import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.objects.properties.ObservableProperty;
import at.jojokobi.donatengine.rendering.RenderData;

public class FollowCameraComponent implements ObjectComponent {
	
	private long clientId;
	private double maxBorderDst;

	public FollowCameraComponent(long clientId, double maxBorderDst) {
		super();
		this.clientId = clientId;
		this.maxBorderDst = maxBorderDst;
	}

	@Override
	public void update(GameObject object, Level level, UpdateEvent event) {
		if (level.getClientId() == clientId) {
			event.getGame().getGameView().getCameraHandler().doCameraFollow(object, level, level.getCamera(), maxBorderDst);
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
