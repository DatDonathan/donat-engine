package at.jojokobi.donatengine.rendering;

import at.jojokobi.donatengine.gui.style.FixedStyle;
import at.jojokobi.donatengine.util.Position;

public class TextRenderData extends PositionedRenderData {
	
	private FixedStyle style;
	private String text;


	public TextRenderData(Position position, FixedStyle style, String text) {
		super(position);
		this.style = style;
		this.text = text;
	}

	public FixedStyle getStyle() {
		return style;
	}

	public void setStyle(FixedStyle style) {
		this.style = style;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
}
