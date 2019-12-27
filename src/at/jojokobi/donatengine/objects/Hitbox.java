package at.jojokobi.donatengine.objects;

import at.jojokobi.donatengine.util.Vector3D;

public class Hitbox {

	private double x = 0;
	private double y = 0;
	private double z = 0;
	private double width = 32;
	private double height = 32;
	private double length = 32;
	private String area = "";
	
	public Hitbox(double x, double y, double z) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Hitbox(double x, double y) {
		this(x, y, 0);
	}
	
	public boolean isColliding (Hitbox obj) {
		return isColliding(obj.getX(), obj.getY(), obj.getZ(), obj.getWidth(), obj.getHeight(), obj.getLength(), obj.getArea());
	}
//	
//	public boolean isCollidingIgnoreZ (Hitbox obj) {
//		return isColliding(obj.getX(), obj.getY(), z, obj.getWidth(), obj.getHeight(), length);
//	}
	
	public boolean isColliding (double x, double y, double z, double width, double height, double length, String area) {
		return area.equals(this.area) && getX() < x + width && x < getX() + getWidth() && getY() < y + height && y < getY() + getHeight() && getZ() < z + length && z < getZ() + getLength();
	}
//	
//	public boolean isColliding (double x, double y, double width, double height) {
//		return getX() < x + width && x < getX() + getWidth() && getY() < y + height && y < getY() + getHeight();
//	}
	
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
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

	public void setZ(double z) {
		this.z = z;
	}

	public void setLength(double depth) {
		this.length = depth;
	}
	
	public Vector3D getPosition () {
		return new Vector3D(x, y, z);
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
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
