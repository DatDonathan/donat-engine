package at.jojokobi.donatengine.objects;

import at.jojokobi.donatengine.event.UpdateEvent;
import at.jojokobi.donatengine.level.Level;
import at.jojokobi.donatengine.util.Vector3D;

public class Camera {
	
	private double x;
	private double y;
	private double z;
	private String area = "";

	private double rotationX = 0;
	private double rotationY = 0;
	private double rotationZ = 0;
	
	private double viewWidth = 0;
	private double viewHeight = 0;

	private double nearClip = 1;
	private double farClip = 1000;
	private double fov = 90;

	public Camera(double x, double y, double z, double viewWidth, double viewHeight) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
		
		this.viewWidth = viewWidth;
		this.viewHeight = viewHeight;
	}
	
	public void update (UpdateEvent event, Level level) {
		
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

	public double getRotationX() {
		return rotationX;
	}

	public void setRotationX(double rotationX) {
		this.rotationX = rotationX;
	}

	public double getRotationY() {
		return rotationY;
	}

	public double getRotationZ() {
		return rotationZ;
	}

	public void setRotationY(double rotationY) {
		this.rotationY = rotationY;
	}

	public void setRotationZ(double rotationZ) {
		this.rotationZ = rotationZ;
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
		return new Vector3D(rotationX, rotationY, rotationZ);
	}

	public double getNearClip() {
		return nearClip;
	}

	public double getFarClip() {
		return farClip;
	}

	public void setNearClip(double nearClip) {
		this.nearClip = nearClip;
	}

	public void setFarClip(double farClip) {
		this.farClip = farClip;
	}

	public double getFov() {
		return fov;
	}

	public void setFov(double fov) {
		this.fov = fov;
	}

}
