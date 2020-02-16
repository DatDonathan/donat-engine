package at.jojokobi.donatengine.rendering;

import at.jojokobi.donatengine.style.FixedStyle;
import at.jojokobi.donatengine.util.Vector2D;

public class ScreenLineRenderData extends ScreenPositonedRenderData {

	private FixedStyle style;
	private Vector2D endPosition;

	
	public ScreenLineRenderData(Vector2D position, FixedStyle style, Vector2D endPosition) {
		super(position);
		this.style = style;
		this.endPosition = endPosition;
	}

	public Vector2D getEndPosition() {
		return endPosition;
	}

	public void setEndPosition(Vector2D endPosition) {
		this.endPosition = endPosition;
	}

	public FixedStyle getStyle() {
		return style;
	}

	public void setStyle(FixedStyle style) {
		this.style = style;
	}

}
