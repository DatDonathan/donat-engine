package at.jojokobi.donatengine.rendering;

import at.jojokobi.donatengine.gui.style.FixedStyle;
import at.jojokobi.donatengine.util.Position;

public class RectRenderData extends PositionedRenderData {
	
	private double width;
	private double height;
	private FixedStyle style;

	
	public RectRenderData(Position position, double width, double height, FixedStyle style) {
		super(position);
		this.width = width;
		this.height = height;
		this.style = style;
	}

	public FixedStyle getStyle() {
		return style;
	}

	public void setStyle(FixedStyle style) {
		this.style = style;
	}

	public double getWidth() {
		return width;
	}

	public double getHeight() {
		return height;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public void setHeight(double height) {
		this.height = height;
	}
	
}
