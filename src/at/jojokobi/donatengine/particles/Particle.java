package at.jojokobi.donatengine.particles;

import java.util.List;

import at.jojokobi.donatengine.objects.Camera;
import at.jojokobi.donatengine.rendering.RenderData;

public abstract class Particle {

	private double x = 0;
	private double y = 0;
	private double z = 0;
	private String area;
	
	private double lifetime = 2.0;
	private double timer = 0.0;
	
	

	public Particle(double x, double y, double z, String area) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
		this.area = area;
	}

	public void update(double delta) {
		timer += delta;
	}
	
	public abstract void render (List<RenderData> data, Camera cam);

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

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}

	public double getLifetime() {
		return lifetime;
	}

	public double getTimer() {
		return timer;
	}

	public void setLifetime(double lifetime) {
		this.lifetime = lifetime;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

}
