package at.jojokobi.donatengine.objects;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import at.jojokobi.donatengine.input.ClickDirection;
import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.level.LevelHandler;
import at.jojokobi.donatengine.objects.properties.ObservableProperty;
import at.jojokobi.donatengine.rendering.Image2DModel;
import at.jojokobi.donatengine.rendering.RenderModel;
import at.jojokobi.donatengine.serialization.BinarySerializable;
import at.jojokobi.donatengine.util.Vector2D;
import at.jojokobi.donatengine.util.Vector3D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class GameObject extends Hitbox implements BinarySerializable{

	private double xOffset = 0;
	private double yOffset = 0;
	private double zOffset = 0;

	private double xMotion = 0;
	private double yMotion = 0;
	private double zMotion = 0;

//	private Level level;
//	private Image image;
	private RenderModel renderModel;

	private boolean solid = false;
	private boolean visible = true;
	private boolean needsUpdate = true;
	
	private boolean moved = false;
	private boolean changedMotion = false;

	private boolean physics = false;
	private boolean collideSolid = true;
	private double gravityPerSecond = 0;
	private double bounce = 0;
	private double gravity = 0;

	private boolean alwaysUpdate = false;
	
	private List<ObjectComponent> components = new ArrayList<>();

//	@Deprecated
//	public GameObject(double x, double y, double z, Level level, Image image) {
//		this(x, y, z, level, new Image2DModel(image));
//	}
//
//	@Deprecated
//	public GameObject(double x, double y, Level level, Image image) {
//		this(x, y, 0, level, image);
//	}

	public GameObject(double x, double y, double z, String area, RenderModel renderModel) {
		super(x, y);
//		this.level = level;
		this.renderModel = renderModel;
		setZ(z);
		setArea(area);
	}
	
//
//	public GameObject(double x, double y, Level level, RenderModel renderModel) {
//		this(x, y, 0, level, renderModel);
//	}

	public boolean collidesSolid(Level level) {
		return getCollided(level).stream().anyMatch((obj) -> obj.isSolid());
	}

	public List<GameObject> getCollided(Level level) {
		List<GameObject> collided = new ArrayList<>();
		for (GameObject obj : level.getObjects()) {
			if (isColliding(obj) && obj != this) {
				collided.add(obj);
			}
		}
		return collided;
	}
//	
//	public List<Tile> getCollidedTiles () {
//		List<Tile> collided = new ArrayList<>();
//		Tile[][] map = getLevel().getMap();
//		for (int y = 0; y < map.length; y++) {
//			for (int x = 0; x < map[y].length; x++) {
//				if (isColliding(x*Tile.TILE_WIDTH, y*Tile.TILE_WIDTH, Tile.TILE_WIDTH, Tile.TILE_WIDTH)) {
//					collided.add(map[y][x]);
//				}
//			}
//		}
//		return collided;
//	}

	public void hostUpdate(Level level, LevelHandler handler, Camera camera, double delta) {
		components.forEach((c) -> c.hostUpdate(this, level, handler, camera, delta));
	}
	
	public void update(Level level, LevelHandler handler, Camera camera, double delta) {
		if (isPhysics()) {
			if (!onGround(level)) {
				gravity += getGravityPerSecond() * delta;
			} else {
				gravity = 0;
			}
		}
		if (xMotion != 0 || getTotalYMotion() != 0 || zMotion != 0) {
			move(xMotion, getTotalYMotion(), zMotion, delta, level);
		}
		components.forEach((c) -> c.update(this, level, handler, camera, delta));
	}
	
	public void clientUpdate(Level level, LevelHandler handler, Camera camera, double delta) {
		components.forEach((c) -> c.clientUpdate(this, level, handler, camera, delta));
	}
	
	public void renderGUI (GraphicsContext ctx, Camera cam, Level level) {
		
	}

	public void move(double xMotion, double yMotion, double zMotion, double delta, Level level) {
		double x = xMotion * delta;
		double y = yMotion * delta;
		double z = zMotion * delta;
		boolean movedBefore = moved;
		boolean motionBefore = changedMotion;
		if (isCollideSolid()) {
			// Y
			List<GameObject> yObjs = null;
			if (y < 0) {
				yObjs = level.getSolidInArea(getX(), getY() + y, getZ(), getWidth(), getHeight() - y, getLength(), getArea ());
			} else {
				yObjs = level.getSolidInArea(getX(), getY(), getZ(), getWidth(), getHeight() + y, getLength(), getArea());
			}
			yObjs.remove(this);
			if (yObjs.isEmpty()) {
				setY(getY() + y);
			}
			// Positive
			else if (y > 0) {
				GameObject yObj = yObjs.get(0);
				for (int i = 1; i < yObjs.size(); i++) {
					if (yObj.getY() > yObjs.get(i).getY()) {
						yObj = yObjs.get(i);
					}
				}
				setY(yObj.getY() - getHeight());
				if (isPhysics()) {
					setTotalYMotion(-yMotion * getBounce());
				}
			}
			// Negative
			else if (y < 0) {
				GameObject yObj = yObjs.get(0);
				for (int i = 1; i < yObjs.size(); i++) {
					if (yObj.getY() + yObj.getHeight() < yObjs.get(i).getY() + yObjs.get(i).getHeight()) {
						yObj = yObjs.get(i);
					}
				}
				setY(yObj.getY() + yObj.getHeight());
				if (isPhysics()) {
					setTotalYMotion(-yMotion * getBounce());
				}
			}

			// X
			List<GameObject> xObjs = null;
			if (x < 0) {
				xObjs = level.getSolidInArea(getX() + x, getY(), getZ(), getWidth() - x, getHeight(), getLength(), getArea ());
			} else {
				xObjs = level.getSolidInArea(getX(), getY(), getZ(), getWidth() + x, getHeight(), getLength(), getArea ());
			}
			xObjs.remove(this);
			if (xObjs.isEmpty()) {
				setX(getX() + x);
			}
			// Positive
			else if (x > 0) {
				GameObject xObj = xObjs.get(0);
				for (int i = 1; i < xObjs.size(); i++) {
					if (xObj.getX() > xObjs.get(i).getX()) {
						xObj = xObjs.get(i);
					}
				}
				setX(xObj.getX() - getWidth());
				if (isPhysics()) {
					setxMotion(-xMotion * getBounce());
				}
			}
			// Negative
			else if (x < 0) {
				GameObject xObj = xObjs.get(0);
				for (int i = 1; i < xObjs.size(); i++) {
					if (xObj.getX() + xObj.getWidth() < xObjs.get(i).getX() + xObjs.get(i).getWidth()) {
						xObj = xObjs.get(i);
					}
				}
				setX(xObj.getX() + xObj.getWidth());
				if (isPhysics()) {
					setxMotion(-xMotion * getBounce());
				}
			}

			// Z
			List<GameObject> zObjs = null;
			if (z < 0) {
				zObjs = level.getSolidInArea(getX(), getY(), getZ() + z, getWidth(), getHeight(), getLength() - z, getArea ());
			} else {
				zObjs = level.getSolidInArea(getX(), getY(), getZ(), getWidth(), getHeight(), getLength() + z, getArea ());
			}
			zObjs.remove(this);
			if (zObjs.isEmpty()) {
				setZ(getZ() + z);
			}
			// Positive
			else if (z > 0) {
				GameObject zObj = zObjs.get(0);
				for (int i = 1; i < zObjs.size(); i++) {
					if (zObj.getZ() > zObjs.get(i).getZ()) {
						zObj = zObjs.get(i);
					}
				}
				setZ(zObj.getZ() - getLength());
				if (isPhysics()) {
					setzMotion(-zMotion * getBounce());
				}
			}
			// Negative
			else if (z < 0) {
				GameObject zObj = zObjs.get(0);
				for (int i = 1; i < zObjs.size(); i++) {
					if (zObj.getZ() + zObj.getLength() < zObjs.get(i).getZ() + zObjs.get(i).getLength()) {
						zObj = zObjs.get(i);
					}
				}
				setZ(zObj.getZ() + zObj.getLength());
				if (isPhysics()) {
					setzMotion(-zMotion * getBounce());
				}
			}
		} else {
			setX(getX() + x);
			setY(getY() + y);
			setZ(getZ() + z);
		}
		moved = movedBefore;
		changedMotion = motionBefore;
	}
	
	@Override
	public void setX(double x) {
		super.setX(x);
		moved = true;
	}
	
	@Override
	public void setY(double y) {
		super.setY(y);
		moved = true;
	}
	
	@Override
	public void setZ(double z) {
		super.setZ(z);
		moved = true;
	}
	
	@Override
	public void setArea(String area) {
		super.setArea(area);
		moved = true;
	}

	public void onSpawn(Level level) {
		components.forEach(c -> c.onSpawn(this, level));
	}

	public boolean isNeedsUpdate() {
		return needsUpdate;
	}

	protected void setNeedsUpdate(boolean needsUpdate) {
		this.needsUpdate = needsUpdate;
	}

	public void delete(Level level) {
		level.remove(this);
	}


	public boolean onGround(Level level) {
		List<GameObject> objs = gravityPerSecond < 0
				? level.getSolidInArea(getX(), getY() - 1, getZ(), getWidth(), 1, getLength(), getArea())
				: level.getSolidInArea(getX(), getY() + getHeight(), getZ(), getWidth(), 1, getLength(), getArea());
		objs.remove(this);
		return !objs.isEmpty();
	}

	public void render(GraphicsContext ctx, Camera cam, Level level) {
		ctx.save();
		components.forEach((c) -> c.renderBefore(this, ctx, cam, level));
		ctx.restore();
		
		ctx.save();
		if (renderModel != null) {
			getRenderModel().render(ctx, cam, getX() - getxOffset(), getY() - getyOffset(), getZ() - getzOffset());
		}
		ctx.restore();
		
		ctx.save();
		components.forEach((c) -> c.renderAfter(this, ctx, cam, level));
		ctx.restore();
//		switch (cam.getPerspective()) {
//		case X_Y_TOP_DOWN:
//			if (cam.isColliding(getX() - getxOffset(), getY() - getyOffset(), getZ() - getzOffset(), getImage().getWidth(), getImage().getHeight(), getLength())) {
//				ctx.drawImage(getImage(), Math.round(getX() - getxOffset() - cam.getX()), Math.round(getY() - getyOffset()- cam.getY()));
//			}
//			break;
//		case X_Z_TOP_DOWN:
//			if (cam.isColliding(getX() - getxOffset(), getY() - getyOffset(), getZ() - getzOffset(), getImage().getWidth(), getHeight(), getImage().getHeight())) {
//				ctx.drawImage(getImage(), Math.round(getX() - getxOffset() - cam.getX()), Math.round(getZ() - getzOffset()- cam.getZ()));
//			}
//			break;
//		default:
//			break;
//		
//		}
	}

	public boolean canRender(Camera cam) {
/*		return cam.isColliding(getX() - getxOffset(), getY() - getyOffset(),
			cam.getZ() - getRenderModel().getRenderingZ(cam, getZ()) - getzOffset(), getRenderModel().getWidth(),
			getRenderModel().getHeight(), getRenderModel().getLength() + getRenderModel().getRenderingZ(cam, getZ()) * 2);*/
		
		return cam.canSee(this);
	}
	
	public ClickDirection getClickDirection (Vector2D pos, Camera cam) {
		ClickDirection direction = null;
		Vector3D position = new Vector3D(getX(), getY(), getZ());

		Vector2D topBackLeft = cam.toScreenPosition(position.clone().add(0, getHeight(), 0));
		Vector2D topBackRight = cam.toScreenPosition(position.clone().add(getWidth(), getHeight(), 0));
		Vector2D topFrontLeft = cam.toScreenPosition(position.clone().add(0, getHeight(), getLength()));
		Vector2D topFrontRight = cam.toScreenPosition(position.clone().add(getWidth(), getHeight(), getLength()));
		
//		Vector2D bottomBackLeft = cam.toScreenPosition(position.clone().add(0, 0, 0));
//		Vector2D bottomBackRight = cam.toScreenPosition(position.clone().add(getWidth(), 0, 0));
		Vector2D bottomFrontLeft = cam.toScreenPosition(position.clone().add(0, 0, getLength()));
		Vector2D bottomFrontRight = cam.toScreenPosition(position.clone().add(getWidth(), 0, getLength()));
		
		//Left
		if (pos.intersects(topBackLeft.clone().add(-2, 0), bottomFrontLeft.clone().add(2, 0))) {
			direction = ClickDirection.LEFT;
		}
		//Right
		else if (pos.intersects(bottomFrontRight.clone().add(-2, 0), topBackRight.clone().add(2, 0))) {
			direction = ClickDirection.RIGHT;
		}
		//Bottom
		else if (pos.intersects(bottomFrontLeft.clone().add(0, -2), bottomFrontRight.clone().add(0, 2))) {
			direction = ClickDirection.BOTTOM;
		}
		//Back
		else if (pos.intersects(topBackLeft.clone().add(0, -2), topBackRight.clone().add(0, 2))) {
			direction = ClickDirection.BACK;
		}
		//Top
		else if (pos.intersects(topBackLeft, topFrontRight)) {
			direction = ClickDirection.TOP;
		}
		//Front
		else if (pos.intersects(topFrontLeft, bottomFrontRight)) {
			direction = ClickDirection.FRONT;
		}
		
		return direction;
	}

	@Deprecated
	public Image getImage() {
		return ((Image2DModel) renderModel).getImage();
	}

	@Deprecated
	protected void setImage(Image image) {
		this.renderModel = new Image2DModel(image);
	}

	public RenderModel getRenderModel() {
		return renderModel;
	}

	public void setRenderModel(RenderModel renderModel) {
		this.renderModel = renderModel;
	}
//
//	public Level getLevel() {
//		return level;
//	}
	
	public boolean fetchMoved () {
		boolean moved = this.moved;
		this.moved = false;
		return moved;
	}
	
	public boolean fetchChangedMotion () {
		boolean changedMotion = this.changedMotion;
		this.changedMotion = false;
		return changedMotion;
	}

	public double getxOffset() {
		return xOffset;
	}

	public void setxOffset(double xOffset) {
		this.xOffset = xOffset;
	}

	public double getyOffset() {
		return yOffset;
	}

	public void setyOffset(double yOffset) {
		this.yOffset = yOffset;
	}

	public double getzOffset() {
		return zOffset;
	}

	public void setzOffset(double zOffset) {
		this.zOffset = zOffset;
	}

	public double getxMotion() {
		return xMotion;
	}
	
	public void setxMotion(double xMotion) {
		setxMotion(xMotion, true);
	}

	public void setxMotion(double xMotion, boolean setChange) {
		if (setChange) {
			changedMotion = changedMotion || xMotion != this.xMotion;
		}
		this.xMotion = xMotion;
	}

	public double getyMotion() {
		return yMotion;
	}

	public double getTotalYMotion() {
		return yMotion + gravity;
	}
	
	public void setyMotion(double yMotion) {
		setyMotion(yMotion, true);
	}

	public void setyMotion(double yMotion, boolean setChange) {
		if (setChange) {
			changedMotion = changedMotion || yMotion != this.yMotion;
		}
		this.yMotion = yMotion;
	}
	
	public void setTotalYMotion(double yMotion) {
		setTotalYMotion(yMotion, true);
	}

	public void setTotalYMotion(double yMotion, boolean setChange) {
		setyMotion(yMotion, setChange);
		setGravity(0);
	}

	public double getzMotion() {
		return zMotion;
	}
	
	public void setzMotion(double zMotion) {
		setzMotion(zMotion, true);
	}

	public void setzMotion(double zMotion, boolean setChange) {
		if (setChange) {
			changedMotion = changedMotion || zMotion != this.zMotion;
		}
		this.zMotion = zMotion;
	}

	public boolean isSolid() {
		return solid;
	}

	public void setSolid(boolean solid) {
		this.solid = solid;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public boolean isPhysics() {
		return physics;
	}

	public void setPhysics(boolean physics) {
		this.physics = physics;
	}

	public double getGravityPerSecond() {
		return gravityPerSecond;
	}

	public void setGravityPerSecond(double gravity) {
		this.gravityPerSecond = gravity;
	}

	public double getBounce() {
		return bounce;
	}

	public void setBounce(double bounce) {
		this.bounce = bounce;
	}

	public double getGravity() {
		return gravity;
	}

	public void setGravity(double gravity) {
		changedMotion = changedMotion || gravity != this.gravity;
		this.gravity = gravity;
	}

	public boolean isAlwaysUpdate() {
		return alwaysUpdate;
	}

	protected void setAlwaysUpdate(boolean alwaysUpdate) {
		this.alwaysUpdate = alwaysUpdate;
	}
//
//	public Input getInput() {
//		return getLevel().getScreen().getInput();
//	}

	public boolean isCollideSolid() {
		return collideSolid;
	}

	public void setCollideSolid(boolean collideSolid) {
		this.collideSolid = collideSolid;
	}
	
	public <T extends ObjectComponent> T getComponent (Class<T> clazz) {
		T component = null;
		for (Iterator<ObjectComponent> iterator = components.iterator(); iterator.hasNext();) {
			ObjectComponent c = iterator.next();
			if (c.getClass() == clazz) {
				component = clazz.cast(c);
			}
		}
		return component;
	}
	
	protected void addComponent (ObjectComponent component) {
		components.add(component);
	}
	
	public List<ObservableProperty<?>> observableProperties () {
		List<ObservableProperty<?>> properties = new ArrayList<>();
		for (ObjectComponent comp : components) {
			properties.addAll(comp.observableProperties());
		}
		return properties;
	}
	
	@Override
	public void serialize(DataOutput buffer) throws IOException{
		buffer.writeDouble(getX());
		buffer.writeDouble(getY());
		buffer.writeDouble(getZ());
		buffer.writeUTF(getArea());
		buffer.writeDouble(xMotion);
		buffer.writeDouble(yMotion);
		buffer.writeDouble(zMotion);
		buffer.writeDouble(gravity);
		for (ObservableProperty<?> property : observableProperties()) {
			property.writeValue(buffer);
		}
	}
	
	@Override
	public void deserialize(DataInput buffer) throws IOException {
		setX(buffer.readDouble());
		setY(buffer.readDouble());
		setZ(buffer.readDouble());
		setArea(buffer.readUTF());
		xMotion = buffer.readDouble();
		yMotion = buffer.readDouble();
		zMotion = buffer.readDouble();
		gravity = buffer.readDouble();
		for (ObservableProperty<?> property : observableProperties()) {
			property.readValue(buffer);
		}
	}

}
