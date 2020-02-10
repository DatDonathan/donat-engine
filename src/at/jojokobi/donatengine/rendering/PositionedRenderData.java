package at.jojokobi.donatengine.rendering;

import at.jojokobi.donatengine.util.Position;

public class PositionedRenderData extends RenderData {

	private Position position;

	public PositionedRenderData(Position position) {
		super();
		this.position = position;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

}
