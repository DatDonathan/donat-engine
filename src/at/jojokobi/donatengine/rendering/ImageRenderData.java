package at.jojokobi.donatengine.rendering;

import at.jojokobi.donatengine.util.Position;

public class ImageRenderData extends PositionedRenderData {

	private String tag;
	private double width;
	private double height;
	private double opacity;

	
	public ImageRenderData(Position position, String tag, double width, double height, double opacity) {
		super(position);
		this.tag = tag;
		this.width = width;
		this.height = height;
		this.opacity = opacity;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public double getWidth() {
		return width;
	}

	public double getHeight() {
		return height;
	}

	public double getOpacity() {
		return opacity;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public void setOpacity(double opacity) {
		this.opacity = opacity;
	}

}
