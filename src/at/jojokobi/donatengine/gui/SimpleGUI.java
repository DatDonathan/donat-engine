package at.jojokobi.donatengine.gui;

import java.util.List;

import at.jojokobi.donatengine.gui.actions.GUIAction;
import at.jojokobi.donatengine.gui.nodes.Node;
import at.jojokobi.donatengine.gui.nodes.Parent;
import at.jojokobi.donatengine.gui.style.FixedStyle;
import at.jojokobi.donatengine.input.Input;
import javafx.scene.canvas.GraphicsContext;

public class SimpleGUI implements GUI{
	
	private Parent parent;
	private FixedStyle style = new FixedStyle();
	boolean started = false;
	private Node selected;
	private String type;
	
	public SimpleGUI(Parent parent, String type) {
		super();
		this.parent = parent;
		this.type = type;
		selected = parent;
		style.reset();
	}

	@Override
	public void render(GraphicsContext ctx, double width, double height) {
		if (started) {
			parent.render(0, 0, ctx);
		}
	}

	@Override
	public void update(GUISystem system, Input input, double width, double height, double delta) {
		if (!started) {
			parent.updateStyle(input.getCursorX(), input.getCursorY(), selected, style);
			parent.updateDimensions(0, 0, width, height);
			parent.updateStyle(input.getCursorX(), input.getCursorY(), selected, style);
			started  = true;
		}
		parent.update(input, delta);
		parent.updateStyle(input.getCursorX(), input.getCursorY(), selected, style);
		parent.updateDimensions(0, 0, width, height);
		if (input.getPrimary()) {
			selected = parent.determineSelected(0, 0, input.getCursorX(), input.getCursorY());
			System.out.println(selected);
		}
	}
	
	public void select (Node node) {
		selected = node;
	}

	@Override
	public List<GUIAction> fetchActions() {
		return parent.fetchActions();
	}

	@Override
	public String getType() {
		return type;
	}

}
