package at.jojokobi.donatengine.objects;

import java.util.Comparator;
import java.util.List;

import at.jojokobi.donatengine.input.ClickDirection;
import at.jojokobi.donatengine.input.ClickInformation;
import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.rendering.Perspective;
import at.jojokobi.donatengine.rendering.StretchYZPerspective;
import at.jojokobi.donatengine.util.MathUtil;
import at.jojokobi.donatengine.util.Vector2D;
import at.jojokobi.donatengine.util.Vector3D;

public class Camera {

	private final Comparator<GameObject> comparator = new Comparator<GameObject>() {
		@Override
		public int compare(GameObject o1, GameObject o2) {

			int compare = 0;
			double x1 = o1.getX() - o1.getxOffset();
			double x2 = o2.getX() - o2.getxOffset();
			double y1 = o1.getY() - o1.getyOffset() + o1.getHeight();
			double y2 = o2.getY() - o2.getyOffset() + o2.getHeight();
			double z1 = o1.getZ() - o1.getzOffset();
			double length1 = o1.getRenderModel().getLength();
			double z2 = o2.getZ() - o2.getzOffset();
			double length2 = o2.getRenderModel().getLength();

			if (y1 < y2) {
				compare = -1;
			} else if (y1 > y2) {
				compare = 1;
			} else if (z1 + length1 < z2 + length2) {
				compare = -1;
			} else if (z1 + length1 > z2 + length2) {
				compare = 1;
			} else if (Math.abs(x1 - getX() - viewBoxWidth / 2) > Math.abs(x2 - getX() - viewBoxWidth / 2)) {
				compare = -1;
			} else if (Math.abs(x1 - getX() - viewBoxWidth / 2) < Math.abs(x2 - getX() - viewBoxWidth / 2)) {
				compare = 1;
			}
			return compare;

//			int compare = 0;
//			if (o1.getZ() + o1.getLength() <= o2.getZ()) {
//				compare = -1;
//			} else if (o1.getZ() >= o2.getZ() + o2.getLength()) {
//				compare = 1;
//			} else if (o1.getY() < o2.getY()) {
//				compare = -1;
//			} else if (o1.getY() > o2.getY()) {
//				compare = 1;
//			} else if (Math.abs(o1.getX() - getX() - getWidth() / 2) > Math.abs(o2.getX() - getX() - getWidth() / 2)) {
//				compare = -1;
//			} else if (Math.abs(o1.getX() - getX() - getWidth() / 2) < Math.abs(o2.getX() - getX() - getWidth() / 2)) {
//				compare = 1;
//			}
//			return compare;
		}
	};

	private Perspective perspective = new StretchYZPerspective();
	
	private double x;
	private double y;
	private double z;
	private String area = "";

	private double rotationX = 0;
	private double viewWidth = 0;
	private double viewHeight = 0;

	private double renderDistance = 32 * 20;

	private double lastX;
	private double lastY;
	private double lastZ;
	private double lastRotationX;

	private double viewBoxX;
	private double viewBoxY;
	private double viewBoxZ;
	private double viewBoxWidth;
	private double viewBoxHeight;
	private double viewBoxLength;

	private Long follow = null;

	public Camera(double x, double y, double z, double viewWidth, double viewHeight) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
		this.viewWidth = viewWidth;
		this.viewHeight = viewHeight;
		
		lastX = x - 1;
	}

	public void update(double delta, Level level) {
//		if (getX() < level.getX()) {
//			setX(0);
//		}
//		if (getY() < level.getY()) {
//			setY(0);
//		}
//		if (getZ() < level.getZ()) {
//			setZ(0);
//		}
//		if (level.getWidth() >= getWidth() && getX() + getWidth() > level.getX() + level.getWidth()) {
//			setX(level.getX() + level.getWidth() - getWidth());
//		}
//		if (level.getHeight() >= getHeight() && getY() + getHeight() > level.getY() + level.getHeight()) {
//			setY(level.getY() + level.getHeight() - getHeight());
//		}
//		if (level.getLength() >= getLength() && getZ() + getLength() > level.getZ() + level.getLength()) {
//			setZ(level.getZ() + level.getLength() - getLength());
//		}

		if (follow != null) {
			GameObject obj = level.getObjectById(follow);
			if (obj != null) {
				
				double x = viewBoxX + viewBoxWidth / 4;
				double y = viewBoxY + viewBoxHeight / 4;
				double dx = viewBoxX + 3 * viewBoxWidth / 4;
				double dy = viewBoxY + 3 * viewBoxHeight / 4;
				double z = viewBoxZ + viewBoxLength / 4;
				double dz = viewBoxZ + 3 * viewBoxLength / 4;
				// X
				if (obj.getX() < x) {
					setX(obj.getX() - viewBoxWidth / 4);
				}
				if (obj.getX() > dx) {
					setX(obj.getX() - 3 * viewBoxWidth / 4);
				}
				// Y
				if (obj.getY() < y) {
					setY(obj.getY() - viewBoxHeight / 4);
				}
				if (obj.getY() > dy) {
					setY(obj.getY() - 3 * viewBoxHeight / 4);
				}
				// Z
				if (obj.getZ() < z) {
					setZ(obj.getZ() - viewBoxLength / 4);
				}
				if (obj.getZ() > dz) {
					setZ(obj.getZ() - 3 * viewBoxLength / 4);
				}
				setArea(obj.getArea());
			}
		}
	}

	public boolean canSee(Hitbox obj) {
		if (getX() != lastX || getY() != lastY || getZ() != lastZ || getRotationX() != lastRotationX) {
			viewBoxWidth = getViewWidth();
			viewBoxHeight = MathUtil.interpolate(Math.sin(Math.toRadians(rotationX)), getViewHeight(),
					getRenderDistance());
			viewBoxLength = MathUtil.interpolate(Math.cos(Math.toRadians(rotationX)), getViewHeight(),
					getRenderDistance());

			viewBoxX = getX();
			viewBoxY = getY();
			viewBoxZ = getZ();

			lastX = getX();
			lastY = getY();
			lastZ = getZ();
			lastRotationX = getRotationX();
		}

		if (viewBoxHeight < 0) {
			viewBoxY += viewBoxHeight;
			viewBoxHeight = -viewBoxHeight;
		}

		if (viewBoxLength < 0) {
			viewBoxZ += viewBoxLength;
			viewBoxLength = -viewBoxLength;
		}
		return obj.isColliding(viewBoxX, viewBoxY, viewBoxZ, viewBoxWidth, viewBoxHeight, viewBoxLength, getArea());
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getZ() {
		return z;
	}

	public String getArea() {
		return area;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public void setZ(double z) {
		this.z = z;
	}

	public void setArea(String area) {
		this.area = area;
	}

	@Deprecated
	public double mergeYAndZ(double y, double z) {
		double sin = getZMultiplier();
		double cos = getYMultiplier();
		return z * sin + y * cos;
	}

	@Deprecated
	public double getZMultiplier() {
		return Math.sin(Math.toRadians(getRotationX()));
	}

	@Deprecated
	public double getYMultiplier() {
		return Math.cos(Math.toRadians(getRotationX()));
	}

	public Long getFollow() {
		return follow;
	}

	public void setFollow(Long follow) {
		this.follow = follow;
	}

	public Perspective getPerspective() {
		return perspective;
	}

	public void setPerspective(Perspective perspective) {
		this.perspective = perspective;
	}

	public Vector3D getCenterRelative(Vector3D pos) {
		return new Vector3D(pos.getX() - getX() - viewBoxWidth / 2, -pos.getY() + getY() + viewBoxHeight  / 2,
				pos.getZ() - viewBoxLength / 2 - getZ());
	}

	public Vector2D centerRelativeToEdgeRelative(Vector2D pos) {
		return new Vector2D(pos.getX() + getViewWidth() / 2, pos.getY() + getViewHeight() / 2);
	}

	public Vector2D toScreenPosition(Vector3D pos) {
		return centerRelativeToEdgeRelative(getPerspective().toScreenPosition(getCenterRelative(pos), getRotation()));
	}

	public ClickInformation getClickedObject(Vector2D screenPos, List<GameObject> objects) {
		ClickInformation clickInfo = null;
		objects.sort(comparator);
		for (GameObject obj : objects) {
			ClickDirection direction = obj.getClickDirection(screenPos, this);
			if (direction != null) {
				clickInfo = new ClickInformation(obj, direction);
			}
		}
		return clickInfo;
	}

	public double getRotationX() {
		return rotationX;
	}

	public void setRotationX(double rotationX) {
		this.rotationX = rotationX;
	}

	public Comparator<GameObject> getComparator() {
		return comparator;
	}

	public double getViewWidth() {
		return viewWidth;
	}

	public double getViewHeight() {
		return viewHeight;
	}

	public void setViewWidth(double viewWidth) {
		this.viewWidth = viewWidth;
	}

	public void setViewHeight(double viewHeight) {
		this.viewHeight = viewHeight;
	}

	public Vector3D getRotation() {
		return new Vector3D(rotationX, 0, 0);
	}

	public double getRenderDistance() {
		return renderDistance;
	}

	public void setRenderDistance(double renderDistance) {
		this.renderDistance = renderDistance;
	}

}
