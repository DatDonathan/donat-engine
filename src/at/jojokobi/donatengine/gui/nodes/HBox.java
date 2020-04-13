package at.jojokobi.donatengine.gui.nodes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import at.jojokobi.donatengine.util.Vector2D;

public class HBox extends Pane{

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
	public Map<Node, Vector2D> calcPositions(List<Node> nodes) {
		Map<Node, Vector2D> positions = new HashMap<>();
		double x = getStyle().getPaddingLeft();
		for (Node node : nodes) {
			x += node.getStyle().getMarginLeft();
			Vector2D pos = new Vector2D(x, getHeight()/2 - node.getHeight()/2);
			x+= node.getWidth();
			x += node.getStyle().getMarginRight();
			positions.put(node, pos);
		}
		return positions;
	}
	
}
