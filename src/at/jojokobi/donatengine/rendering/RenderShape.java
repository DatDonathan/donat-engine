package at.jojokobi.donatengine.rendering;

import at.jojokobi.donatengine.style.FixedStyle;
import at.jojokobi.donatengine.util.Vector2D;

public abstract class RenderShape {
	
	private Vector2D position;
	private FixedStyle style;
	
		
	public RenderShape(Vector2D position, FixedStyle style) {
		super();
		this.style = style;
		this.position = position;
	}
	
	public FixedStyle getStyle() {
		return style;
	}
	public Vector2D getPosition() {
		return position;
	}
	public void setStyle(FixedStyle style) {
		this.style = style;
	}
	public void setPosition(Vector2D position) {
		this.position = position;
	}

}
