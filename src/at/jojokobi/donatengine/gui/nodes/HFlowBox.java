package at.jojokobi.donatengine.gui.nodes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import at.jojokobi.donatengine.util.Vector2D;

public class HFlowBox extends Pane {

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
		double y = getStyle().getPaddingTop();
		List<Node> currRow = new ArrayList<>();
		for (Node node : nodes) {
			x += node.getStyle().getMarginLeft();
			if (x + node.getWidth() + node.getStyle().getMarginRight() > getWidth() - getStyle().getPaddingLeft() - getStyle().getPaddingRight() && !currRow.isEmpty()){
				x = node.getStyle().getMarginLeft() + getStyle().getPaddingLeft();
				double maxHeight = 0;
				for (Node n : currRow) {
					if (n.getHeight() + n.getStyle().getMarginTop() + n.getStyle().getMarginBottom() > maxHeight) {
						maxHeight = n.getHeight() + n.getStyle().getMarginTop() + n.getStyle().getMarginBottom();
					}
				}
				y += maxHeight;
				currRow.clear();
			}
			
			Vector2D pos = new Vector2D(x, y + node.getStyle().getMarginTop());
			positions.put(node, pos);
			currRow.add(node);
			x += node.getWidth() + node.getStyle().getMarginRight();
		}
		return positions;
	}

}
