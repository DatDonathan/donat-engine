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
	public void update(Level level, double delta) {
		if (blockObjects) {
			for (GameObject obj : level.getObjects()) {
				
			}
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
