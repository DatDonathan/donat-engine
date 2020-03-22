package at.jojokobi.donatengine.objects;

import at.jojokobi.donatengine.util.Position;
import at.jojokobi.donatengine.util.Vector3D;

public interface Collidable {
	
	public double getX();
	
	public double getY();
	
	public double getZ();
	
	public double getWidth();
	
	public double getHeight();
	
	public double getLength();
	
	public String getArea();
	
	public boolean isSolid();
	
	public default Position getPosition () {
		return new Position(new Vector3D(getX(), getY(), getZ()), getArea());
	}
	
	public default boolean isColliding (Collidable obj) {
		return isColliding(obj.getX(), obj.getY(), obj.getZ(), obj.getWidth(), obj.getHeight(), obj.getLength(), obj.getArea());
	}
	
	public default boolean isColliding (double x, double y, double z, double width, double height, double length, String area) {
		return area.equals(getArea()) && getX() < x + width && x < getX() + getWidth() && getY() < y + height && y < getY() + getHeight() && getZ() < z + length && z < getZ() + getLength();
	}

}
