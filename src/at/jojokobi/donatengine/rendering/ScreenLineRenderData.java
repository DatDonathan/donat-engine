package at.jojokobi.donatengine.rendering;

import at.jojokobi.donatengine.util.Vector2D;

public class ScreenLineRenderData extends ScreenPositonedRenderData {

	private Vector2D endPosition;

	public ScreenLineRenderData(Vector2D position, Vector2D endPosition) {
		super(position);
		this.endPosition = endPosition;
	}

	public Vector2D getEndPosition() {
		return endPosition;
	}

	public void setEndPosition(Vector2D endPosition) {
		this.endPosition = endPosition;
	}

}
