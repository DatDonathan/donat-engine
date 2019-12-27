package at.jojokobi.donatengine.util;

public class Rect {
	
	private double x;
	private double y;
	private double width;
	private double height;
	
	public Rect(double x, double y, double width, double height) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
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

	public double getHeight() {
		return height;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public void setHeight(double height) {
		this.height = height;
	}
	
	public boolean isColliding (double x, double y, double width, double height) {
		return getX() < x + width && x < getX() + getWidth() && getY() < y + height && y < getY() + getHeight();
	}

}
