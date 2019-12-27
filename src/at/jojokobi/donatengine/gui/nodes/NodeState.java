package at.jojokobi.donatengine.gui.nodes;

public class NodeState {
	
	private boolean hovered;
	private boolean selected;
	public NodeState(boolean hovered, boolean selected) {
		super();
		this.hovered = hovered;
		this.selected = selected;
	}
	public boolean isHovered() {
		return hovered;
	}
	public boolean isSelected() {
		return selected;
	}
	public void setHovered(boolean hovered) {
		this.hovered = hovered;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}

}
