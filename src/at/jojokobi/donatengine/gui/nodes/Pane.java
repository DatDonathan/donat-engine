package at.jojokobi.donatengine.gui.nodes;

public class Pane extends Parent {

	@Override
	public boolean isSelectable() {
		return false;
	}

	@Override
	public void fire() {
		
	}

	@Override
	public void onType(String ch) {
		
	}

	@Override
	public void addChild(Node node) {
		super.addChild(node);
	}
	
}
