package at.jojokobi.donatengine.input;

import at.jojokobi.donatengine.objects.GameObject;

public class ClickInformation {

	private GameObject gameObject;
	private ClickDirection direction;
	
	public ClickInformation(GameObject gameObject, ClickDirection direction) {
		super();
		this.gameObject = gameObject;
		this.direction = direction;
	}
	public GameObject getGameObject() {
		return gameObject;
	}
	public ClickDirection getDirection() {
		return direction;
	}

}
