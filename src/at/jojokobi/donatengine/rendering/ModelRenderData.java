package at.jojokobi.donatengine.rendering;

import at.jojokobi.donatengine.util.Position;

public class ModelRenderData extends PositionedRenderData {

	private String tag;
	private double animationTime;

	public ModelRenderData(Position position, String tag, double animationTime) {
		super(position);
		this.tag = tag;
		this.animationTime = animationTime;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public double getAnimationTime() {
		return animationTime;
	}

	public void setAnimationTime(double animationTime) {
		this.animationTime = animationTime;
	}
	
}
