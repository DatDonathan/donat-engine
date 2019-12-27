package at.jojokobi.donatengine.gui.nodes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import at.jojokobi.donatengine.util.Vector2D;

public class VBox extends Pane{

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
		double y = getStyle().getPaddingTop();
		for (Node node : nodes) {
			y += node.getStyle().getMarginTop();
			Vector2D pos = new Vector2D(getWidth()/2 - node.getWidth()/2, y);
			y+= node.getHeight();
			y += node.getStyle().getMarginBottom();
			positions.put(node, pos);
		}
		return positions;
	}
	
}
