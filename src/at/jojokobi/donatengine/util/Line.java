package at.jojokobi.donatengine.util;

public class Line {
	
	private Vector3D start;
	private Vector3D end;
	
	public Line(Vector3D start, Vector3D end) {
		super();
		this.start = start.clone();
		this.end = end.clone();
	}

	public Vector3D getStart() {
		return start.clone();
	}

	public Vector3D getEnd() {
		return end.clone();
	}

	public void setStart(Vector3D start) {
		this.start = start;
	}

	public void setEnd(Vector3D end) {
		this.end = end;
	}
	
	public Vector3D getDistanceVector () {
		return end.clone().subtract(start.getX(), start.getY(), start.getZ());
	}

}
