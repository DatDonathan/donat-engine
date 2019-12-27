package at.jojokobi.donatengine.gui.style;

import at.jojokobi.donatengine.gui.nodes.Node;

public class FitHeightDimension implements Dimension{

	@Override
	public Double getValue(double parent, Node node) {
		return node.getFitBounds().getHeight();
	}
	
}
