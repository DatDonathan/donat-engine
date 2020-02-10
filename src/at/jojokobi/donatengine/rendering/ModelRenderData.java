package at.jojokobi.donatengine.rendering;

import at.jojokobi.donatengine.util.Position;

public class ModelRenderData extends PositionedRenderData {

	private String tag;

	public ModelRenderData(Position position, String tag) {
		super(position);
		this.tag = tag;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}
	
}
