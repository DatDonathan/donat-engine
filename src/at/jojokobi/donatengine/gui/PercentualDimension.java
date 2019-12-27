package at.jojokobi.donatengine.gui;

import at.jojokobi.donatengine.gui.nodes.Node;
import at.jojokobi.donatengine.gui.style.Dimension;

public class PercentualDimension implements Dimension {

	private double percent;

	public PercentualDimension(double percent) {
		super();
		this.percent = percent;
	}

	@Override
	public Double getValue(double parent, Node node) {
		return percent * parent;
	}
	
}
