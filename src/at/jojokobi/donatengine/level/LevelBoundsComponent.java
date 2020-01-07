package at.jojokobi.donatengine.level;

import java.util.Arrays;
import java.util.List;

import at.jojokobi.donatengine.objects.Camera;
import at.jojokobi.donatengine.objects.GameObject;
import at.jojokobi.donatengine.objects.properties.ObjectProperty;
import at.jojokobi.donatengine.objects.properties.ObservableProperty;
import at.jojokobi.donatengine.util.Vector3D;
import javafx.scene.canvas.GraphicsContext;

public class LevelBoundsComponent implements LevelComponent {
	
	private ObjectProperty<Vector3D> pos = new ObjectProperty<Vector3D>(null);
	private ObjectProperty<Vector3D> size = new ObjectProperty<Vector3D>(null);
	
	private boolean blockObjects;


	public LevelBoundsComponent(Vector3D pos, Vector3D size, boolean blockObjects) {
		super();
		this.pos.set(pos);
		this.size.set(size);
		this.blockObjects = blockObjects;
	}

	@Override
	public void init(Level level, LevelHandler handler) {
		
	}
	
	@Override
	public void update(Level level, LevelHandler handler, Camera cam, double delta) {
		Vector3D pos = this.pos.get();
		Vector3D size = this.size.get();
		if (blockObjects && level.getBehavior().isHost()) {
			for (GameObject obj : level.getObjects()) {
				if (obj.getX() < pos.getX()) {
					obj.setX(pos.getX(), false);
				}
				if (obj.getY() < pos.getY()) {
					obj.setY(pos.getY(), false);
				}
				if (obj.getZ() < pos.getZ()) {
					obj.setZ(pos.getZ(), false);
				}
				if (obj.getX() + obj.getWidth() > pos.getX() + size.getX()) {
					obj.setX(pos.getX() + size.getX() - obj.getWidth(), false);
				}
				if (obj.getY() + obj.getHeight() > pos.getY() + size.getY()) {
					obj.setY(pos.getY() + size.getY() - obj.getHeight(), false);
				}
				if (obj.getZ() + obj.getLength() > pos.getZ() + size.getZ()) {
					obj.setZ(pos.getZ() + size.getZ() - obj.getLength(), false);
				}
			}
		}
	}

	@Override
	public void clientUpdate(Level level, LevelHandler handler, Camera cam, double delta) {
		Vector3D pos = this.pos.get();
		Vector3D size = this.size.get();
		if (cam.getX() < pos.getX()) {
			cam.setX(pos.getX());
		}
		if (cam.getY() < pos.getY()) {
			cam.setY(pos.getY());
		}
		if (cam.getZ() < pos.getZ()) {
			cam.setZ(pos.getZ());
		}
		if (cam.getX() + cam.getViewWidth() > pos.getX() + size.getX()) {
			cam.setX(pos.getX() + size.getX() - cam.getViewWidth());
		}
		if (cam.getZ() + cam.getViewHeight() > pos.getZ() + size.getZ()) {
			cam.setZ(pos.getZ() + size.getZ() - cam.getViewHeight());
		}
	}

	@Override
	public void hostUpdate(Level level, LevelHandler handler, Camera cam, double delta) {

	}
	
	public boolean outsideBounds (GameObject obj) {
		Vector3D pos = this.pos.get();
		Vector3D size = this.size.get();
		return obj.getX() < pos.getX() || obj.getY() < pos.getY() || obj.getZ() < pos.getZ() ||
				obj.getX() + obj.getWidth() > pos.getX() + size.getX() || obj.getY() + obj.getHeight() > pos.getY() + size.getY() ||
						obj.getZ() + obj.getLength() > pos.getZ() + size.getZ();
	}
	
	public boolean nearBounds (GameObject obj) {
		Vector3D pos = this.pos.get();
		Vector3D size = this.size.get();
		return obj.getX() <= pos.getX() || obj.getY() <= pos.getY() || obj.getZ() <= pos.getZ() ||
				obj.getX() + obj.getWidth() >= pos.getX() + size.getX() || obj.getY() + obj.getHeight() >= pos.getY() + size.getY() ||
						obj.getZ() + obj.getLength() >= pos.getZ() + size.getZ();
	}

	@Override
	public void renderBefore(GraphicsContext ctx, Camera cam, Level level) {
		
	}

	@Override
	public void renderAfter(GraphicsContext ctx, Camera cam, Level level) {
		
	}

	@Override
	public List<ObservableProperty<?>> observableProperties() {
		return Arrays.asList(pos, size);
	}

	public Vector3D getPos() {
		return pos.get();
	}

	public void setPos(Vector3D pos) {
		this.pos.set(pos);
	}

	public Vector3D getSize() {
		return size.get();
	}

	public void setSize(Vector3D size) {
		this.size.set(size);
	}

	public boolean isBlockObjects() {
		return blockObjects;
	}

	public void setBlockObjects(boolean blockObjects) {
		this.blockObjects = blockObjects;
	}

}
