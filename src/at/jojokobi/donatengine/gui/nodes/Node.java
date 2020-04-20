package at.jojokobi.donatengine.gui.nodes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import at.jojokobi.donatengine.gui.actions.GUIAction;
import at.jojokobi.donatengine.input.Input;import at.jojokobi.donatengine.rendering.RenderRect;
import at.jojokobi.donatengine.rendering.RenderShape;
import at.jojokobi.donatengine.style.Dimension;
import at.jojokobi.donatengine.style.FitHeightDimension;
import at.jojokobi.donatengine.style.FitWidthDimension;
import at.jojokobi.donatengine.style.FixedStyle;
import at.jojokobi.donatengine.util.Pair;
import at.jojokobi.donatengine.util.Rect;
import at.jojokobi.donatengine.util.Vector2D;

public abstract class Node {

	private List<Pair<Predicate<NodeState>, FixedStyle>> styles = new ArrayList<>();
	
	private double x;
	private double y;
	private double width;
	private double height;
	private FixedStyle style;
	private Dimension widthDimension = new FitWidthDimension();
	private Dimension heightDimension = new FitHeightDimension();
	private NodeState state = new NodeState(false, false);
	private List<GUIAction> actions = new ArrayList<>();
	
	public void render (double dx, double dy, List<RenderShape> shapes) {
//		ctx.setFill(style.getFill());
//		ctx.setStroke(style.getBorder());
//		ctx.setLineWidth(style.getBorderStrength());
//		ctx.fillRoundRect(dx + x,  dy+ y, width, height - 2, style.getBorderRadius(), style.getBorderRadius());
//		ctx.strokeRoundRect(dx + x, dy + y, width , height - 2, style.getBorderRadius(), style.getBorderRadius());
		shapes.add(new RenderRect(new Vector2D(x + dx, y + dy), width, height, style));
	}

	public void update(Input input, double delta) {
		if ((state.isSelected() && input.getSubmit()) || (state.isHovered() && input.isReleased(Input.PRIMARY_BUTTON))) {
			fire();
		}
		if (state.isSelected()) {
			for (String ch : input.getTypedChars()) {
				onType(ch);
			}
		}
	}

	public List<Node> getChildren () {
		return Arrays.asList();
	}
	
	public Node determineSelected (double dx, double dy, double mouseX, double mouseY) {
		return checkHovered(mouseX, mouseY, dx, dy) && isSelectable() ? this : null;
	}
	
	public boolean checkHovered(double mouseX, double mouseY, double dx, double dy) {
		Rect rect = new Rect(x + dx, y + dy, width, height);
		return rect.isColliding(mouseX, mouseY, 0, 0);
	}

	public void addStyle(Predicate<NodeState> predicate, FixedStyle style) {
		styles.add(0, new Pair<Predicate<NodeState>, FixedStyle>(predicate, style));
	}

	public FixedStyle getStyle(NodeState state) {
		FixedStyle style = new FixedStyle();
		for (Pair<Predicate<NodeState>, FixedStyle> pair : styles) {
			if (pair.getKey().test(state)) {
				style = style.merge(pair.getValue(), false);
			}
		}
		return style;
	}

	public FixedStyle getStyle(NodeState state, FixedStyle parent) {
		FixedStyle style = getStyle(state).merge(parent, true).merge(new FixedStyle().reset(), false);
		return style;
	}
	
	public void updateStyle (double mouseX, double mouseY, double dx, double dy, Node selected, FixedStyle parent) {
		this.state = new NodeState(checkHovered(mouseX, mouseY, dx, dy), selected == this);
		this.style = getStyle(state, parent);
	}
	
	public void updateDimensions (double x, double y, double parentWidth, double parentHeight) {
		this.x = x;
		this.y = y;
		this.width = widthDimension.getValue(parentWidth, this);
		this.height = heightDimension.getValue(parentHeight, this);
	}
	
	public List<GUIAction> fetchActions () {
		List<GUIAction> actions = new ArrayList<>(this.actions);
		this.actions.clear();
		return actions;
	}

	protected void addAction (GUIAction action) {
		actions.add(action);
	}
	
	public abstract boolean isSelectable();

	public abstract void fire();

	public abstract void onType(String ch);
	
	public Rect getFitBounds () {
		return new Rect(0, 0, 0, 0);
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getWidth() {
		return width;
	}

	public double getHeight() {
		return height;
	}
	
	public Rect toRect () {
		Rect rect = new Rect(x, y, width, height);
		return rect;
	}

	public Dimension getWidthDimension() {
		return widthDimension;
	}

	public Dimension getHeightDimension() {
		return heightDimension;
	}

	public void setWidthDimension(Dimension widthDimension) {
		this.widthDimension = widthDimension;
	}

	public void setHeightDimension(Dimension heightDimension) {
		this.heightDimension = heightDimension;
	}

	public FixedStyle getStyle() {
		return style;
	}

	public NodeState getState() {
		return state;
	}

}
