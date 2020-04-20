package at.jojokobi.donatengine.gui;

import java.util.LinkedList;
import java.util.List;

import at.jojokobi.donatengine.gui.actions.GUIAction;
import at.jojokobi.donatengine.gui.nodes.Node;
import at.jojokobi.donatengine.gui.nodes.Parent;
import at.jojokobi.donatengine.input.Input;
import at.jojokobi.donatengine.rendering.RenderData;
import at.jojokobi.donatengine.rendering.RenderShape;
import at.jojokobi.donatengine.rendering.ScreenCanvasRenderData;
import at.jojokobi.donatengine.style.FixedStyle;
import at.jojokobi.donatengine.util.Vector2D;

public class SimpleGUI implements GUI{
	
	private Parent parent;
	private FixedStyle style = new FixedStyle();
	boolean started = false;
	private Node selected;
	private String type;
	private Object data;
	private long client;
	
	public SimpleGUI(Parent parent, String type, Object data, long client) {
		super();
		this.parent = parent;
		this.type = type;
		this.client = client;
		selected = parent;
		style.reset();
	}

	@Override
	public void render(long clientId, List<RenderData> data, double width, double height) {
		if (started && client == clientId) {
			List<RenderShape> shapes = new LinkedList<>();
			parent.render(0, 0, shapes);
			data.add(new ScreenCanvasRenderData(new Vector2D(), shapes));
		}
	}

	@Override
	public void update(long clientId, GUISystem system, Input input, double width, double height, double delta) {
		if (client == clientId) {
			if (!started) {
				parent.updateStyle(input.getCursorX(), input.getCursorY(), 0, 0, selected, style);
				parent.updateDimensions(0, 0, width, height);
				parent.updateStyle(input.getCursorX(), input.getCursorY(), 0, 0, selected, style);
				started  = true;
			}
			parent.update(input, delta);
			parent.updateStyle(input.getCursorX(), input.getCursorY(), 0, 0, selected, style);
			parent.updateDimensions(0, 0, width, height);
			if (input.getPrimary()) {
				selected = parent.determineSelected(0, 0, input.getCursorX(), input.getCursorY());
			}
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
	
	@Override
	public Object getData() {
		return data;
	}

	@Override
	public long getClient() {
		return client;
	}

}
