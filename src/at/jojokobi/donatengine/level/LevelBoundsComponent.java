package at.jojokobi.donatengine.level;

import java.util.Arrays;
import java.util.List;

import at.jojokobi.donatengine.objects.Camera;
import at.jojokobi.donatengine.objects.GameObject;
import at.jojokobi.donatengine.objects.properties.ObservableProperty;
import at.jojokobi.donatengine.util.Vector3D;
import javafx.scene.canvas.GraphicsContext;

public class LevelBoundsComponent implements LevelComponent {
	
	private Vector3D pos;
	private Vector3D size;
	
	private boolean blockObjects;


	public LevelBoundsComponent(Vector3D pos, Vector3D size, boolean blockObjects) {
		super();
		this.pos = pos;
		this.size = size;
		this.blockObjects = blockObjects;
	}

	@Override
	public void init(Level level) {
		
	}

	@Override
	public void update(Level level, Camera cam, double delta) {
		if (blockObjects) {
			for (GameObject obj : level.getObjects()) {
				if (obj.getX() < pos.getX()) {
					obj.setX(pos.getX());
				}
				if (obj.getY() < pos.getY()) {
					obj.setY(pos.getY());
				}
				if (obj.getZ() < pos.getZ()) {
					obj.setZ(pos.getZ());
				}
				if (obj.getX() + obj.getWidth() >= pos.getX() + size.getX()) {
					obj.setX(pos.getX() + size.getX() - obj.getWidth());
				}
				if (obj.getY() + obj.getHeight() >= pos.getY() + size.getY()) {
					obj.setY(pos.getY() + size.getY() - obj.getHeight());
				}
				if (obj.getZ() + obj.getLength() >= pos.getZ() + size.getZ()) {
					obj.setZ(pos.getZ() + size.getZ() - obj.getLength());
				}
			}
		}
		
		if (cam.getX() < pos.getX()) {
			cam.setX(pos.getX());
		}
		if (cam.getY() < pos.getY()) {
			cam.setY(pos.getY());
		}
		if (cam.getZ() < pos.getZ()) {
			cam.setZ(pos.getZ());
		}
		if (cam.getX() + cam.getViewWidth() >= pos.getX() + size.getX()) {
			cam.setX(pos.getX() + size.getX() - cam.getViewWidth());
		}
		if (cam.getZ() + cam.getViewHeight() >= pos.getZ() + size.getZ()) {
			cam.setZ(pos.getZ() + size.getZ() - cam.getViewHeight());
		}
	}

	@Override
	public void renderBefore(GraphicsContext ctx, Camera cam, Level level) {
		
	}

	@Override
	public void renderAfter(GraphicsContext ctx, Camera cam, Level level) {
		
	}

	@Override
	public List<ObservableProperty<?>> observableProperties() {
		return Arrays.asList();
	}

	public Vector3D getPos() {
		return pos;
	}

	public void setPos(Vector3D pos) {
		this.pos = pos;
	}

	public Vector3D getSize() {
		return size;
	}

	public void setSize(Vector3D size) {
		this.size = size;
	}

	public boolean isBlockObjects() {
		return blockObjects;
	}

	public void setBlockObjects(boolean blockObjects) {
		this.blockObjects = blockObjects;
	}

}
