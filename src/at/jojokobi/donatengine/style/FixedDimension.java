package at.jojokobi.donatengine.style;

import at.jojokobi.donatengine.gui.nodes.Node;

public class FixedDimension implements Dimension{

	private double alignment;

	public FixedDimension(double alignment) {
		super();
		this.alignment = alignment;
	}

	@Override
	public Double getValue(double parent, Node node) {
		return alignment;
	}
	
}
