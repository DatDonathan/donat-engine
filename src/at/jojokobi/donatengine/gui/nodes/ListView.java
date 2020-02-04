package at.jojokobi.donatengine.gui.nodes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import at.jojokobi.donatengine.gui.PercentualDimension;
import at.jojokobi.donatengine.gui.style.FitHeightDimension;
import at.jojokobi.donatengine.gui.style.FixedStyle;
import at.jojokobi.donatengine.input.Input;
import at.jojokobi.donatengine.util.Vector2D;
import javafx.scene.paint.Color;

public class ListView<T> extends Parent{
	
	private Supplier<List<T>> supplier;
	private List<T> temp;
	
	public ListView(Supplier<List<T>> supplier) {
		super();
		this.supplier = supplier;
	}
	
	@Override
	public void update(Input input, double delta) {
		super.update(input, delta);
		List<T> list = supplier.get();
		if (!list.equals(temp)) {
			clear();
			for (T t : list) {
				addChild(new ListItem<>(t));
			}
			temp = list;
		}
	}

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

class ListItem<T> extends Parent {
	
	private Text text;
	private T value;
	
	public ListItem(T t) {
		this.text = new Text(t + "");
		this.value = t;
		addChild(this.text);
		setWidthDimension(new PercentualDimension(1));
		setHeightDimension(new FitHeightDimension());
		addStyle(s -> true, new FixedStyle().setPadding(5));
		addStyle(s -> s.isSelected(), new FixedStyle().setFill(Color.LIGHTBLUE));
	}

	@Override
	public boolean isSelectable() {
		return true;
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
		for (Node node : nodes) {
			Vector2D pos = new Vector2D(getWidth()/2 - node.getWidth()/2, getHeight()/2 - node.getHeight()/2);
			positions.put(node, pos);
		}
		return positions;
	}

	public T getValue() {
		return value;
	}
	
}
