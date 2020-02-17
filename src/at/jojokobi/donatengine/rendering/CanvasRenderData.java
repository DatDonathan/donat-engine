package at.jojokobi.donatengine.rendering;

import java.util.List;

import at.jojokobi.donatengine.util.Position;

public class CanvasRenderData extends PositionedRenderData{

	private List<RenderShape>  shapes;

	public CanvasRenderData(Position position, List<RenderShape> shapes) {
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
