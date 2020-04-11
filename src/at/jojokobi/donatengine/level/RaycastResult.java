package at.jojokobi.donatengine.level;


public class RaycastResult<T> {

	private T object;
	private CubeFace direction;
	
	public RaycastResult(T object, CubeFace direction) {
		super();
		this.object = object;
		this.direction = direction;
	}
	public T getObject() {
		return object;
	}
	public CubeFace getDirection() {
		return direction;
	}

}
