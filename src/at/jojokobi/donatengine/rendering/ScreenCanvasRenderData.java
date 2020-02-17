package at.jojokobi.donatengine.rendering;

import java.util.List;

import at.jojokobi.donatengine.util.Vector2D;

public class ScreenCanvasRenderData extends ScreenPositonedRenderData{

	private List<RenderShape>  shapes;

	public ScreenCanvasRenderData(Vector2D position, List<RenderShape> shapes) {
		super(position);
		this.shapes = shapes;
	}

	public List<RenderShape> getShapes() {
		return shapes;
	}

	public void setShapes(List<RenderShape> shapes) {
		this.shapes = shapes;
	}
	
}
