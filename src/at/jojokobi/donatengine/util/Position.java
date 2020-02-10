package at.jojokobi.donatengine.util;

public class Position {
	
	private Vector3D position;
	private String area;
	
	
	
	public Position(Vector3D position, String area) {
		super();
		this.position = position;
		this.area = area;
	}
	
	public Vector3D getPosition() {
		return position;
	}
	public String getArea() {
		return area;
	}
	public void setPosition(Vector3D position) {
		this.position = position;
	}
	public void setArea(String area) {
		this.area = area;
	}
	
}
