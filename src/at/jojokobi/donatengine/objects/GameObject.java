package at.jojokobi.donatengine.objects;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import at.jojokobi.donatengine.event.UpdateEvent;
import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.objects.properties.ObservableProperty;
import at.jojokobi.donatengine.rendering.RenderData;
import at.jojokobi.donatengine.rendering.ModelRenderData;
import at.jojokobi.donatengine.serialization.BinarySerializable;
import at.jojokobi.donatengine.serialization.SerializationWrapper;
import at.jojokobi.donatengine.util.Position;
import at.jojokobi.donatengine.util.Vector3D;

public abstract class GameObject implements BinarySerializable, Collidable{
	
	private double x = 0;
	private double y = 0;
	private double z = 0;
	private double width = 1;
	private double height = 1;
	private double length = 1;
	private String area = "";

	private double xOffset = 0;
	private double yOffset = 0;
	private double zOffset = 0;

	private double xMotion = 0;
	private double yMotion = 0;
	private double zMotion = 0;

//	private Level level;
//	private Image image;
	private String renderTag = "";

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


	public GameObject(double x, double y, double z, String area, String renderTag) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.area = area;
		this.renderTag = renderTag;
	}

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

	public void hostUpdate(Level level, UpdateEvent event) {
		components.forEach((c) -> c.hostUpdate(this, level, event));
	}
	
	public void update(Level level, UpdateEvent event) {
		if (isPhysics()) {
			if (!onGround(level)) {
				gravity += getGravityPerSecond() * event.getDelta();
			} else {
				gravity = 0;
			}
		}
		if (xMotion != 0 || getTotalYMotion() != 0 || zMotion != 0) {
			move(xMotion, getTotalYMotion(), zMotion, event.getDelta(), level);
		}
		components.forEach((c) -> c.update(this, level, event));
	}
	
	public void clientUpdate(Level level, UpdateEvent event) {
		components.forEach((c) -> c.clientUpdate(this, level, event));
	}
	
	public void renderGUI (List<RenderData> data, Camera cam, Level level) {
		
	}

	public void move(double xMotion, double yMotion, double zMotion, double delta, Level level) {
		double x = xMotion * delta;
		double y = yMotion * delta;
		double z = zMotion * delta;
		boolean movedBefore = moved;
		boolean motionBefore = changedMotion;
		if (isCollideSolid()) {
			// Y
			System.out.println("Y");
			List<Collidable> yObjs = null;
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
				Collidable yObj = yObjs.get(0);
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
				Collidable yObj = yObjs.get(0);
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
			System.out.println("X");
			List<Collidable> xObjs = null;
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
				Collidable xObj = xObjs.get(0);
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
				Collidable xObj = xObjs.get(0);
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
			List<Collidable> zObjs = null;
			System.out.println("Z");
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
				Collidable zObj = zObjs.get(0);
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
				Collidable zObj = zObjs.get(0);
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
	
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
	public double getWidth() {
		return width;
	}
	public void setWidth(double width) {
		this.width = width;
	}
	public double getHeight() {
		return height;
	}
	public void setHeight(double height) {
		this.height = height;
	}

	public double getZ() {
		return z;
	}

	public double getLength() {
		return length;
	}

	public void setLength(double depth) {
		this.length = depth;
	}
	
	public Vector3D getPositionVector () {
		return new Vector3D(x, y, z);
	}

	public String getArea() {
		return area;
	}

	
	public void setX(double x) {
		setX(x, true);
	}
	
	public void setY(double y) {
		setY(y, true);
	}
	
	public void setZ(double z) {
		setZ(z , true);
	}
	
	public void setX(double x, boolean setChange) {
		this.x = x;
		moved = moved || setChange;
	}
	
	public void setY(double y, boolean setChange) {
		this.y = y;
		moved = moved || setChange;
	}
	
	public void setZ(double z, boolean setChange) {
		this.z = z;
		moved = moved || setChange;
	}
	
	public void setArea(String area) {
		this.area = area;
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
		List<Collidable> objs = gravityPerSecond < 0
				? level.getSolidInArea(getX(), getY() - 1, getZ(), getWidth(), 1, getLength(), getArea())
				: level.getSolidInArea(getX(), getY() + getHeight(), getZ(), getWidth(), 1, getLength(), getArea());
		objs.remove(this);
		return !objs.isEmpty();
	}

	public void render(List<RenderData> data, Camera cam, Level level) {
		components.forEach((c) -> c.renderBefore(this, data, cam, level));

		
		if (renderTag != null) {
			data.add(new ModelRenderData(new Position(getPositionVector().subtract(xOffset, yOffset, zOffset), getArea()), renderTag));
		}
		
		components.forEach((c) -> c.renderAfter(this, data, cam, level));
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
	
//	public ClickDirection getClickDirection (Vector2D pos, Camera cam) {
//		ClickDirection direction = null;
//		Vector3D position = new Vector3D(getX(), getY(), getZ());
//
//		Vector2D topBackLeft = cam.toScreenPosition(position.clone().add(0, getHeight(), 0));
//		Vector2D topBackRight = cam.toScreenPosition(position.clone().add(getWidth(), getHeight(), 0));
//		Vector2D topFrontLeft = cam.toScreenPosition(position.clone().add(0, getHeight(), getLength()));
//		Vector2D topFrontRight = cam.toScreenPosition(position.clone().add(getWidth(), getHeight(), getLength()));
//		
////		Vector2D bottomBackLeft = cam.toScreenPosition(position.clone().add(0, 0, 0));
////		Vector2D bottomBackRight = cam.toScreenPosition(position.clone().add(getWidth(), 0, 0));
//		Vector2D bottomFrontLeft = cam.toScreenPosition(position.clone().add(0, 0, getLength()));
//		Vector2D bottomFrontRight = cam.toScreenPosition(position.clone().add(getWidth(), 0, getLength()));
//		
//		//Left
//		if (pos.intersects(topBackLeft.clone().add(-2, 0), bottomFrontLeft.clone().add(2, 0))) {
//			direction = ClickDirection.LEFT;
//		}
//		//Right
//		else if (pos.intersects(bottomFrontRight.clone().add(-2, 0), topBackRight.clone().add(2, 0))) {
//			direction = ClickDirection.RIGHT;
//		}
//		//Bottom
//		else if (pos.intersects(bottomFrontLeft.clone().add(0, -2), bottomFrontRight.clone().add(0, 2))) {
//			direction = ClickDirection.BOTTOM;
//		}
//		//Back
//		else if (pos.intersects(topBackLeft.clone().add(0, -2), topBackRight.clone().add(0, 2))) {
//			direction = ClickDirection.BACK;
//		}
//		//Top
//		else if (pos.intersects(topBackLeft, topFrontRight)) {
//			direction = ClickDirection.TOP;
//		}
//		//Front
//		else if (pos.intersects(topFrontLeft, bottomFrontRight)) {
//			direction = ClickDirection.FRONT;
//		}
//		
//		return direction;
//	}

	
//
//	public Level getLevel() {
//		return level;
//	}
	
	public String getRenderTag() {
		return renderTag;
	}

	public void setRenderTag(String renderTag) {
		this.renderTag = renderTag;
	}

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
	
	public Vector3D getMotion() {
		return new Vector3D(xMotion, yMotion, zMotion);
	}
	
	public Vector3D getSize() {
		return new Vector3D(getWidth(), getHeight(), getLength());
	}
	
	public Vector3D getTotalMotion() {
		return new Vector3D(xMotion, getTotalYMotion(), zMotion);
	}
	
	public <T extends GameObject> List<T> getObjectsInDirection (Level level,Vector3D dir, double distance, Class<T> clazz) {
		double startX = getX();
		double startY = getY();
		double startZ = getZ();
		double width = distance;
		double height = distance;
		double length = distance;
		if (dir.getX() < 0) {
			startX -= distance;
			height = getHeight();
			length = getLength();
		}
		else if (dir.getX() > 0) {
			startX += getWidth();
			height = getHeight();
			length = getLength();
		}
		if (dir.getY() < 0) {
			startY -= distance;
			width = getWidth();
			length = getLength();
		}
		else if (dir.getY() > 0) {
			startY += getHeight();
			width = getWidth();
			length = getLength();
		}
		if (dir.getZ() < 0) {
			startZ -= distance;
			width = getWidth();
			height = getHeight();
		}
		else if (dir.getZ() > 0) {
			startZ += getLength();
			width = getWidth();
			height = getHeight();
		}
		return level.getObjectsInArea(startX, startY, startZ, width, height, length, getArea(), clazz);
	}
	
	@Override
	public void serialize(DataOutput buffer, SerializationWrapper serialization) throws IOException{
		buffer.writeDouble(getX());
		buffer.writeDouble(getY());
		buffer.writeDouble(getZ());
		buffer.writeUTF(getArea());
		buffer.writeDouble(xMotion);
		buffer.writeDouble(yMotion);
		buffer.writeDouble(zMotion);
		buffer.writeDouble(gravity);
		for (ObservableProperty<?> property : observableProperties()) {
			property.writeValue(buffer, serialization);
		}
	}
	
	@Override
	public void deserialize(DataInput buffer, SerializationWrapper serialization) throws IOException {
		setX(buffer.readDouble());
		setY(buffer.readDouble());
		setZ(buffer.readDouble());
		setArea(buffer.readUTF());
		xMotion = buffer.readDouble();
		yMotion = buffer.readDouble();
		zMotion = buffer.readDouble();
		gravity = buffer.readDouble();
		for (ObservableProperty<?> property : observableProperties()) {
			property.readValue(buffer, serialization);
		}
	}
	

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(getClass().getSimpleName() +  " [x=");
		builder.append(x);
		builder.append(", y=");
		builder.append(y);
		builder.append(", z=");
		builder.append(z);
		builder.append(", width=");
		builder.append(width);
		builder.append(", height=");
		builder.append(height);
		builder.append(", length=");
		builder.append(length);
		builder.append("]");
		return builder.toString();
	}

}
