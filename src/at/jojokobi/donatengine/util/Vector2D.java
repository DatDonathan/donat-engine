package at.jojokobi.donatengine.util;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import at.jojokobi.donatengine.serialization.BinarySerializable;
import at.jojokobi.donatengine.serialization.SerializationWrapper;

public class Vector2D implements Cloneable, BinarySerializable{
	
	private double x;
	private double y;

	public Vector2D() {
		
	}

	public Vector2D(double x, double y) {
		super();
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}
	
	public Vector2D add (double x, double y) {
		this.x += x;
		this.y += y;
		return this;
	}
	
	public Vector2D add(Vector2D vector) {
		add(vector.getX(), vector.getY());
		return this;
	}
	
	public Vector2D subtract (Vector2D vec) {
		return subtract(vec.getX(), vec.getY());
	}
	
	public Vector2D subtract (double x, double y) {
		this.x -= x;
		this.y -= y;
		return this;
	}
	
	public boolean intersects (Vector2D topLeft, Vector2D bottomRight) {
		return intersects(topLeft.getX(), topLeft.getY(), bottomRight.getX() - topLeft.getX(), bottomRight.getY() - topLeft.getY());
	}
	
	public boolean intersects (double x, double y, double width, double height) {
		if (width < 0) {
			x += width;
			width *=-1;
		}
		else if (height < 0) {
			y += height;
			height *=-1;
		}
		return getX() >= x && getY() >= y && getX() < x + width && getY() < y + height;
	}
	
	public Vector2D multiply (double m) {
		x *= m;
		y *= m;
		return this;
	}
	
	@Override
	public Vector2D clone()  {
		return new Vector2D(x, y);
	}
	
	public Vector2D round () {
		x = Math.round(x);
		y = Math.round(y);
		return this;
	}
	
	public double length () {
		return Math.sqrt(x*x + y*y);
	}
	
	public Vector2D normalize () {
		double length = length();
		if (x != 0) {
			x /= length;
		}
		if (y != 0) {
			y /= length;
		}
		return this;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Vector2D [x=");
		builder.append(x);
		builder.append(", y=");
		builder.append(y);
		builder.append("]");
		return builder.toString();
	}

	@Override
	public void serialize(DataOutput buffer, SerializationWrapper serialization) throws IOException {
		buffer.writeDouble(x);
		buffer.writeDouble(y);
	}

	@Override
	public void deserialize(DataInput buffer, SerializationWrapper serialization) throws IOException {
		x = buffer.readDouble();
		y = buffer.readDouble();
	}
	
}
