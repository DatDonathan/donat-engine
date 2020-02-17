package at.jojokobi.donatengine.rendering;

import at.jojokobi.donatengine.style.FixedStyle;
import at.jojokobi.donatengine.util.Vector2D;

public class RenderLine extends RenderShape {

	private Vector2D endPosition;

	public RenderLine(Vector2D position, Vector2D endPosition, FixedStyle style) {
		super(position, style);
		this.endPosition = endPosition;
	}

	public Vector2D getEndPosition() {
		return endPosition;
	}

	public void setEndPosition(Vector2D endPosition) {
		this.endPosition = endPosition;
	}
	
}
