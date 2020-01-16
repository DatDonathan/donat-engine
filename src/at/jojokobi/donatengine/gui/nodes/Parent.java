package at.jojokobi.donatengine.gui.nodes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import at.jojokobi.donatengine.gui.actions.GUIAction;
import at.jojokobi.donatengine.gui.style.FixedStyle;
import at.jojokobi.donatengine.input.Input;
import at.jojokobi.donatengine.util.Rect;
import at.jojokobi.donatengine.util.Vector2D;
import javafx.scene.canvas.GraphicsContext;

public abstract class Parent extends Node{
	
	private List<Node> children = new ArrayList<>();

	@Override
	public void render (double dx, double dy, GraphicsContext ctx) {
		super.render(dx, dy, ctx);
		for (Node node : children) {
			ctx.save();
			node.render(dx + getX(), dy + getY(), ctx);
			ctx.restore();
		}
	}
	
	@Override
	public void update(Input input, double delta) {
		super.update(input, delta);
		for (Node node : children) {
			node.update(input, delta);
		}
	}
	
	@Override
	public Node determineSelected(double dx, double dy, double mouseX, double mouseY) {
		Node selected = super.determineSelected(dx, dy, mouseX, mouseY);
		for (Iterator<Node> iterator = children.iterator(); iterator.hasNext() && selected == null;) {
			Node node = iterator.next();
			selected = node.determineSelected(dx + getX(), dy + getY(), mouseX, mouseY);
		}
		return selected;
	}
	
	@Override
	public void updateStyle(double mouseX, double mouseY, Node selected, FixedStyle parent) {
		super.updateStyle(mouseX, mouseY, selected, parent);
		for (Node node : children) {
			node.updateStyle(mouseX, mouseY, selected, getStyle());
		}
	}
	
	@Override
	public void updateDimensions(double x, double y, double parentWidth, double parentHeight) {
		super.updateDimensions(x, y, parentWidth, parentHeight);
		Map<Node, Vector2D> positions = calcPositions(children);
		for (Node node : children) {
			Vector2D pos = positions.get(node);
			node.updateDimensions(pos.getX(), pos.getY(), getWidth() - getStyle().getPaddingLeft() - getStyle().getPaddingRight(), getHeight() - getStyle().getPaddingTop() - getStyle().getPaddingBottom());
		}
	}
	
	@Override
	public List<GUIAction> fetchActions() {
		List<GUIAction> actions = super.fetchActions();
		for (Node node : children) {
			actions.addAll(node.fetchActions());
		}
		return actions;
	}
	
	@Override
	public Rect getFitBounds() {
		double width = getStyle().getPaddingLeft();
		double height = getStyle().getPaddingTop();
		for (Node node : children) {
			if (node.getX() + node.getWidth() > width) {
				width = node.getX() + node.getWidth();
			}
			if (node.getY() + node.getHeight() > height) {
				height = node.getY() + node.getHeight();
			}
		}
		width += getStyle().getPaddingRight();
		height += getStyle().getPaddingBottom();
		return new Rect(getX(), getY(), width, height);
	}
	
	protected void addChild (Node node) {
		children.add(node);
	}
	
	protected void removeChild (Node node) {
		children.remove(node);
	}
	
	protected void clear () {
		children.clear();
	}
	
	public Map<Node, Vector2D> calcPositions (List<Node> nodes) {
		Map<Node, Vector2D> positions = new HashMap<Node, Vector2D> ();
		for (Node node : nodes) {
			positions.put(node, new Vector2D(getStyle().getPaddingLeft() + node.getStyle().getMarginLeft(), getStyle().getPaddingTop() + node.getStyle().getMarginTop()));
		}
		return positions;
	}
	
	@Override
	public List<Node> getChildren() {
		return new ArrayList<>(children);
	}
	
}
