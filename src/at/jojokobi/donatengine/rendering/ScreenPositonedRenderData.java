package at.jojokobi.donatengine.rendering;

import at.jojokobi.donatengine.util.Vector2D;

public class ScreenPositonedRenderData extends RenderData {
	
	private Vector2D position;

	public ScreenPositonedRenderData(Vector2D position) {
		super();
		this.position = position;
	}

	public Vector2D getPosition() {
		return position;
	}

	public void setPosition(Vector2D position) {
		this.position = position;
	}

}
