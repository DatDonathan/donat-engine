package at.jojokobi.donatengine.rendering;

import at.jojokobi.donatengine.style.FixedStyle;
import at.jojokobi.donatengine.util.Vector2D;

public class RenderText extends RenderShape {
	
	private String text;	
	
	public RenderText(Vector2D position, String text, FixedStyle style) {
		super(position, style);
		this.text = text;
	}


	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
