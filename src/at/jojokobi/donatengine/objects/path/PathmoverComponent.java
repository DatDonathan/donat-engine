package at.jojokobi.donatengine.objects.path;

import java.util.ArrayList;
import java.util.List;

import at.jojokobi.donatengine.event.UpdateEvent;
import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.objects.Camera;
import at.jojokobi.donatengine.objects.GameObject;
import at.jojokobi.donatengine.objects.ObjectComponent;
import at.jojokobi.donatengine.objects.properties.ObservableProperty;
import at.jojokobi.donatengine.rendering.RenderData;
import at.jojokobi.donatengine.util.Vector3D;

public class PathmoverComponent implements ObjectComponent {

	private double speed;
	private PathMovement movement;

	public PathmoverComponent(double speed) {
		super();
		this.speed = speed;
	}

	public PathMovement move(GameObject obj, Path path) {
		movement = new PathMovement(path);
		movement.next(obj.getPositionVector());
		return movement;
	}

	@Override
	public void update(GameObject object, Level level, UpdateEvent event) {

	}

	@Override
	public void clientUpdate(GameObject object, Level level, UpdateEvent event) {

	}

	@Override
	public void hostUpdate(GameObject object, Level level, UpdateEvent event) {
		if (movement != null) {
			if (!movement.isFinished()) {
				Vector3D pos = object.getPositionVector();
				if (pos.equals(movement.getGoal()) || (((pos.getX() < movement.getGoal()
						.getX()) != (movement.getStart().getX() < movement.getGoal().getX())
						|| ((pos.getY() < movement.getGoal()
								.getY()) != (movement.getStart().getY() < movement.getGoal().getY())
								|| ((pos.getZ() < movement.getGoal()
										.getZ()) != (movement.getStart().getZ() < movement.getGoal().getZ())))))) {
					object.setX(movement.getGoal().getX());
					object.setY(movement.getGoal().getY());
					object.setZ(movement.getGoal().getZ());
					
					movement.next(pos);
				}
				if (!movement.isFinished()) {
					Vector3D motion = movement.getGoal().clone().subtract(pos);
					motion.normalize().multiply(speed);
				}
			}
			else {
				movement = null;
				object.setX(0);
				object.setY(0);
				object.setZ(0);
			}
		}
	}

	@Override
	public void renderBefore(GameObject object, List<RenderData> data, Camera cam, Level level) {

	}

	@Override
	public void renderAfter(GameObject object, List<RenderData> data, Camera cam, Level level) {

	}

	@Override
	public List<ObservableProperty<?>> observableProperties() {
		return new ArrayList<ObservableProperty<?>>();
	}

}
