package at.jojokobi.donatengine.util;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import at.jojokobi.donatengine.serialization.SerializationWrapper;

public class Vector3D extends Vector2D {
	
	private double z;

	public Vector3D() {
		
	}

	public Vector3D(double x, double y, double z) {
		super(x, y);
		this.z = z;
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}
	
	@Override
	public Vector3D add(double x, double y) {
		super.add(x, y);
		return this;
	}
	
	@Override
	public Vector3D subtract(double x, double y) {
		super.subtract(x, y);
		return this;
	}
	
	public Vector3D add(double x, double y, double z) {
		add(x, y);
		this.z += z;
		return this;
	}
	
	public Vector3D subtract(double x, double y, double z) {
		subtract(x, y);
		this.z -= z;
		return this;
	}
	
	public Vector3D subtract(Vector3D vec) {
		return subtract(vec.getX(), vec.getY(), vec.getZ());
	}
	
	@Override
	public Vector3D clone()  {
		return new Vector3D(getX(), getY(), getZ());
	}
	
	@Override
	public Vector3D round() {
		super.round();
		this.z = Math.round(z);
		return this;
	}
	
	@Override
	public double length() {
		return Math.sqrt(getX()*getX() + getY()*getY() + getZ()*getZ());
	}
	
	@Override
	public Vector3D normalize() {
		double length = length();
		super.normalize();
		if (z != 0) {
			z /= length;
		}
		return this;
	}
	
	@Override
	public Vector3D multiply(double m) {
		super.multiply(m);
		z *= m;
		return this;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Vector3D [z=");
		builder.append(z);
		builder.append(", x=");
		builder.append(getX());
		builder.append(", y=");
		builder.append(getY());
		builder.append("]");
		return builder.toString();
	}

	@Override
	public void serialize(DataOutput buffer, SerializationWrapper serialization) throws IOException {
		super.serialize(buffer, serialization);
		buffer.writeDouble(z);
	}
	
	@Override
	public void deserialize(DataInput buffer, SerializationWrapper serialization) throws IOException {
		super.deserialize(buffer, serialization);
		z = buffer.readDouble();
	}

	public Vector3D add(Vector3D vector) {
		add(vector.getX(), vector.getY(), vector.getZ());
		return this;
	}
	
}
