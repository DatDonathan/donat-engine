package at.jojokobi.donatengine.gui;

import at.jojokobi.fxengine.gui.nodes.NodeState;
import at.jojokobi.fxengine.gui.style.Dimension;
import at.jojokobi.fxengine.gui.style.FixedStyle;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

@Deprecated
public interface Style {
	
	public Font getFont(NodeState state);
	
	public Paint getFill(NodeState state);
	
	public Paint getBorder(NodeState state);
	
	public Double getBorderRadius(NodeState state);
	
	public Dimension getWidth(NodeState state);
	
	public Dimension getHeight(NodeState state);
	
	public Double getBorderStrength (NodeState state);
	
	public FixedStyle toFixedStyle (NodeState state);

}
