package at.jojokobi.donatengine.objects;

import at.jojokobi.fxengine.level.Level;

public class FollowCamera extends Camera{

	private long follow;
	
	public FollowCamera(double x, double y, double z, double width, double height, double length, long follow) {
		super(x, y, z, width, height, length);
		this.follow = follow;
	}
	
	public FollowCamera(double x, double y, double width, double height, long follow) {
		this(x, y, 0, width, height, 0, follow);
	}

	@Override
	public void update(double delta, Level level) {
		super.update(delta, level);
		GameObject obj = level.getObjectById(follow);
		
		if (obj != null) {
			double x = getX() + getWidth()/4;
			double y = getY() + getHeight()/4;
			double dx = getX() + 3*getWidth()/4;
			double dy = getY() + 3*getHeight()/4;
			double z = getZ() + getLength()/4;
			double dz = getZ() + 3*getLength()/4;
			//X
			if (obj.getX() < x) {
				setX(obj.getX() - getWidth()/4);
			}
			if (obj.getX() > dx) {
				setX(obj.getX() - 3*getWidth()/4);
			}
			//Y
			if (obj.getY() < y) {
				setY(obj.getY() - getHeight()/4);
			}
			if (obj.getY() > dy) {
				setY(obj.getY() - 3*getHeight()/4);
			}
			//Z
			if (obj.getZ() < z) {
				setZ(obj.getZ() - getLength()/4);
			}
			if (obj.getZ() > dz) {
				setZ(obj.getZ() - 3*getLength()/4);
			}
		}
		
/*		if (getPerspective() == RenderPerspecitve.X_Y_TOP_DOWN) {
			double x = getX() + getWidth()/4;
			double y = getY() + getHeight()/4;
			double dx = getX() + 3*getWidth()/4;
			double dy = getY() + 3*getHeight()/4;
			if (obj.getX() < x) {
				setX(obj.getX() - getWidth()/4);
			}
			if (obj.getY() < y) {
				setY(obj.getY() - getHeight()/4);
			}
			if (obj.getX() > dx) {
				setX(obj.getX() - 3*getWidth()/4);
			}
			if (obj.getY() > dy) {
				setY(obj.getY() - 3*getHeight()/4);
			}
		}
		else if (getPerspective() == RenderPerspecitve.X_Z_TOP_DOWN) {
			double x = getX() + getWidth()/4;
			double z = getZ() + getLength()/4;
			double dx = getX() + 3*getWidth()/4;
			double dz = getZ() + 3*getLength()/4;
			if (obj.getX() < x) {
				setX(obj.getX() - getWidth()/4);
			}
			if (obj.getZ() < z) {
				setZ(obj.getZ() - getLength()/4);
			}
			if (obj.getX() > dx) {
				setX(obj.getX() - 3*getWidth()/4);
			}
			if (obj.getZ() > dz) {
				setZ(obj.getZ() - 3*getLength()/4);
			}
		}*/
	}

}
