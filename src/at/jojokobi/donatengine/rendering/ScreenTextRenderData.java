package at.jojokobi.donatengine.rendering;

import at.jojokobi.donatengine.style.FixedStyle;
import at.jojokobi.donatengine.util.Vector2D;

public class ScreenTextRenderData extends ScreenPositonedRenderData {

	private String text;
	private FixedStyle style;
	
	public ScreenTextRenderData(Vector2D position, String text, FixedStyle style) {
		super(position);
		this.text = text;
		this.style = style;
	}

	public String getText() {
		return text;
	}

	public FixedStyle getStyle() {
		return style;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setStyle(FixedStyle style) {
		this.style = style;
	}
	
}
