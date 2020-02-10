package at.jojokobi.donatengine.rendering;

import at.jojokobi.donatengine.gui.style.FixedStyle;
import at.jojokobi.donatengine.util.Vector2D;

public class ScreenRectRenderData extends ScreenPositonedRenderData {

	private double width;
	private double height;
	private FixedStyle style;
	
	public ScreenRectRenderData(Vector2D position, double width, double height, FixedStyle style) {
		super(position);
		this.width = width;
		this.height = height;
		this.style = style;
	}

	public double getWidth() {
		return width;
	}

	public double getHeight() {
		return height;
	}

	public FixedStyle getStyle() {
		return style;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public void setStyle(FixedStyle style) {
		this.style = style;
	}
	
}
