package at.jojokobi.donatengine.rendering;

public class BackgroundRenderData extends RenderData {
	
	private String tag;


	public BackgroundRenderData(String tag) {
		super();
		this.tag = tag;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

}
